package pt.tecnico.addressbook.server;

import io.grpc.stub.StreamObserver;
import pt.tecnico.addressbook.grpc.*;
import pt.tecnico.addressbook.grpc.AddressBookServiceGrpc.AddressBookServiceImplBase;
import pt.tecnico.addressbook.server.domain.AddressBook;
import pt.tecnico.addressbook.server.domain.exception.*;

import static io.grpc.Status.INVALID_ARGUMENT;

public class AddressBookServiceImpl extends AddressBookServiceImplBase {

    AddressBook addressBook = new AddressBook();

    @Override
    public void listPeople(ListPeopleRequest request, StreamObserver<AddressBookList> responseObserver) {
        AddressBookList response = addressBook.proto();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void addPerson(PersonInfo request, StreamObserver<AddPersonResponse> responseObserver) {
        try {
            addressBook.addPerson(request.getName(), request.getEmail(), request.getPhone().getNumber(), request.getPhone().getType());
            AddPersonResponse response = AddPersonResponse.getDefaultInstance();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }

        catch (DuplicatePersonInfoException e) {
            responseObserver.onError(INVALID_ARGUMENT.withDescription(e.getMessage()).asRuntimeException());
        }
    }

    @Override
    public void searchPerson(SearchPersonRequest request, StreamObserver<PersonInfo> responseObserver) {
        try {
            PersonInfo response = addressBook.searchPerson(request.getEmail()).proto();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }

        catch (UnknownPersonInfoException e) {
            responseObserver.onError(INVALID_ARGUMENT.withDescription(e.getMessage()).asRuntimeException());
        }
    }

    @Override
    public void deletePerson(DeletePersonRequest request, StreamObserver<DeletePersonResponse> responseObserver) {
        try {
            addressBook.deletePerson(request.getEmail());
            DeletePersonResponse response = DeletePersonResponse.getDefaultInstance();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }

        catch (UnknownPersonInfoException e) {
            responseObserver.onError(INVALID_ARGUMENT.withDescription(e.getMessage()).asRuntimeException());
        }
    }
}
