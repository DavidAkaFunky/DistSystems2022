package pt.ulisboa.tecnico.classes.professor;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import pt.ulisboa.tecnico.classes.NamingServerFrontend;
import pt.ulisboa.tecnico.classes.contract.professor.ProfessorServiceGrpc;
import pt.ulisboa.tecnico.classes.contract.professor.ProfessorServiceGrpc.ProfessorServiceBlockingStub;
import pt.ulisboa.tecnico.classes.contract.professor.ProfessorClassServer.*;
import pt.ulisboa.tecnico.classes.contract.ClassesDefinitions.*;
import pt.ulisboa.tecnico.classes.Stringify;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ProfessorFrontend {

    private NamingServerFrontend namingServer;
    private boolean debug;
    private Logger logger = Logger.getLogger(ProfessorFrontend.class.getName());

    /**
     * Professor frontend constructor.
     * @param namingServer a frontend where the lookup method will be called to the naming server
     * @param debug set to true to display a message when each method is called
     */
    public ProfessorFrontend(NamingServerFrontend namingServer, boolean debug){
        this.namingServer = namingServer;
        this.debug = debug;
    }


    /**
     * Request server to open enrollments.
     * @param capacity the maximum capacity of the enrollment
     */
    public void openEnrollments(int capacity) {
        if (debug)
            logger.log(Level.INFO, "Sending open enrollments server request...");
        try {
            ArrayList<String> qualifiers = new ArrayList<String>();
            qualifiers.add("P");
            List<Server> servers = namingServer.lookup("Turmas", qualifiers);

            while (servers.size() > 0) {
                Server server = namingServer.chooseServer(servers);
                ManagedChannel channel = namingServer.connect(server);
                ProfessorServiceBlockingStub stub = ProfessorServiceGrpc.newBlockingStub(channel);

                try {
                    OpenEnrollmentsRequest request = OpenEnrollmentsRequest.newBuilder().setCapacity(capacity).build();
                    ResponseCode code = stub.withDeadlineAfter(2000, TimeUnit.MILLISECONDS).openEnrollments(request).getCode();
                    channel.shutdown();
                    if (code != ResponseCode.INACTIVE_SERVER) {
                        System.out.println(Stringify.format(code));
                        return;
                    }
                    servers.remove(server);
                } catch (StatusRuntimeException e) {
                    channel.shutdown();
                    servers.remove(server);
                    System.out.println("Caught exception with description: " +
                            e.getStatus().getDescription());
                }
            }

            System.out.println("ERROR: All servers are currently inactive.");
        } catch (StatusRuntimeException e) {
            System.out.println("Caught exception with description: " +
                    e.getStatus().getDescription() + ". Naming Server isn't working.");
        }
    }

    /**
     * Request server to close enrollments.
     */
	public void closeEnrollments(){
        if (debug)
            logger.log(Level.INFO, "Sending close enrollments server request...");
        try {
            ArrayList<String> qualifiers = new ArrayList<String>();
            qualifiers.add("P");
            List<Server> servers = namingServer.lookup("Turmas", qualifiers);

            while (servers.size() > 0) {
                Server server = namingServer.chooseServer(servers);
                ManagedChannel channel = namingServer.connect(server);
                ProfessorServiceBlockingStub stub = ProfessorServiceGrpc.newBlockingStub(channel);

                try {
                    ResponseCode code = stub.withDeadlineAfter(2000, TimeUnit.MILLISECONDS).closeEnrollments(CloseEnrollmentsRequest.getDefaultInstance()).getCode();
                    channel.shutdown();
                    if (code != ResponseCode.INACTIVE_SERVER) {
                        System.out.println(Stringify.format(code));
                        return;
                    }
                    servers.remove(server);
                } catch (StatusRuntimeException e) {
                    channel.shutdown();
                    servers.remove(server);
                    System.out.println("Caught exception with description: " +
                            e.getStatus().getDescription());
                }

            }
            System.out.println("ERROR: All servers are currently inactive.");
        } catch (StatusRuntimeException e) {
            System.out.println("Caught exception with description: " +
                    e.getStatus().getDescription() + ". Naming Server isn't working.");
        }
    }

    /**
     * Request server for a class and list with using the formatting given by Stringify.
     */
	public void listClass(){
        if (debug)
            logger.log(Level.INFO, "Sending list class server request...");
        try {
            List<Server> servers = namingServer.lookup("Turmas", new ArrayList<String>());

            while (servers.size() > 0) {
                Server server = namingServer.chooseServer(servers);
                ManagedChannel channel = namingServer.connect(server);
                ProfessorServiceBlockingStub stub = ProfessorServiceGrpc.newBlockingStub(channel);
                try {
                    ListClassResponse response = stub.withDeadlineAfter(2000, TimeUnit.MILLISECONDS).listClass(ListClassRequest.getDefaultInstance());
                    channel.shutdown();
                    ResponseCode code = response.getCode();
                    switch (code) {
                        case OK -> {
                            ClassState classState = response.getClassState();
                            System.out.println(Stringify.format(classState));
                            return;
                        }
                        case INACTIVE_SERVER -> servers.remove(server);
                        default -> {
                            System.out.println(Stringify.format(code));
                            return;
                        }
                    }
                } catch (StatusRuntimeException e) {
                    channel.shutdown();
                    servers.remove(server);
                    System.out.println("Caught exception with description: " +
                            e.getStatus().getDescription());
                }

            }

            System.out.println("ERROR: All servers are currently inactive.");
        } catch (StatusRuntimeException e) {
            System.out.println("Caught exception with description: " +
                    e.getStatus().getDescription() + ". Naming Server isn't working.");
        }
    }

    /**
     * Request server to cancel the enrollment of a specific student
     * @param studentID the students' ID to cancel the enrollment
     */
	public void cancelEnrollment(String studentID){
        if (debug)
            logger.log(Level.INFO, "Sending cancel enrollments server request...");
        try {
            ArrayList<String> qualifiers = new ArrayList<String>();
            qualifiers.add("P");
            List<Server> servers = namingServer.lookup("Turmas", qualifiers);

            while (servers.size() > 0) {
                Server server = namingServer.chooseServer(servers);
                ManagedChannel channel = namingServer.connect(server);
                ProfessorServiceBlockingStub stub = ProfessorServiceGrpc.newBlockingStub(channel);

                try {
                    CancelEnrollmentRequest request = CancelEnrollmentRequest.newBuilder().setStudentId(studentID).build();
                    ResponseCode code = stub.withDeadlineAfter(2000, TimeUnit.MILLISECONDS).cancelEnrollment(request).getCode();
                    channel.shutdown();
                    if (code != ResponseCode.INACTIVE_SERVER) {
                        System.out.println(Stringify.format(code));
                        return;
                    }
                    servers.remove(server);
                } catch (StatusRuntimeException e) {
                    channel.shutdown();
                    servers.remove(server);
                    System.out.println("Caught exception with description: " +
                            e.getStatus().getDescription());
                }
            }

            System.out.println("ERROR: All servers are currently inactive.");
        } catch (StatusRuntimeException e) {
            System.out.println("Caught exception with description: " +
                    e.getStatus().getDescription() + ". Naming Server isn't working.");
        }
    }

}
