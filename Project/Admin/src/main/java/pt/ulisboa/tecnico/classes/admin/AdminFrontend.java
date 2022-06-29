package pt.ulisboa.tecnico.classes.admin;

import pt.ulisboa.tecnico.classes.NamingServerFrontend;
import pt.ulisboa.tecnico.classes.contract.admin.AdminServiceGrpc;
import pt.ulisboa.tecnico.classes.contract.admin.AdminServiceGrpc.AdminServiceBlockingStub;
import pt.ulisboa.tecnico.classes.contract.admin.AdminClassServer.*;
import pt.ulisboa.tecnico.classes.contract.ClassesDefinitions.*;
import pt.ulisboa.tecnico.classes.Stringify;

import java.util.logging.Logger;
import java.util.logging.Level;

import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AdminFrontend {
    private NamingServerFrontend namingServer;
    private boolean debug;
    private Logger logger = Logger.getLogger(AdminFrontend.class.getName());

    /**
     * Admin Frontend constructor.
     * @param namingServer a frontend where the lookup method will be called to the naming server
     * @param debug  set to true to display a message when each method is called
     */
    public AdminFrontend(NamingServerFrontend namingServer, boolean debug){
        this.namingServer = namingServer;
        this.debug = debug;
    }

    /**
     * Send the servers with the specified qualifier a message to activate themselves.
     * @param qualifier the qualifier of the server(s) to be activated.
     */
    public void activate(String qualifier){
        if (debug)
            logger.log(Level.INFO, "Sending activate server request...");
        try{
            ArrayList<String> qualifiers = new ArrayList<String>();
            qualifiers.add(qualifier);
            List<Server> servers = namingServer.lookup("Turmas", qualifiers);
            for (Server server: servers) {
                ManagedChannel channel = namingServer.connect(server);
                AdminServiceBlockingStub stub = AdminServiceGrpc.newBlockingStub(channel);
                try {
                    ResponseCode code = stub.withDeadlineAfter(2000, TimeUnit.MILLISECONDS).activate(ActivateRequest.getDefaultInstance()).getCode();
                    channel.shutdown();
                    System.out.println(Stringify.format(code));
                } catch (StatusRuntimeException e) {
                    System.out.println("Caught exception with description: " +
                            e.getStatus().getDescription());
                }
            }
        } catch (StatusRuntimeException e) {
            System.out.println("Caught exception with description: " +
                    e.getStatus().getDescription() + ". Naming Server isn't working.");
        }
    }

    /**
     * Send the servers with the specified qualifier a message to deactivate themselves (simulating a crash).
     * @param qualifier the qualifier of the server(s) to be deactivated.
     */
    public void deactivate(String qualifier){
        if (debug)
            logger.log(Level.INFO, "Sending deactivate server request...");
        try{
            ArrayList<String> qualifiers = new ArrayList<String>();
            qualifiers.add(qualifier);
            List<Server> servers = namingServer.lookup("Turmas", qualifiers);
            for (Server server: servers) {
                ManagedChannel channel = namingServer.connect(server);
                AdminServiceBlockingStub stub = AdminServiceGrpc.newBlockingStub(channel);
                try {
                    ResponseCode code = stub.withDeadlineAfter(2000, TimeUnit.MILLISECONDS).deactivate(DeactivateRequest.getDefaultInstance()).getCode();
                    channel.shutdown();
                    System.out.println(Stringify.format(code));
                } catch (StatusRuntimeException e) {
                    System.out.println("Caught exception with description: " +
                            e.getStatus().getDescription());
                }
            }
        } catch (StatusRuntimeException e) {
            System.out.println("Caught exception with description: " +
                    e.getStatus().getDescription() + ". Naming Server isn't working.");
        }
    }

    /**
     * Send the servers with the specified type a message to activate the gossip mechanism.
     * @param qualifier the qualifier of the server(s) to activate gossip.
     */
    public void activateGossip(String qualifier){
        if (debug)
            logger.log(Level.INFO, "Sending activate gossip request...");
        try{
            ArrayList<String> qualifiers = new ArrayList<String>();
            qualifiers.add(qualifier);
            List<Server> servers = namingServer.lookup("Turmas", qualifiers);
            for (Server server: servers) {
                ManagedChannel channel = namingServer.connect(server);
                AdminServiceBlockingStub stub = AdminServiceGrpc.newBlockingStub(channel);
                try {
                    ResponseCode code = stub.withDeadlineAfter(2000, TimeUnit.MILLISECONDS).activateGossip(ActivateGossipRequest.getDefaultInstance()).getCode();
                    channel.shutdown();
                    System.out.println(Stringify.format(code));
                } catch (StatusRuntimeException e) {
                    System.out.println("Caught exception with description: " +
                            e.getStatus().getDescription());
                }
            }
        } catch (StatusRuntimeException e) {
            System.out.println("Caught exception with description: " +
                    e.getStatus().getDescription() + ". Naming Server isn't working.");
        }
    }

    /**
     * Send the servers with the specified type a message to deactivate the gossip mechanism.
     * @param qualifier the qualifier of the server(s) to deactivate gossip.
     */
    public void deactivateGossip(String qualifier){
        if (debug)
            logger.log(Level.INFO, "Sending deactivate gossip request...");
        try{
            ArrayList<String> qualifiers = new ArrayList<String>();
            qualifiers.add(qualifier);
            List<Server> servers = namingServer.lookup("Turmas", qualifiers);
            for (Server server: servers) {
                ManagedChannel channel = namingServer.connect(server);
                AdminServiceBlockingStub stub = AdminServiceGrpc.newBlockingStub(channel);
                try {
                    ResponseCode code = stub.withDeadlineAfter(2000, TimeUnit.MILLISECONDS).deactivateGossip(DeactivateGossipRequest.getDefaultInstance()).getCode();
                    channel.shutdown();
                    System.out.println(Stringify.format(code));
                } catch (StatusRuntimeException e) {
                    System.out.println("Caught exception with description: " +
                            e.getStatus().getDescription());
                }
            }
        } catch (StatusRuntimeException e) {
            System.out.println("Caught exception with description: " +
                    e.getStatus().getDescription() + ". Naming Server isn't working.");
        }
    }

    /**
     * Send the servers with the specified type a message to force gossip.
     * @param qualifier the qualifier of the server(s) to force gossip.
     */
    public void gossip(String qualifier){
        if (debug)
            logger.log(Level.INFO, "Sending gossip request...");
        try{
            ArrayList<String> qualifiers = new ArrayList<String>();
            qualifiers.add(qualifier);
            List<Server> servers = namingServer.lookup("Turmas", qualifiers);
            for (Server server: servers) {
                ManagedChannel channel = namingServer.connect(server);
                AdminServiceBlockingStub stub = AdminServiceGrpc.newBlockingStub(channel);
                try {
                    ResponseCode code = stub.withDeadlineAfter(2000, TimeUnit.MILLISECONDS).gossip(GossipRequest.getDefaultInstance()).getCode();
                    channel.shutdown();
                    System.out.println(Stringify.format(code));
                } catch (StatusRuntimeException e) {
                    System.out.println("Caught exception with description: " +
                            e.getStatus().getDescription());
                }
            }
        } catch (StatusRuntimeException e) {
            System.out.println("Caught exception with description: " +
                    e.getStatus().getDescription() + ". Naming Server isn't working.");
        }
    }


    /**
     * List all enrolled and discarded students (independently of the server's activity mode)
     * @param qualifier the qualifier of the server(s) to collect information from.
     */
    public void dump(String qualifier){
        if (debug)
            logger.log(Level.INFO, "Sending dump request...");
        try{
            ArrayList<String> qualifiers = new ArrayList<String>();
            qualifiers.add(qualifier);
            List<Server> servers = namingServer.lookup("Turmas", qualifiers);
            if (servers.size() == 0){
                System.out.println("ERROR: There are no servers to get information from.");
                return;
            }
            for (Server server: servers) {
                ManagedChannel channel = namingServer.connect(server);
                AdminServiceBlockingStub stub = AdminServiceGrpc.newBlockingStub(channel);
                try {
                    DumpResponse response = stub.withDeadlineAfter(2000, TimeUnit.MILLISECONDS).dump(DumpRequest.getDefaultInstance());
                    channel.shutdown();
                    System.out.println(Stringify.format(response.getClassState()));
                } catch (StatusRuntimeException e) {
                    System.out.println("Caught exception with description: " +
                            e.getStatus().getDescription());
                }
            }
        } catch (StatusRuntimeException e) {
            System.out.println("Caught exception with description: " +
                    e.getStatus().getDescription() + ". Naming Server isn't working.");
        }
    }

}
