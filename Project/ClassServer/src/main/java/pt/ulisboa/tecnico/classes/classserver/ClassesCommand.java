package pt.ulisboa.tecnico.classes.classserver;

import java.time.Instant;
import com.google.protobuf.Timestamp;

public class ClassesCommand {
    private Timestamp timestamp;
    private String command;

    /**
     * ClassesCommand constructor, generating a timestamp
     * with the instant the command was executed.
     * Used in the gossip protocol.
     * @param command a string used to recreate the client's command.
     */
    public ClassesCommand(String command){
        Instant now = Instant.now();
        this.timestamp = Timestamp.newBuilder()
                                  .setSeconds(now.getEpochSecond())
                                  .setNanos(now.getNano())
                                  .build();
        this.command = command;
    }

    public Timestamp getTimestamp(){
        return timestamp;
    }

    public String getCommand(){
        return command;
    }
}
