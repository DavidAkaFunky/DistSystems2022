package pt.ulisboa.tecnico.classes.namingserver;

import java.util.concurrent.ConcurrentHashMap;

public class NamingServices {
    private ConcurrentHashMap<String, ServiceEntry> services = new ConcurrentHashMap<String, ServiceEntry>();

    public NamingServices(){}

    public void addService(ServiceEntry serviceEntry){
        services.put(serviceEntry.getName(),serviceEntry);
    }

    public void deleteService(String name){
        services.remove(name);
    }

    public ServiceEntry getService(String name){
        return services.get(name);
    }

}

