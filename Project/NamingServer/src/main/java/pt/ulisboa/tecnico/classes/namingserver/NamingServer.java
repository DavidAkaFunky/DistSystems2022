package pt.ulisboa.tecnico.classes.namingserver;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

public class NamingServer {

    private static final String DEBUG_FLAG = "-debug";

    // Server host port.
    private static final Integer PORT = 5000;

    /**
     * Start server, only taking the DEBUG_FLAG as a possible argument.
     * @param args The arguments passed when running the naming server.
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        System.out.println(NamingServer.class.getSimpleName());

        int length = args.length;
        // Print received arguments.
        System.out.printf("Received %d arguments%n", length);
        for (int i = 0; i < length; ++i) {
            System.out.printf("arg[%d] = %s%n", i, args[i]);
        }

        // The only accepted argument is DEBUG_FLAG.
        if (length > 1 || (length == 1 && !args[0].equals(DEBUG_FLAG))){
            System.out.println("Wrong format! '-debug' is the only accepted flag.");
            return;
        }

        // If args.length == 1, since it passed the condition above,
        // it means that args[0].equals(DEBUG_FLAG), otherwise
        // args.length == 0 and the flag is set to false.
        boolean debug = length == 1;

        NamingServices namingServices = new NamingServices();
        final BindableService namingServerImpl = new NamingServerServiceImpl(namingServices, debug);

        // Create a new server to listen on port.
        Server server = ServerBuilder.forPort(PORT).addService(namingServerImpl).build();
        // Start the server.
		try {
			server.start();
		} catch (Exception e) {
			System.out.println("Port 5000 is already in use.");
			System.exit(1);
		}
        // Server threads are running in the background.
        System.out.println("Server started");

        // Do not exit the main thread. Wait until server is terminated.
        server.awaitTermination();
    }
}
