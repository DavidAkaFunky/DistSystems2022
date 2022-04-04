package pt.tecnico.addressbook.server.domain.exception;

public class UnknownPersonInfoException extends IllegalArgumentException {
    private final String email;

    public UnknownPersonInfoException(String email) {
        super("Person with email " + email + " is not registered in the address book");
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

}
