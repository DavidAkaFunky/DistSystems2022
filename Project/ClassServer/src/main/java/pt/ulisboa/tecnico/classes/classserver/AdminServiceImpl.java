package pt.ulisboa.tecnico.classes.classserver;

import io.grpc.stub.StreamObserver;

import pt.ulisboa.tecnico.classes.contract.admin.AdminServiceGrpc;
import pt.ulisboa.tecnico.classes.contract.admin.AdminClassServer.*;
import pt.ulisboa.tecnico.classes.contract.ClassesDefinitions.*;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminServiceImpl extends AdminServiceGrpc.AdminServiceImplBase {
	
	private Classes classes;
	private ClassesServerFrontend classesServerFrontend;
	private boolean debug;
	private boolean gossipDeactivatedManually = true;
	private Logger logger = Logger.getLogger(AdminServiceImpl.class.getName());

	/**
	 * AdminServiceImpl constructor.
	 * @param classes the server's class
	 * @param classesServerFrontend the frontend to the classes server
	 * @param debug set to true to display a message when each method is called
	 */
	public AdminServiceImpl(Classes classes, ClassesServerFrontend classesServerFrontend, boolean debug){
		this.classes = classes;
		this.classesServerFrontend = classesServerFrontend;
		this.debug = debug;
	}

	/**
	 * Activate server service (calls setServerStatus with true)
	 * @param request
	 * @param responseObserver
	 */
    @Override
	public void activate(ActivateRequest request, StreamObserver<ActivateResponse> responseObserver) {
		if (debug)
			logger.log(Level.INFO, "Activate request");
		synchronized (classes){
			classes.setServerStatus(true);
			classes.setMaintenance(false);
			if (!gossipDeactivatedManually)
				classes.setGossipActivated(true);
		}
        ActivateResponse response = ActivateResponse.newBuilder().setCode(ResponseCode.OK).build();
		// Send a single response through the stream.
		responseObserver.onNext(response);
		// Notify the client that the operation has been completed.
		responseObserver.onCompleted();
	}

	/**
	 * Deactivate server service (calls setServerStatus with false)
	 * @param request
	 * @param responseObserver
	 */
    @Override
	public void deactivate(DeactivateRequest request, StreamObserver<DeactivateResponse> responseObserver) {
		if (debug)
			logger.log(Level.INFO, "Deactivate request");
		synchronized (classes){
			classes.setServerStatus(false);
			classes.setGossipActivated(false);
		}
		DeactivateResponse response = DeactivateResponse.newBuilder().setCode(ResponseCode.OK).build();
		// Send a single response through the stream.
		responseObserver.onNext(response);
		// Notify the client that the operation has been completed.
		responseObserver.onCompleted();
	}

	/**
	 * List all enrolled and discarded students (independently of the server's activity mode)
	 * @param request
	 * @param responseObserver
	 */
	@Override
	public void dump(DumpRequest request, StreamObserver<DumpResponse> responseObserver) {
		// StreamObserver is used to represent the gRPC stream between the server and
		// client in order to send the appropriate responses (or errors, if any occur).
		if (debug)
			logger.log(Level.INFO, "Dump request");
		DumpResponse.Builder responseBuilder = DumpResponse.newBuilder();
		synchronized (classes) {
			responseBuilder.setClassState(classes.getClassState());
		}

		// Send a single response through the stream.
		responseObserver.onNext(responseBuilder.setCode(ResponseCode.OK).build());
		// Notify the client that the operation has been completed.
		responseObserver.onCompleted();
	}

	/**
	 * Activates the automatic gossip between the servers
	 * @param request
	 * @param responseObserver
	 */
	@Override
	public void activateGossip(ActivateGossipRequest request, StreamObserver<ActivateGossipResponse> responseObserver) {
		// StreamObserver is used to represent the gRPC stream between the server and
		// client in order to send the appropriate responses (or errors, if any occur).
		if (debug)
			logger.log(Level.INFO, "Activate gossip request");
		synchronized (classes){
			classes.setGossipActivated(true);
			gossipDeactivatedManually = false;
		}
			
		// Send a single response through the stream.
		responseObserver.onNext(ActivateGossipResponse.newBuilder().setCode(ResponseCode.OK).build());
		// Notify the client that the operation has been completed.
		responseObserver.onCompleted();
	}

	/**
	 * Dectivates the automatic gossip between the servers
	 * @param request
	 * @param responseObserver
	 */
	@Override
	public void deactivateGossip(DeactivateGossipRequest request, StreamObserver<DeactivateGossipResponse> responseObserver) {
		// StreamObserver is used to represent the gRPC stream between the server and
		// client in order to send the appropriate responses (or errors, if any occur).
		if (debug)
			logger.log(Level.INFO, "Deactivate gossip request");
			
		synchronized (classes){
			classes.setGossipActivated(false);
			gossipDeactivatedManually = true;
		}
		
		// Send a single response through the stream.
		responseObserver.onNext(DeactivateGossipResponse.newBuilder().setCode(ResponseCode.OK).build());
		// Notify the client that the operation has been completed.
		responseObserver.onCompleted();
	}

	/**
	 * Forces the automatic gossip between the servers
	 * @param request
	 * @param responseObserver
	 */
	@Override
	public void gossip(GossipRequest request, StreamObserver<GossipResponse> responseObserver) {
		// StreamObserver is used to represent the gRPC stream between the server and
		// client in order to send the appropriate responses (or errors, if any occur).
		if (debug)
			logger.log(Level.INFO, "Gossip request");

		GossipResponse.Builder responseBuilder = GossipResponse.newBuilder();
		ResponseCode code;
		// Does the server need to be active to start the gossip?
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
		else {
			if (debug)
				logger.log(Level.INFO, "Gossip started!");
			classesServerFrontend.startGossip();
			code = ResponseCode.OK;
		}
		
		// Send a single response through the stream.
		responseObserver.onNext(responseBuilder.setCode(code).build());
		// Notify the client that the operation has been completed.
		responseObserver.onCompleted();
	}

}