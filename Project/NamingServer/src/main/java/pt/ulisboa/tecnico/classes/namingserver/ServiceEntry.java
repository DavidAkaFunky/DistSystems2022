package pt.ulisboa.tecnico.classes.namingserver;

import java.util.ArrayList;
import java.util.List;

public class ServiceEntry {
    private String name;
    private List<ClassesServerInfo> servers = new ArrayList<ClassesServerInfo>();

    public ServiceEntry(String name){
        this.name = name;
    }

    public void addServer(ClassesServerInfo server){
        servers.add(server);
    }

    public String getName() {
        return name;
    }

    public List<ClassesServerInfo> getServers() {
        return servers;
    }

    public void deleteServer(String host, int port){
        servers.removeIf(s -> s.getHost().equals(host) && s.getPort() == port);
    }

}
