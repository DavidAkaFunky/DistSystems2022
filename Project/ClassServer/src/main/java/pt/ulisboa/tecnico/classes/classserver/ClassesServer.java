package pt.ulisboa.tecnico.classes.classserver;

import io.grpc.*;

import pt.ulisboa.tecnico.classes.NamingServerFrontend;

import java.util.ArrayList;
import java.util.Timer;
import java.util.regex.Pattern;
import java.util.concurrent.ConcurrentHashMap;

public class ClassesServer {

	private static final String DEBUG_FLAG = "-debug";

  	// The naming server host and port.
	private static final String NAMING_HOST = "localhost";
	private static final Integer NAMING_PORT = 5000;


	/**
	 * Start server, only taking the DEBUG_FLAG as a possible argument
	 * @param args The arguments passed when running the Classes Server (naming server's IP and port, the server qualifier and optionally, the debug flag).
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		System.out.println(ClassesServer.class.getSimpleName());

		int length = args.length;
		// Print received arguments.
		System.out.printf("Received %d arguments%n", length);
		for (int i = 0; i < length; ++i) {
			System.out.printf("arg[%d] = %s%n", i, args[i]);
		}

		// Check that the arguments are well-formatted
		if (length < 3 || (length <= 4 && !args[0].equals("P") && !args[0].equals("S"))  || !Pattern.matches("[0-9]+[\\.]?[0-9]*", args[2])){
			System.out.println("Wrong format! Try <<QUALIFIER>> <<HOST>> <<PORT>>.%n<<QUALIFIER>> must be P or S");
			return;
		}

		if (length > 4 || (length == 4 && !args[3].equals(DEBUG_FLAG))){
			System.out.println("Wrong format! Too many arguments.");
			return;
		}
	
		String SERVER_HOST = args[1];
		Integer SERVER_PORT = Integer.parseInt(args[2]);

		// If args.length == 4, since it passed the condition above,
		// it means that args[3].equals(DEBUG_FLAG), otherwise
		// length == 3 and the flag is set to false.
		boolean debug = length == 4;

		NamingServerFrontend namingServer = new NamingServerFrontend(NAMING_HOST, NAMING_PORT);

		ArrayList<String> qualifiers = new ArrayList<String>();
		qualifiers.add(args[0]);

		Integer[] vectorClock = new Integer[3];
		Integer[] replicaVectorClock = new Integer[vectorClock.length];
		for (int i = 0; i < vectorClock.length; ++i){
			vectorClock[i] = 0;
			replicaVectorClock[i] = 0;
		}

		Timer timer = new Timer();
    	Classes classes = new Classes();
		Classes replica = new Classes();
		ConcurrentHashMap<Integer, ClassesCommand> commands = new ConcurrentHashMap<>();

		ClassesServerFrontend frontend = new ClassesServerFrontend(classes, replica, replicaVectorClock, namingServer, "Turmas", SERVER_HOST, SERVER_PORT, qualifiers, commands, debug);
		timer.schedule(frontend, 5000, 5000);

		// Add server to naming server.
		int id = frontend.registerServer();
		if (id == -1)
			System.exit(1);
		
		final BindableService professorImpl = new ProfessorServiceImpl(classes, qualifiers, commands, vectorClock, id, frontend, debug);
		final BindableService studentImpl = new StudentServiceImpl(classes, commands, vectorClock, id, debug);
		final BindableService adminImpl = new AdminServiceImpl(classes, frontend, debug);

		// Create a new server to listen on port.
		ServerBuilder<?> serverBuilder = ServerBuilder.forPort(SERVER_PORT)
												   	  .addService(professorImpl)
				  								      .addService(studentImpl)
												   	  .addService(adminImpl);


		final BindableService classesServerImpl = new ClassesServerServiceImpl(classes, replica, commands, vectorClock, replicaVectorClock, id, debug);
		serverBuilder.addService(classesServerImpl);

		// Start the server.
		Server server = serverBuilder.build();
		try {
			server.start();
		} catch (Exception e) {
			System.out.println("This port is already in use.");
			frontend.deleteServer();
			namingServer.shutdownChannel();
			timer.cancel();
			System.exit(1);
		}

		// Server threads are running in the background.
		System.out.println("Server started");

		// Shutdown hook.
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			frontend.deleteServer();
			namingServer.shutdownChannel();
			server.shutdown();
			timer.cancel();
		}));

		// Do not exit the main thread. Wait until server is terminated.
		server.awaitTermination();
		
	}
  
}