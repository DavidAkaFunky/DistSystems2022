package pt.ulisboa.tecnico.classes.classserver;

import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import pt.ulisboa.tecnico.classes.NamingServerFrontend;
import pt.ulisboa.tecnico.classes.contract.ClassesDefinitions.*;
import pt.ulisboa.tecnico.classes.contract.classserver.ClassServerClassServer.*;
import pt.ulisboa.tecnico.classes.contract.classserver.ClassServerServiceGrpc;
import pt.ulisboa.tecnico.classes.contract.classserver.ClassServerServiceGrpc.*;
import pt.ulisboa.tecnico.classes.Stringify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

public class ClassesServerFrontend extends TimerTask {
	private static final String ENROLL_CMD = "enroll";
    private static final String OPEN_ENROLLMENTS_CMD = "openEnrollments";
	private static final String CLOSE_ENROLLMENTS_CMD = "closeEnrollments";
    private static final String CANCEL_ENROLLMENT_CMD = "cancelEnrollment";

    private Classes classes;
    private Classes replica;
    private NamingServerFrontend namingServer;
    private String service;
    private String host;
    private int port;
    private ArrayList<String> qualifiers;
    private boolean debug;
    private Logger logger = Logger.getLogger(AdminServiceImpl.class.getName());
    private int id;
    private Integer[] replicaVectorClock;
    private Integer[] mostRecentCommandVectorClock;
    private ConcurrentHashMap<Integer, ClassesCommand> commands;

    /**
     * ClassesServerFrontend constructor.
     * @param classes the server class state
     * @param replica the server class state replica
     * @param replicaVectorClock the vector clock associated with the class state replica
     * @param namingServer a frontend where the lookup method will be called to the naming server
     * @param service the name of this server's service
     * @param host the server's IP/host name
     * @param port the server's port
     * @param qualifiers a list of the server's qualifiers
     * @param commands a list of the server's commands
     * @param debug set to true to display a message when each method is called
     */
    public ClassesServerFrontend(Classes classes, Classes replica, Integer[] replicaVectorClock, NamingServerFrontend namingServer, String service, String host, int port, ArrayList<String> qualifiers, ConcurrentHashMap<Integer, ClassesCommand> commands, boolean debug){
        this.classes = classes;
        this.replica = replica;
        this.replicaVectorClock = replicaVectorClock;
        this.namingServer = namingServer;
        this.service = service;
        this.host = host;
        this.port = port;
        this.qualifiers = qualifiers;
        this.commands = commands;
        this.debug = debug;
        this.mostRecentCommandVectorClock = new Integer[replicaVectorClock.length];
        for (int i = 0; i < replicaVectorClock.length; ++i){
            mostRecentCommandVectorClock[i] = 0;
        }
    }

    /**
     * Register the server on the naming server.
     * @throws StatusRuntimeException
     * @return the server's id (or -1 in case of failure)
     */
    public int registerServer() throws StatusRuntimeException{
        if (debug)
			logger.log(Level.INFO, "Registering server on naming server...");
        try {
            id = namingServer.register(service, host, port, qualifiers);
            if (debug)
			    logger.log(Level.INFO, "Server registered successfully with ID " + id);
            return id;
        } catch (StatusRuntimeException e) {
            System.out.println("Caught exception with description: " +
                    e.getStatus().getDescription());
        }
        return -1;
    }

    /**
     * Delete the server from the naming server.
     */
    public void deleteServer(){
        if (debug)
			logger.log(Level.INFO, "Removing server from naming server...");
        try {
            namingServer.delete(service, host, port, id);
        } catch (StatusRuntimeException e) {
            System.out.println("Caught exception with description: " +
                    e.getStatus().getDescription());
        }
    }

    /**
     * Starts the gossip on this server.
     */
    public void startGossip(){
        List<Server> servers = namingServer.lookup("Turmas", new ArrayList<String>());
        ArrayList<ManagedChannel> managedChannels = new ArrayList<>();
        ArrayList<Command> commands = new ArrayList<>();
        if (debug)
            logger.log(Level.INFO, "Beginning gossip...");
        for (Server server: servers) {
            ManagedChannel channel = namingServer.connect(server);
            ClassServerServiceBlockingStub stub = ClassServerServiceGrpc.newBlockingStub(channel);
            try {
                int serverID = server.getId();
                StartGossipRequest request = StartGossipRequest.newBuilder().setVectorClockEntry(replicaVectorClock[serverID]).build();
                StartGossipResponse response = stub.withDeadlineAfter(2000, TimeUnit.MILLISECONDS).startGossip(request);
                ResponseCode code = response.getCode();
                if (debug)
                    logger.log(Level.INFO, Stringify.format(code));
                if (code == ResponseCode.INACTIVE_SERVER) {
                    channel.shutdown();
                }
                else if (code == ResponseCode.OK) {
                    managedChannels.add(channel);
                    List<Command> serverCommands = response.getCommandsList();
                    commands.addAll(serverCommands);
                    replicaVectorClock[serverID] += serverCommands.size();
                }
            } catch (StatusRuntimeException e) {
                channel.shutdown();
                System.out.println("Caught exception with description: " +
                        e.getStatus().getDescription());
            }
        }

        Collections.sort(commands, Comparator.comparing((Command c) -> c.getTimestamp().getSeconds())
                                         .thenComparing((c) -> c.getTimestamp().getNanos()));
        for (int i = 0; i < replicaVectorClock.length; ++i){
            System.out.println(replicaVectorClock[i]);
        }
        applyFunctions(commands);
        propagateState(replica, managedChannels);
    }

    /**
     * Applies the received commands to the stored replica of the class state.
     * @param commands the commands to apply.
     */
    public void applyFunctions(ArrayList<Command> commands){ 
        for (Command command: commands){
            String commandString = command.getCommand();
            switch (commandString){
				case CLOSE_ENROLLMENTS_CMD -> replica.closeEnrollments();
				default -> {
					String[] splitted = commandString.split(" ");
                    // The student ID must start with STUDENT_ID_PREFIX and
                    // the second part of the ID must be an integer with a length of 4
                    switch (splitted[0]){
                        case ENROLL_CMD -> {
                            String studentName = String.join(" ", Arrays.asList(splitted).subList(2, splitted.length));
                            // If the command is present, it means the server who processed the request
                            // accepted it, so the student must at least be sent to the discarded list
                            if (!replica.areEnrollmentsOpen() || replica.getCapacity() <= replica.getEnrolledSize())
                                replica.addDiscardedStudent(splitted[1], studentName);
                            else
                                replica.enrollStudent(splitted[1], studentName);
                        }
                        case CANCEL_ENROLLMENT_CMD -> {
                            if (splitted.length == 2)
                                replica.cancelStudentEnrollment(splitted[1]);
                        }
                        case OPEN_ENROLLMENTS_CMD -> {
                            if (splitted.length == 2){
                                int capacity = Integer.parseInt(splitted[1]);
                                replica.openEnrollments(capacity);
                            }
                        }
                        case CLOSE_ENROLLMENTS_CMD -> {
                            if (splitted.length == 2)
                                replica.closeEnrollments();
                        }
                    }
				}
			}
		}
    }

    /**
     * Sends this server's class state to all other servers.
     * @param classes the server's class.
     * @param managedChannels the sockets connecting it to the currently active servers.
     */
    public void propagateState(Classes classes, ArrayList<ManagedChannel> managedChannels){
        if (debug)
            logger.log(Level.INFO, "Propagating state...");
        ClassState classState = classes.getClassState();
        for (ManagedChannel channel: managedChannels) {
            ClassServerServiceBlockingStub stub = ClassServerServiceGrpc.newBlockingStub(channel);
            try {
                PropagateStateRequest request = PropagateStateRequest.newBuilder()
                    .setClassState(classState)
                    .addAllVectorClock(Arrays.asList(replicaVectorClock))
                    .build();
                PropagateStateResponse response = stub.withDeadlineAfter(2000, TimeUnit.MILLISECONDS).propagateState(request);
                channel.shutdown();
                mostRecentCommandVectorClock[response.getId()] = replicaVectorClock[id];
                if (debug)
                    logger.log(Level.INFO, Stringify.format(response.getCode()));
            } catch (StatusRuntimeException e) {
                channel.shutdown();
                System.out.println("Caught exception with description: " +
                        e.getStatus().getDescription());
            }
        }
        int minCommand = Collections.min(Arrays.asList(mostRecentCommandVectorClock));
        for (Integer i: commands.keySet()){
            if (i > minCommand)
                break;
            commands.remove(i);
        }
    }

    public void run() {
		if (classes.isGossipActivated())
			startGossip();
	}
}
