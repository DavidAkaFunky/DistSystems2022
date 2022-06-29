package pt.ulisboa.tecnico.classes.namingserver;

import io.grpc.stub.StreamObserver;

import pt.ulisboa.tecnico.classes.contract.naming.NamingServerServiceGrpc;
import pt.ulisboa.tecnico.classes.contract.naming.ClassServerNamingServer.*;
import pt.ulisboa.tecnico.classes.contract.ClassesDefinitions.Server;

import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static io.grpc.Status.RESOURCE_EXHAUSTED;

public class NamingServerServiceImpl extends NamingServerServiceGrpc.NamingServerServiceImplBase {

	private boolean debug;
	private Logger logger = Logger.getLogger(NamingServerServiceImpl.class.getName());
	private NamingServices namingServices;
	private Integer[] servers = new Integer[3];

	/**
	 * NamingServerServiceImpl constructor.
	 * @param namingServices the naming server's available services
	 * @param debug the debug flag
	 */
	public NamingServerServiceImpl(NamingServices namingServices, boolean debug){
		this.namingServices = namingServices;
		this.debug = debug;
		for (int i = 0; i < servers.length; ++i)
			servers[i] = 0;
	}

	/**
	 * Register server service
	 * @param request
	 * @param responseObserver
	 */
    @Override
	public void register(RegisterRequest request, StreamObserver<RegisterResponse> responseObserver) {
		if (debug)
			logger.log(Level.INFO, "Register server request");

		int id = -1;
		for (int i = 0; i < servers.length; ++i)
			if (servers[i] == 0){
				servers[i] = 1;
				id = i;
				break;
			}
				
		if (id == -1){
			if (debug)
				logger.log(Level.WARNING, "The naming server is currently full.");
			responseObserver.onError(RESOURCE_EXHAUSTED.withDescription("The naming server is currently full.").asRuntimeException());
			return;
		}
		String name = request.getName();
		synchronized (namingServices) {
			ServiceEntry serviceEntry = namingServices.getService(name);
			if (serviceEntry == null) {
				serviceEntry = new ServiceEntry(name);
				namingServices.addService(serviceEntry);
			}

			ClassesServerInfo server = new ClassesServerInfo(request.getHost(), request.getPort(), request.getQualifiersList(), id);
			serviceEntry.addServer(server);
		}
		if (debug)
			logger.log(Level.INFO, "Server registered successfully with ID " + id);
		// Send a single response through the stream.
		responseObserver.onNext(RegisterResponse.newBuilder().setId(id).build());
		// Notify the client that the operation has been completed.
		responseObserver.onCompleted();
	}

	/**
	 * Lookup server service
	 * @param request
	 * @param responseObserver
	 */
	@Override
	public void lookup(LookupRequest request, StreamObserver<LookupResponse> responseObserver) {
		if (debug)
			logger.log(Level.INFO, "Lookup server request");

		String name = request.getName();
		LookupResponse.Builder responseBuilder = LookupResponse.newBuilder();
		synchronized (namingServices) {
			ServiceEntry serviceEntry = namingServices.getService(name);

			if (serviceEntry != null){
				Stream<ClassesServerInfo> serviceStream = serviceEntry.getServers().stream();
				if (request.getQualifiersList().size() > 0) {
					serviceStream = serviceStream.filter(s -> !Collections.disjoint(s.getQualifiers(), request.getQualifiersList()));
				}
				responseBuilder
						.addAllServers(serviceStream.map(s -> Server.newBuilder().setHost(s.getHost()).setPort(s.getPort()).setId(s.getID()).build())
						.collect(Collectors.toList()));
			}
		}

		// Send a single response through the stream.
		responseObserver.onNext(responseBuilder.build());
		// Notify the client that the operation has been completed.
		responseObserver.onCompleted();
	}

	/**
	 * Delete server service
	 * @param request
	 * @param responseObserver
	 */
	@Override
	public void delete(DeleteRequest request, StreamObserver<DeleteResponse> responseObserver) {
		if (debug)
			logger.log(Level.INFO, "Delete server request");

		String name = request.getName();
		synchronized (namingServices) {
			ServiceEntry serviceEntry = namingServices.getService(name);

			if (serviceEntry != null){
				serviceEntry.deleteServer(request.getHost(), request.getPort());
				servers[request.getId()] = 0;
				if (serviceEntry.getServers().size() == 0)
					namingServices.deleteService(name);
			}
		}

		// Send a single response through the stream.
		responseObserver.onNext(DeleteResponse.getDefaultInstance());
		// Notify the client that the operation has been completed.
		responseObserver.onCompleted();
	}

}