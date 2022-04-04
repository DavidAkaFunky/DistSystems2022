package pt.tecnico.addressbook.server.domain;

import pt.tecnico.addressbook.grpc.AddressBookList;
import pt.tecnico.addressbook.grpc.PersonInfo.PhoneType;
import pt.tecnico.addressbook.server.domain.exception.*;

import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class AddressBook {

    private ConcurrentHashMap<String, Person> people = new ConcurrentHashMap<>();

    public AddressBook() {
    }

    public void addPerson(String name, String email, int phoneNumber, PhoneType type, int otherPhoneNumber, PhoneType otherType) throws DuplicatePersonInfoException {
        if(people.putIfAbsent(email, new Person(name, email, phoneNumber, type, otherPhoneNumber, otherType)) != null) {
            throw new DuplicatePersonInfoException(email);
        }
    }

    public Person searchPerson(String email) throws UnknownPersonInfoException {
        Person person = people.get(email);
        if (person == null){
            throw new UnknownPersonInfoException(email);
        }
        return person;
    }

    public void deletePerson(String email) throws UnknownPersonInfoException {
        if (people.remove(email) == null){
            throw new UnknownPersonInfoException(email);
        }
    }

    public void removeAll(String name) throws UnknownPersonNameException {
        boolean erased = false;
        for (Person person : people.values()) {
            if (person.getName().equals(name)){
                people.remove(person.getEmail());
                erased = true;
            }
        }
        if (!erased){
            throw new UnknownPersonNameException(name);
        }
    }


    public AddressBookList proto() {
        return AddressBookList.newBuilder()
                .addAllPeople(people.values().stream().map(Person::proto).collect(Collectors.toList()))
                .build();
    }

    
}
