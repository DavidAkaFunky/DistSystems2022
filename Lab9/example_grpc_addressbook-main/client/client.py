import grpc
import sys
sys.path.insert(1, '../contract/target/generated-sources/protobuf/python')

import AddressBook_pb2 as pb2
import AddressBook_pb2_grpc as pb2_grpc


class PythonClient(object):
    """
    Client for gRPC functionality
    """

    def __init__(self, host, port):
        self.host = host
        self.server_port = port

        # instantiate a channel
        self.channel = grpc.insecure_channel(
            '{}:{}'.format(self.host, self.server_port))

        # bind the client and the server
        self.stub = pb2_grpc.AddressBookServiceStub(self.channel)

    def close_channel(self):
        """
        Client function to close channel
        """
        self.channel.close()

    def list_people(self):
        """
        Client function to call the rpc for listPeople
        """
        request = pb2.ListPeopleRequest()
        try:
            response = self.stub.listPeople(request)

            for person in response.people:
                print_person(person)

        except grpc.RpcError as rpc_error:
            print('ERROR: code={}, description{}'.format(rpc_error.code(), rpc_error.details()))

    def add_person(self):
        """
        Client function to call the rpc for addPerson
        """
        person = pb2.PersonInfo()
        person.name = input("Enter name: ")
        person.email = input("Enter email: ")

        type = input("Enter phone type: [mobile/home/work] ")
        if type == "mobile":
            person.phone.type = pb2.PersonInfo.PhoneType.MOBILE
        elif type == "home":
            person.phone.type = pb2.PersonInfo.PhoneType.HOME
        elif type == "work":
            person.phone.type = pb2.PersonInfo.PhoneType.WORK
        else:
            print("Unknown phone type; leaving as default value.")
            return

        type = input("Enter other phone type: [mobile/home/work] ")
        if type == "mobile":
            person.otherPhone.type = pb2.PersonInfo.PhoneType.MOBILE
        elif type == "home":
            person.otherPhone.type = pb2.PersonInfo.PhoneType.HOME
        elif type == "work":
            person.otherPhone.type = pb2.PersonInfo.PhoneType.WORK
        else:
            print("Unknown phone type; leaving as default value.")
            return

        try:
            person.phone.number = int(input("Enter phone number: "))
            person.otherPhone.number = int(input("Enter other phone number: "))
            self.stub.addPerson(person)
        except ValueError:
            print('Error: Please enter a valid number.')
        except grpc.RpcError as rpc_error:
            print('ERROR: code={}, description={}'.format(rpc_error.code(), rpc_error.details()))

    def search_person(self):
        request = pb2.SearchPersonRequest()
        request.email = input("Enter email: ")
        try:
            response = self.stub.searchPerson(request)
            print_person(response)

        except grpc.RpcError as rpc_error:
            print('ERROR: code={}, description{}'.format(rpc_error.code(), rpc_error.details()))

    def delete_person(self):
        request = pb2.SearchPersonRequest()
        request.email = input("Enter email: ")
        try:
            self.stub.deletePerson(request)
            print("Person deleted successfully.")

        except grpc.RpcError as rpc_error:
            print('ERROR: code={}, description{}'.format(rpc_error.code(), rpc_error.details()))

    def remove_all(self):
        request = pb2.RemoveAllRequest()
        request.name = input("Enter name: ")
        try:
            self.stub.removeAll(request)
            print("Everyone with the given name has been removed successfully.")

        except grpc.RpcError as rpc_error:
            print('ERROR: code={}, description{}'.format(rpc_error.code(), rpc_error.details()))

def print_person(person):
    """
    Print person's information.
    """
    print("  Name:", person.name)
    print("  E-mail address:", person.email)

    if person.phone.type == pb2.PersonInfo.PhoneType.MOBILE:
        print("  Mobile phone #: ", end="")
    elif person.phone.type == pb2.PersonInfo.PhoneType.HOME:
        print("  Home phone #: ", end="")
    elif person.phone.type == pb2.PersonInfo.PhoneType.WORK:
        print("  Work phone #: ", end="")
    print(person.phone.number)

    if person.otherPhone.type == pb2.PersonInfo.PhoneType.MOBILE:
        print("  Mobile other phone #: ", end="")
    elif person.otherPhone.type == pb2.PersonInfo.PhoneType.HOME:
        print("  Home other phone #: ", end="")
    elif person.otherPhone.type == pb2.PersonInfo.PhoneType.WORK:
        print("  Work other phone #: ", end="")
    print(person.otherPhone.number)


def get_user_choice():
    """
    Let users know what they can do.
    """
    print("\n[1] See a list of addresses.")
    print("[2] Add person's address.")
    print("[3] Search person's address.")
    print("[4] Delete a person from the list.")
    print("[5] Remove all people with a given name.")
    print("[q] Quit.")

    return input("What would you like to do? ")


if __name__ == '__main__':
    client = PythonClient('localhost', 8080)
    # Set up a loop where users can choose what they'd like to do.
    choice = ''
    while choice != 'q':

        choice = get_user_choice()

        # Respond to the user's choice.
        if choice == '1':
            client.list_people()
        elif choice == '2':
            client.add_person()
        elif choice == '3':
            client.search_person()
        elif choice == '4':
            client.delete_person()
        elif choice == '5':
            client.remove_all()
        elif choice == 'q':
            print("\nBye.")
        else:
            print("\nI didn't understand that choice.\n")

    client.close_channel()
