// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: AddressBook.proto

package pt.tecnico.addressbook.grpc;

public final class AddressBookProtobuf {
  private AddressBookProtobuf() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_pt_tecnico_addressbook_grpc_PersonInfo_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_pt_tecnico_addressbook_grpc_PersonInfo_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_pt_tecnico_addressbook_grpc_PersonInfo_PhoneNumber_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_pt_tecnico_addressbook_grpc_PersonInfo_PhoneNumber_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_pt_tecnico_addressbook_grpc_AddressBookList_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_pt_tecnico_addressbook_grpc_AddressBookList_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_pt_tecnico_addressbook_grpc_ListPeopleRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_pt_tecnico_addressbook_grpc_ListPeopleRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_pt_tecnico_addressbook_grpc_SearchPersonRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_pt_tecnico_addressbook_grpc_SearchPersonRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_pt_tecnico_addressbook_grpc_AddPersonResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_pt_tecnico_addressbook_grpc_AddPersonResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_pt_tecnico_addressbook_grpc_DeletePersonRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_pt_tecnico_addressbook_grpc_DeletePersonRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_pt_tecnico_addressbook_grpc_DeletePersonResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_pt_tecnico_addressbook_grpc_DeletePersonResponse_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\021AddressBook.proto\022\033pt.tecnico.addressb" +
      "ook.grpc\"\372\001\n\nPersonInfo\022\014\n\004name\030\001 \001(\t\022\r\n" +
      "\005email\030\003 \001(\t\022B\n\005phone\030\004 \001(\01323.pt.tecnico" +
      ".addressbook.grpc.PersonInfo.PhoneNumber" +
      "\032^\n\013PhoneNumber\022\016\n\006number\030\001 \001(\005\022?\n\004type\030" +
      "\002 \001(\01621.pt.tecnico.addressbook.grpc.Pers" +
      "onInfo.PhoneType\"+\n\tPhoneType\022\n\n\006MOBILE\020" +
      "\000\022\010\n\004HOME\020\001\022\010\n\004WORK\020\002\"J\n\017AddressBookList" +
      "\0227\n\006people\030\001 \003(\0132\'.pt.tecnico.addressboo" +
      "k.grpc.PersonInfo\"\023\n\021ListPeopleRequest\"$" +
      "\n\023SearchPersonRequest\022\r\n\005email\030\001 \001(\t\"\023\n\021" +
      "AddPersonResponse\"$\n\023DeletePersonRequest" +
      "\022\r\n\005email\030\001 \001(\t\"\026\n\024DeletePersonResponse2" +
      "\306\003\n\022AddressBookService\022j\n\nlistPeople\022..p" +
      "t.tecnico.addressbook.grpc.ListPeopleReq" +
      "uest\032,.pt.tecnico.addressbook.grpc.Addre" +
      "ssBookList\022d\n\taddPerson\022\'.pt.tecnico.add" +
      "ressbook.grpc.PersonInfo\032..pt.tecnico.ad" +
      "dressbook.grpc.AddPersonResponse\022i\n\014sear" +
      "chPerson\0220.pt.tecnico.addressbook.grpc.S" +
      "earchPersonRequest\032\'.pt.tecnico.addressb" +
      "ook.grpc.PersonInfo\022s\n\014deletePerson\0220.pt" +
      ".tecnico.addressbook.grpc.DeletePersonRe" +
      "quest\0321.pt.tecnico.addressbook.grpc.Dele" +
      "tePersonResponseB4\n\033pt.tecnico.addressbo" +
      "ok.grpcB\023AddressBookProtobufP\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_pt_tecnico_addressbook_grpc_PersonInfo_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_pt_tecnico_addressbook_grpc_PersonInfo_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_pt_tecnico_addressbook_grpc_PersonInfo_descriptor,
        new java.lang.String[] { "Name", "Email", "Phone", });
    internal_static_pt_tecnico_addressbook_grpc_PersonInfo_PhoneNumber_descriptor =
      internal_static_pt_tecnico_addressbook_grpc_PersonInfo_descriptor.getNestedTypes().get(0);
    internal_static_pt_tecnico_addressbook_grpc_PersonInfo_PhoneNumber_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_pt_tecnico_addressbook_grpc_PersonInfo_PhoneNumber_descriptor,
        new java.lang.String[] { "Number", "Type", });
    internal_static_pt_tecnico_addressbook_grpc_AddressBookList_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_pt_tecnico_addressbook_grpc_AddressBookList_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_pt_tecnico_addressbook_grpc_AddressBookList_descriptor,
        new java.lang.String[] { "People", });
    internal_static_pt_tecnico_addressbook_grpc_ListPeopleRequest_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_pt_tecnico_addressbook_grpc_ListPeopleRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_pt_tecnico_addressbook_grpc_ListPeopleRequest_descriptor,
        new java.lang.String[] { });
    internal_static_pt_tecnico_addressbook_grpc_SearchPersonRequest_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_pt_tecnico_addressbook_grpc_SearchPersonRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_pt_tecnico_addressbook_grpc_SearchPersonRequest_descriptor,
        new java.lang.String[] { "Email", });
    internal_static_pt_tecnico_addressbook_grpc_AddPersonResponse_descriptor =
      getDescriptor().getMessageTypes().get(4);
    internal_static_pt_tecnico_addressbook_grpc_AddPersonResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_pt_tecnico_addressbook_grpc_AddPersonResponse_descriptor,
        new java.lang.String[] { });
    internal_static_pt_tecnico_addressbook_grpc_DeletePersonRequest_descriptor =
      getDescriptor().getMessageTypes().get(5);
    internal_static_pt_tecnico_addressbook_grpc_DeletePersonRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_pt_tecnico_addressbook_grpc_DeletePersonRequest_descriptor,
        new java.lang.String[] { "Email", });
    internal_static_pt_tecnico_addressbook_grpc_DeletePersonResponse_descriptor =
      getDescriptor().getMessageTypes().get(6);
    internal_static_pt_tecnico_addressbook_grpc_DeletePersonResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_pt_tecnico_addressbook_grpc_DeletePersonResponse_descriptor,
        new java.lang.String[] { });
  }

  // @@protoc_insertion_point(outer_class_scope)
}