package pt.ulisboa.tecnico.classes.classserver;

import io.grpc.stub.StreamObserver;
import pt.ulisboa.tecnico.classes.contract.ClassesDefinitions.ResponseCode;
import pt.ulisboa.tecnico.classes.contract.ClassesDefinitions.Command; 
import pt.ulisboa.tecnico.classes.contract.classserver.ClassServerClassServer.*;
import pt.ulisboa.tecnico.classes.contract.classserver.ClassServerServiceGrpc;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ClassesServerServiceImpl extends ClassServerServiceGrpc.ClassServerServiceImplBase {

    private boolean debug;
    private Classes classes;
    private Classes replica;
    private Integer id;
    private ConcurrentHashMap<Integer, ClassesCommand> commands;
    private Integer[] vectorClock;
    private Integer[] replicaVectorClock;
    private Logger logger = Logger.getLogger(ClassesServerServiceImpl.class.getName());

    /**
     * ClassServerServiceImpl constructor.
     * @param classes the server's class
     * @param replica the server's class replica
     * @param commands a list of the server's commands
     * @param vectorClock the vector clock of this server
     * @param replicaVectorClock the vector clock associated to the replica
     * @param id the server's id (given by the naming server)
     * @param debug set to true to display a message when each method is called
     */
    public ClassesServerServiceImpl(Classes classes, Classes replica, ConcurrentHashMap<Integer, ClassesCommand> commands, Integer[] vectorClock, Integer[] replicaVectorClock, Integer id, boolean debug){
		this.classes = classes;
        this.replica = replica;
		this.commands = commands;
		this.vectorClock = vectorClock;
        this.replicaVectorClock = replicaVectorClock;
        this.id = id;
		this.debug = debug;
	}

    /**
     * Start gossip service
     * @param request
     * @param responseObserver
     */
    @Override
    public void startGossip(StartGossipRequest request, StreamObserver<StartGossipResponse> responseObserver) {
        if (debug)
            logger.log(Level.INFO, "Gossip request");

        ResponseCode code;
        StartGossipResponse.Builder responseBuilder = StartGossipResponse.newBuilder();
        synchronized (classes){
            if (classes.getServerStatus()){
                classes.setMaintenance(true);
                code = ResponseCode.OK;
                System.out.println(request.getVectorClockEntry());
                System.out.println(vectorClock[id]);
                for (int i = request.getVectorClockEntry() + 1; i <= vectorClock[id]; ++i){
                    ClassesCommand command = commands.get(i);
                    responseBuilder.addCommands(Command.newBuilder()
                                                       .setTimestamp(command.getTimestamp())
                                                       .setCommand(command.getCommand()));
                }
            }
            else{
                code = ResponseCode.INACTIVE_SERVER;
            }
        }

        // Send a single response through the stream.
        responseObserver.onNext(responseBuilder.setCode(code).build());
        // Notify the client that the operation has been completed.
        responseObserver.onCompleted();
    }

    /**
     * Propagate state service
     * @param request
     * @param responseObserver
     */
    @Override
    public void propagateState(PropagateStateRequest request, StreamObserver<PropagateStateResponse> responseObserver) {
        if (debug)
            logger.log(Level.INFO, "Propagate state request");

        ResponseCode code;
        synchronized (classes){
            if (classes.getServerStatus()){
                classes.setClassState(request.getClassState());
                replica.setClassState(request.getClassState());
                List<Integer> newVectorClock = request.getVectorClockList();
                for (int i = 0; i < newVectorClock.size(); ++i){
                    vectorClock[i] = newVectorClock.get(i);
                    replicaVectorClock[i] = newVectorClock.get(i);
                }
                code = ResponseCode.OK;
            }
            else{
                code = ResponseCode.INACTIVE_SERVER;
            }
            classes.setMaintenance(false);
        }

        PropagateStateResponse response = PropagateStateResponse.newBuilder().setCode(code).build();
        // Send a single response through the stream.
        responseObserver.onNext(response);
        // Notify the client that the operation has been completed.
        responseObserver.onCompleted();
    }

}
