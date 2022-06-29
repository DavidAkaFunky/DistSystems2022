package pt.ulisboa.tecnico.classes.classserver;
import io.grpc.stub.StreamObserver;
import pt.ulisboa.tecnico.classes.contract.professor.ProfessorServiceGrpc;
import pt.ulisboa.tecnico.classes.contract.professor.ProfessorClassServer.*;
import pt.ulisboa.tecnico.classes.contract.ClassesDefinitions.*;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.concurrent.ConcurrentHashMap;

public class ProfessorServiceImpl extends ProfessorServiceGrpc.ProfessorServiceImplBase {

	private static final String STUDENT_ID_PREFIX = "aluno";

	private Classes classes;
	private ArrayList<String> qualifiers;
	private ConcurrentHashMap<Integer, ClassesCommand> commands;
	private Integer[] vectorClock;
	private int id;
	private boolean debug;
	private ClassesServerFrontend classesServerFrontend;
	private Logger logger = Logger.getLogger(ProfessorServiceImpl.class.getName());

	/**
	 * ProfessorServerImpl constructor.
	 * @param classes the server's class
	 * @param qualifiers the server's qualifiers
	 * @param commands a list of the server's commands
     * @param vectorClock the vector clock of this server
     * @param id the server's id (given by the naming server)
     * @param classesServerFrontend the vector clock associated to the replica
	 * @param debug set to true to display a message when each method is called
	 */
	public ProfessorServiceImpl(Classes classes, ArrayList<String> qualifiers, ConcurrentHashMap<Integer, ClassesCommand> commands, Integer[] vectorClock, int id, ClassesServerFrontend classesServerFrontend, boolean debug){
		this.classes = classes;
		this.qualifiers = qualifiers;
		this.commands = commands;
		this.vectorClock = vectorClock;
		this.id = id;
		this.classesServerFrontend = classesServerFrontend;
		this.debug = debug;
	}

	/**
	 * Open enrollments, unless the server is inactive,
	 * or the given class size is less than the current class capacity,
	 * or the enrollments have already been opened
	 * @param request
	 * @param responseObserver
	 */
    @Override
	public void openEnrollments(OpenEnrollmentsRequest request, StreamObserver<OpenEnrollmentsResponse> responseObserver) {
		// StreamObserver is used to represent the gRPC stream between the server and
		// client in order to send the appropriate responses (or errors, if any occur).
		Integer capacity = request.getCapacity();
		if (debug)
			logger.log(Level.INFO, "Open enrollments request (capacity = {0})", capacity);
        ResponseCode code;
		synchronized (classes){
			if (!classes.getServerStatus()){
				if (debug)
					logger.log(Level.WARNING, "Inactive server");
				code = ResponseCode.INACTIVE_SERVER;
			}
			else if (classes.inMaintenance()){
				if (debug)
					logger.log(Level.WARNING, "Server in maintenance mode (gossip)");
				code = ResponseCode.INACTIVE_SERVER;
			}
			else if (!qualifiers.contains("P")){
				if (debug)
					logger.log(Level.WARNING, "Writing not supported");
				code = ResponseCode.WRITING_NOT_SUPPORTED;
			}
			else if (classes.getEnrolledSize() > capacity){
				if (debug)
					logger.log(Level.WARNING, "Class is currently full");
				code = ResponseCode.FULL_CLASS;
			}
			else if (classes.openEnrollments(request.getCapacity())){
				code = ResponseCode.OK;
				String command = "openEnrollments " + request.getCapacity();
				ClassesCommand classCommand = new ClassesCommand(command);
				commands.put(++vectorClock[id], classCommand);
			}
			else {
				if (debug)
					logger.log(Level.WARNING, "Enrollments have already been opened");
				code = ResponseCode.ENROLLMENTS_ALREADY_OPENED;
			}
		}

		OpenEnrollmentsResponse response = OpenEnrollmentsResponse.newBuilder().setCode(code).build();

		// Send a single response through the stream.
		responseObserver.onNext(response);
		// Notify the client that the operation has been completed.
		responseObserver.onCompleted();
		if (code == ResponseCode.OK)
			classesServerFrontend.startGossip();
	}

	/**
	 * Close enrollments, unless the server is inactive, or enrollments have already been closed
	 * @param request
	 * @param responseObserver
	 */
    @Override
	public void closeEnrollments(CloseEnrollmentsRequest request, StreamObserver<CloseEnrollmentsResponse> responseObserver) {
		// StreamObserver is used to represent the gRPC stream between the server and
		// client in order to send the appropriate responses (or errors, if any occur).

		if (debug)
			logger.log(Level.INFO, "Close enrollments request");
		ResponseCode code;
		synchronized (classes){
			if (!classes.getServerStatus()){
				if (debug)
					logger.log(Level.WARNING, "Inactive server");
				code = ResponseCode.INACTIVE_SERVER;
			}
			else if (classes.inMaintenance()){
				if (debug)
					logger.log(Level.WARNING, "Server in maintenance mode (gossip)");
				code = ResponseCode.INACTIVE_SERVER;
			}
			else if (!qualifiers.contains("P")){
				if (debug)
					logger.log(Level.WARNING, "Writing not supported");
				code = ResponseCode.WRITING_NOT_SUPPORTED;
			}
			else if (classes.closeEnrollments()){
				code = ResponseCode.OK;
				String command = "closeEnrollments";
				ClassesCommand classCommand = new ClassesCommand(command);
				commands.put(++vectorClock[id], classCommand);
			}
			else {
				if (debug)
					logger.log(Level.WARNING, "Enrollments have already been closed");
				code = ResponseCode.ENROLLMENTS_ALREADY_CLOSED;
			}
		}

		CloseEnrollmentsResponse response = CloseEnrollmentsResponse.newBuilder().setCode(code).build();

		// Send a single response through the stream.
		responseObserver.onNext(response);
		// Notify the client that the operation has been completed.
		responseObserver.onCompleted();
		if (code == ResponseCode.OK)
			classesServerFrontend.startGossip();
	}

	/**
	 * List all enrolled and discarded students, unless server is inactive
	 * @param request
	 * @param responseObserver
	 */

	@Override
	public void listClass(ListClassRequest request, StreamObserver<ListClassResponse> responseObserver) {
		// StreamObserver is used to represent the gRPC stream between the server and
		// client in order to send the appropriate responses (or errors, if any occur).

		if (debug)
			logger.log(Level.INFO, "List class request");
		ResponseCode code;
		ListClassResponse.Builder responseBuilder = ListClassResponse.newBuilder();
		synchronized (classes) {
			if (!classes.getServerStatus()){
				if (debug)
					logger.log(Level.WARNING, "Inactive server");
				code = ResponseCode.OK;
			}
			else if (classes.inMaintenance()){
				if (debug)
					logger.log(Level.WARNING, "Server in maintenance mode (gossip)");
				code = ResponseCode.OK;
			}
			else {
				if (debug)
					logger.log(Level.INFO, "Class state sent successfully");
				code = ResponseCode.OK;
				responseBuilder.setClassState(classes.getClassState());
			}
		}

		// Send a single response through the stream.
		responseObserver.onNext(responseBuilder.setCode(code).build());
		// Notify the client that the operation has been completed.
		responseObserver.onCompleted();
	}

	/**
	 * Cancel enrollment unless the server is inactive, or the student provided doesn't exist
	 * @param request
	 * @param responseObserver
	 */
	@Override
	public void cancelEnrollment(CancelEnrollmentRequest request, StreamObserver<CancelEnrollmentResponse> responseObserver) {
		// StreamObserver is used to represent the gRPC stream between the server and
		// client in order to send the appropriate responses (or errors, if any occur).

		if (debug)
			logger.log(Level.INFO, "Cancel enrollment request (ID = {0})", request.getStudentId());
		ResponseCode code;
		String studentID = request.getStudentId();
		synchronized (classes){
			if (!classes.getServerStatus()){
				if (debug)
					logger.log(Level.WARNING, "Inactive server");
				code = ResponseCode.INACTIVE_SERVER;
			}
			else if (classes.inMaintenance()){
				if (debug)
					logger.log(Level.WARNING, "Server in maintenance mode (gossip)");
				code = ResponseCode.INACTIVE_SERVER;
			}
			else if (!qualifiers.contains("P")) {
				if (debug)
					logger.log(Level.WARNING, "Writing not supported");
				code = ResponseCode.WRITING_NOT_SUPPORTED;
			}
			else if (studentID.length() != 9 ||
					!studentID.startsWith(STUDENT_ID_PREFIX) ||
					!Pattern.matches("[0-9]+[\\.]?[0-9]*", studentID.substring(5))) {
				if (debug)
					logger.log(Level.WARNING, "Wrong studentName/studentID format.");
				code = ResponseCode.NON_EXISTING_STUDENT;
			}
			else if (classes.cancelStudentEnrollment(studentID)) {
				if (debug)
					logger.log(Level.INFO, "Student enrollment cancelled successfully");
				code = ResponseCode.OK;
				String command = "cancelEnrollment " + studentID;
				ClassesCommand classCommand = new ClassesCommand(command);
				commands.put(++vectorClock[id],classCommand);
			}
			else {
				if (debug)
					logger.log(Level.WARNING, "Student does not exist");
				System.out.println(classes.getEnrolledStudents());
				code = ResponseCode.NON_EXISTING_STUDENT;
			}
		}

		CancelEnrollmentResponse response = CancelEnrollmentResponse.newBuilder().setCode(code).build();

		// Send a single response through the stream.
		responseObserver.onNext(response);
		// Notify the client that the operation has been completed.
		responseObserver.onCompleted();
	}

}