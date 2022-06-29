package pt.ulisboa.tecnico.classes.student;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import pt.ulisboa.tecnico.classes.NamingServerFrontend;
import pt.ulisboa.tecnico.classes.contract.ClassesDefinitions;
import pt.ulisboa.tecnico.classes.contract.student.StudentServiceGrpc;
import pt.ulisboa.tecnico.classes.contract.student.StudentServiceGrpc.StudentServiceBlockingStub;
import pt.ulisboa.tecnico.classes.contract.student.StudentClassServer.*;
import pt.ulisboa.tecnico.classes.contract.ClassesDefinitions.*;
import pt.ulisboa.tecnico.classes.Stringify;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class StudentFrontend {

    private NamingServerFrontend namingServer;
    private boolean debug;
    private Logger logger = Logger.getLogger(StudentFrontend.class.getName());

    private boolean isEnrolled = false;

    /**
     * Student frontend constructor.
     * @param namingServer a frontend where the lookup method will be called to the naming server
     * @param debug set to true to display a message when each method is called
     */
    public StudentFrontend(NamingServerFrontend namingServer, boolean debug){
        this.namingServer = namingServer;
        this.debug = debug;
    }

    /**
     * Request server for a class and list with using the formatting given by Stringify.
     * @param studentID the ID of the student making the request
     */
	public void listClass(String studentID){
        if (debug)
            logger.log(Level.INFO, "Sending list class server request...");
        try {
            List<Server> servers = namingServer.lookup("Turmas", new ArrayList<String>());
            while (servers.size() > 0) {
                Server server = namingServer.chooseServer(servers);
                ManagedChannel channel = namingServer.connect(server);
                StudentServiceBlockingStub stub = StudentServiceGrpc.newBlockingStub(channel);
                try {
                    ListClassRequest request = ListClassRequest.newBuilder().setStudentID(studentID).setIsEnrolled(isEnrolled).build();
                    ListClassResponse response = stub.withDeadlineAfter(2000, TimeUnit.MILLISECONDS).listClass(request);
                    channel.shutdown();
                    ResponseCode code = response.getCode();
                    switch (code) {
                        case OK -> {
                            ClassState classState = response.getClassState();
                            System.out.println(Stringify.format(classState));
                            return;
                        }
                        case INACTIVE_SERVER -> servers.remove(server);
                        case NON_EXISTING_STUDENT -> servers.remove(server);
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
            System.out.println("ERROR: All servers are currently unavailable.");
        } catch (StatusRuntimeException e) {
            System.out.println("Caught exception with description: " +
                    e.getStatus().getDescription() + ". Naming Server isn't working.");
        }
    }

    /**
     * Requests server to enroll a student taking an ID and name, prints response with stringify format
     * @param studentID the ID of the student making the request
     * @param StudentName the name of the student making the request
     */
    public void enroll(String studentID, String StudentName){
        if (debug)
            logger.log(Level.INFO, "Sending enroll server request...");
        try {
            List<Server> servers = namingServer.lookup("Turmas", new ArrayList<String>());
            ClassesDefinitions.Student student = ClassesDefinitions.Student.newBuilder()
                    .setStudentId(studentID)
                    .setStudentName(StudentName)
                    .build();
            EnrollRequest request = EnrollRequest.newBuilder().setStudent(student).build();
            while (servers.size() > 0) {
                Server server = namingServer.chooseServer(servers);
                ManagedChannel channel = namingServer.connect(server);
                StudentServiceBlockingStub stub = StudentServiceGrpc.newBlockingStub(channel);
                try{
                    ResponseCode code = stub.withDeadlineAfter(2000, TimeUnit.MILLISECONDS).enroll(request).getCode();
                    channel.shutdown();
                    if (code != ResponseCode.INACTIVE_SERVER){
                        if (code == ResponseCode.OK)
                            isEnrolled = true;
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
            System.out.println("ERROR: All servers are currently unavailable.");
        } catch (StatusRuntimeException e) {
            System.out.println("Caught exception with description: " +
                    e.getStatus().getDescription() + ". Naming Server isn't working.");
        }
    }

}
