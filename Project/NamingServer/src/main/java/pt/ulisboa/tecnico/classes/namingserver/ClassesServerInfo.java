package pt.ulisboa.tecnico.classes.namingserver;

import java.util.List;

public class ClassesServerInfo {
    private String host;
    private Integer port;
    private List<String> qualifiers;
    private Integer id;

    /**
     * ClassesServerInfo constructor.
     * @param host the server's host
     * @param port the server's port
     * @param qualifiers the server's qualifiers
     * @param id the server's identifier
     */
    public ClassesServerInfo(String host, Integer port, List<String> qualifiers, Integer id){
        this.host = host;
        this.port = port;
        this.qualifiers = qualifiers;
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public Integer getPort() {
        return port;
    }

    public List<String> getQualifiers() {
        return qualifiers;
    }

    public Integer getID() {
        return id;
    }
}
