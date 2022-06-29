package pt.ulisboa.tecnico.classes;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import pt.ulisboa.tecnico.classes.contract.naming.ClassServerNamingServer.*;
import pt.ulisboa.tecnico.classes.contract.naming.NamingServerServiceGrpc;
import pt.ulisboa.tecnico.classes.contract.ClassesDefinitions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NamingServerFrontend {

    private ManagedChannel channel;
    private NamingServerServiceGrpc.NamingServerServiceBlockingStub namingServerStub;
    private Random random = new Random();

    /**
     * Naming Server Frontend constructor.
     * @param host the naming server's host.
     * @param port the naming server's port.
     */
    public NamingServerFrontend(String host, int port){
        channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
        namingServerStub = NamingServerServiceGrpc.newBlockingStub(channel);
    }

    /**
     * Chooses one of the available servers (at random).
     * @param servers the servers tho chose from.
     * @return the server chosen
     */
    public Server chooseServer(List<Server> servers){
        return servers.get(random.nextInt(servers.size()));
    }

    /**
     * Registers a server in the naming server.
     * @param service the server's service.
     * @param host the server's host.
     * @param port the server's port.
     * @param qualifiers the server's qualifiers.
     * @throws StatusRuntimeException
     * @return the id of the server registered
     */
    public int register(String service, String host, int port, ArrayList<String> qualifiers) throws StatusRuntimeException {
        RegisterRequest request = RegisterRequest.newBuilder()
                .setName(service).setHost(host).setPort(port).addAllQualifiers(qualifiers).build();
        return namingServerStub.register(request).getId();
    }

    /**
     * Deletes a server in the naming server.
     * @param service the server's service
     * @param host the server's host
     * @param port the server's port
     * @param id the server's id
     * @throws StatusRuntimeException
     */
    public void delete(String service, String host, int port, int id) throws StatusRuntimeException {
        namingServerStub.delete(DeleteRequest.newBuilder()
                .setName(service).setHost(host).setPort(port).setId(id).build());
    }

    /**
     * Looks up the servers avaiable for a certain service with the given qualifiers.
     * @param serviceName the service name.
     * @param qualifiers the server's qualifiers to look from.
     * @return the list of servers for that service with the given qualifiers.
     */
    public List<Server> lookup(String serviceName, ArrayList<String> qualifiers){
        LookupRequest request = LookupRequest.newBuilder()
                .addAllQualifiers(qualifiers).setName(serviceName).build();

        return new ArrayList<Server>(namingServerStub.lookup(request).getServersList());
    }

    /**
     * Shuts down the channel.
     */
    public void shutdownChannel(){
        channel.shutdown();
    }

    /**
     * Connects to a specified server.
     * @param server the server to connect to.
     */
    public ManagedChannel connect(Server server){
        return ManagedChannelBuilder.forAddress(server.getHost(), server.getPort()).usePlaintext().build();
    }

}
