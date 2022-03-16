package pt.tecnico.grpc.server;

import pt.tecnico.grpc.Banking.RegisterResponse;

import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class Bank {
    private static final Logger LOGGER = Logger.getLogger(Bank.class.getName());
    ConcurrentHashMap<String, Integer> clients = new ConcurrentHashMap<>();

    public boolean isClient(String client){
        return clients.containsKey(client);
    }

    public void register(String client, Integer balance) {
        clients.put(client, balance);
        LOGGER.info("User: " + client + " has been instantiated with balance: " + balance);
    }

    public Integer getBalance(String client){
        return clients.get(client);
    }

    public boolean subsidize(int threshold, int amount){
        boolean subsidized = false;
        for (ConcurrentHashMap.Entry<String, Integer> set: clients.entrySet()) {
            String client = set.getKey();
            int balance = getBalance(client);
            if (balance < threshold) {
                clients.put(client, balance + amount);
                subsidized = true;
            }
        }
        return subsidized;
    }
}
