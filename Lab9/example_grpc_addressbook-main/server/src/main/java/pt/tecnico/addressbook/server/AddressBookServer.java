package pt.tecnico.addressbook.server;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class AddressBookServer {

    public static void main(String[] args) {
        System.out.println(AddressBookServer.class.getSimpleName());
        System.out.printf("Received %d argument(s)%n", args.length);
        for (int i = 0; i < args.length; i++) {
            System.out.printf("args[%d] = %s%n", i, args[i]);
        }
        if (args.length < 1) {
            System.err.println("Missing arguments.");
            System.exit(1);
        }

        int port = Integer.parseInt(args[0]);

        final BindableService impl = new AddressBookServiceImpl();

        Server server = null;
        try {

            server = ServerBuilder.forPort(port).addService(impl).build();

            server.start();
            System.out.println("Server started");
            System.out.println();

            // Wait until server is terminated.
            System.out.println("Press enter to shutdown");
            System.in.read();
            server.shutdown();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (server != null)
                server.shutdown();
        }
    }
}
