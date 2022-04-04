package pt.tecnico.addressbook.server.domain.exception;

public class UnknownPersonNameException extends IllegalArgumentException {
    private final String name;

    public UnknownPersonNameException(String name) {
        super("Person with name " + name + " is not registered in the address book");
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
