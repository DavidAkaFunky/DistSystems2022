package pt.tecnico.addressbook.server.domain.exception;

public class DuplicatePersonInfoException extends IllegalArgumentException {
    private final String email;

    public DuplicatePersonInfoException(String email) {
        super("Person with email " + email + " already registered in the address book");
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

}
