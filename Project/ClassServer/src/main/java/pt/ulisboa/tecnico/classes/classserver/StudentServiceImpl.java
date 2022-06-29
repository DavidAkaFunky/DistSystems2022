package pt.ulisboa.tecnico.classes.classserver;

import pt.ulisboa.tecnico.classes.contract.student.StudentServiceGrpc;
import pt.ulisboa.tecnico.classes.contract.student.StudentClassServer.*;
import pt.ulisboa.tecnico.classes.contract.ClassesDefinitions.*;
import pt.ulisboa.tecnico.classes.contract.ClassesDefinitions;
import io.grpc.stub.StreamObserver;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.concurrent.ConcurrentHashMap;

public class StudentServiceImpl extends StudentServiceGrpc.StudentServiceImplBase{

	private static final String STUDENT_ID_PREFIX = "aluno";

    private Classes classes;
	private ConcurrentHashMap<Integer, ClassesCommand> commands;
	private Integer[] vectorClock;
	private int id;
	private boolean debug;
	private Logger logger = Logger.getLogger(StudentServiceImpl.class.getName());

	/**
	 * StudentServerImpl constructor.
	 * @param classes the server's class
     * @param commands a list of the server's commands
     * @param vectorClock the vector clock of this server
	 * @param id the server's id (given by the naming server)
	 * @param debug set to true to display a message when each method is called
	 */
    public StudentServiceImpl(Classes classes, ConcurrentHashMap<Integer, ClassesCommand> commands, Integer[] vectorClock, int id, boolean debug){
		this.classes = classes;
		this.commands = commands;
		this.vectorClock = vectorClock;
		this.id = id;
		this.debug = debug;
	}

	/**
	 * List class unless the server is inactive.
	 * @param request
	 * @param responseObserver
	 */
	@Override
	public void listClass(ListClassRequest request, StreamObserver<ListClassResponse> responseObserver) {
		// StreamObserver is used to represent the gRPC stream between the server and
		// client in order to send the appropriate responses (or errors, if any occur).
		boolean isEnrolled = request.getIsEnrolled();
		String studentID = request.getStudentID(); 
		if (debug)
			logger.log(Level.INFO, "List class request");
		ListClassResponse.Builder responseBuilder = ListClassResponse.newBuilder();
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
			else if (isEnrolled && !(classes.inClass(studentID))){
				code = ResponseCode.NON_EXISTING_STUDENT;	
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
	 * Enroll student unless the server is inactive,
	 * or the enrollments are already closed,
	 * or the class is full.
	 * @param request
	 * @param responseObserver
	 */
    @Override
    public void enroll(EnrollRequest request, StreamObserver<EnrollResponse> responseObserver){
		ClassesDefinitions.Student student = request.getStudent();
		if (debug)
			logger.log(Level.INFO, "Enroll request (ID = {0}, name = {1})",
							       new Object[] {student.getStudentId(), student.getStudentName()});
        ResponseCode code;
		String studentID = student.getStudentId();
		String studentName = student.getStudentName();
		synchronized (classes) {
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
			else if (!classes.areEnrollmentsOpen()) {
				if (debug)
					logger.log(Level.WARNING, "Enrollments are closed");
				code = ResponseCode.ENROLLMENTS_ALREADY_CLOSED;
			}
			else if (classes.getCapacity() <= classes.getEnrolledSize()) {
				if (debug)
					logger.log(Level.WARNING, "Class is full");
				code = ResponseCode.FULL_CLASS;
			}
			else if (studentID.length() != 9 ||
					 !studentID.startsWith(STUDENT_ID_PREFIX) ||
					 !Pattern.matches("[0-9]+[\\.]?[0-9]*", studentID.substring(5)) ||
					 studentName.length() > 30 ||
					 studentName.length() < 3) {
				if (debug)
					logger.log(Level.WARNING, "Wrong studentName/studentID format.");
				code = ResponseCode.NON_EXISTING_STUDENT;
			}
			else if (classes.enrollStudent(student.getStudentId(), student.getStudentName())){
				if (debug)
					logger.log(Level.INFO, "Student enrolled successfully");
				code = ResponseCode.OK;
				String command = "enroll " + studentID + " " + studentName; 
				ClassesCommand classCommand = new ClassesCommand(command);
				commands.put(++vectorClock[id], classCommand);
			}
			else {
				if (debug)
					logger.log(Level.WARNING, "Student already enrolled");
				code = ResponseCode.STUDENT_ALREADY_ENROLLED;
			}
		}

		EnrollResponse response = EnrollResponse.newBuilder().setCode(code).build();

		// Send a single response through the stream.
		responseObserver.onNext(response);
		// Notify the client that the operation has been completed.
		responseObserver.onCompleted();

    }
}