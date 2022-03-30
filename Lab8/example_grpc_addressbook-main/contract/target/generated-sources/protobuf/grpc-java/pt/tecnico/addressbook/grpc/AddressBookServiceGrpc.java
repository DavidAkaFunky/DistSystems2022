package pt.tecnico.addressbook.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.44.1)",
    comments = "Source: AddressBook.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class AddressBookServiceGrpc {

  private AddressBookServiceGrpc() {}

  public static final String SERVICE_NAME = "pt.tecnico.addressbook.grpc.AddressBookService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<pt.tecnico.addressbook.grpc.ListPeopleRequest,
      pt.tecnico.addressbook.grpc.AddressBookList> getListPeopleMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "listPeople",
      requestType = pt.tecnico.addressbook.grpc.ListPeopleRequest.class,
      responseType = pt.tecnico.addressbook.grpc.AddressBookList.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<pt.tecnico.addressbook.grpc.ListPeopleRequest,
      pt.tecnico.addressbook.grpc.AddressBookList> getListPeopleMethod() {
    io.grpc.MethodDescriptor<pt.tecnico.addressbook.grpc.ListPeopleRequest, pt.tecnico.addressbook.grpc.AddressBookList> getListPeopleMethod;
    if ((getListPeopleMethod = AddressBookServiceGrpc.getListPeopleMethod) == null) {
      synchronized (AddressBookServiceGrpc.class) {
        if ((getListPeopleMethod = AddressBookServiceGrpc.getListPeopleMethod) == null) {
          AddressBookServiceGrpc.getListPeopleMethod = getListPeopleMethod =
              io.grpc.MethodDescriptor.<pt.tecnico.addressbook.grpc.ListPeopleRequest, pt.tecnico.addressbook.grpc.AddressBookList>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "listPeople"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pt.tecnico.addressbook.grpc.ListPeopleRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pt.tecnico.addressbook.grpc.AddressBookList.getDefaultInstance()))
              .setSchemaDescriptor(new AddressBookServiceMethodDescriptorSupplier("listPeople"))
              .build();
        }
      }
    }
    return getListPeopleMethod;
  }

  private static volatile io.grpc.MethodDescriptor<pt.tecnico.addressbook.grpc.PersonInfo,
      pt.tecnico.addressbook.grpc.AddPersonResponse> getAddPersonMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "addPerson",
      requestType = pt.tecnico.addressbook.grpc.PersonInfo.class,
      responseType = pt.tecnico.addressbook.grpc.AddPersonResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<pt.tecnico.addressbook.grpc.PersonInfo,
      pt.tecnico.addressbook.grpc.AddPersonResponse> getAddPersonMethod() {
    io.grpc.MethodDescriptor<pt.tecnico.addressbook.grpc.PersonInfo, pt.tecnico.addressbook.grpc.AddPersonResponse> getAddPersonMethod;
    if ((getAddPersonMethod = AddressBookServiceGrpc.getAddPersonMethod) == null) {
      synchronized (AddressBookServiceGrpc.class) {
        if ((getAddPersonMethod = AddressBookServiceGrpc.getAddPersonMethod) == null) {
          AddressBookServiceGrpc.getAddPersonMethod = getAddPersonMethod =
              io.grpc.MethodDescriptor.<pt.tecnico.addressbook.grpc.PersonInfo, pt.tecnico.addressbook.grpc.AddPersonResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "addPerson"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pt.tecnico.addressbook.grpc.PersonInfo.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pt.tecnico.addressbook.grpc.AddPersonResponse.getDefaultInstance()))
              .setSchemaDescriptor(new AddressBookServiceMethodDescriptorSupplier("addPerson"))
              .build();
        }
      }
    }
    return getAddPersonMethod;
  }

  private static volatile io.grpc.MethodDescriptor<pt.tecnico.addressbook.grpc.SearchPersonRequest,
      pt.tecnico.addressbook.grpc.PersonInfo> getSearchPersonMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "searchPerson",
      requestType = pt.tecnico.addressbook.grpc.SearchPersonRequest.class,
      responseType = pt.tecnico.addressbook.grpc.PersonInfo.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<pt.tecnico.addressbook.grpc.SearchPersonRequest,
      pt.tecnico.addressbook.grpc.PersonInfo> getSearchPersonMethod() {
    io.grpc.MethodDescriptor<pt.tecnico.addressbook.grpc.SearchPersonRequest, pt.tecnico.addressbook.grpc.PersonInfo> getSearchPersonMethod;
    if ((getSearchPersonMethod = AddressBookServiceGrpc.getSearchPersonMethod) == null) {
      synchronized (AddressBookServiceGrpc.class) {
        if ((getSearchPersonMethod = AddressBookServiceGrpc.getSearchPersonMethod) == null) {
          AddressBookServiceGrpc.getSearchPersonMethod = getSearchPersonMethod =
              io.grpc.MethodDescriptor.<pt.tecnico.addressbook.grpc.SearchPersonRequest, pt.tecnico.addressbook.grpc.PersonInfo>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "searchPerson"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pt.tecnico.addressbook.grpc.SearchPersonRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pt.tecnico.addressbook.grpc.PersonInfo.getDefaultInstance()))
              .setSchemaDescriptor(new AddressBookServiceMethodDescriptorSupplier("searchPerson"))
              .build();
        }
      }
    }
    return getSearchPersonMethod;
  }

  private static volatile io.grpc.MethodDescriptor<pt.tecnico.addressbook.grpc.DeletePersonRequest,
      pt.tecnico.addressbook.grpc.DeletePersonResponse> getDeletePersonMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "deletePerson",
      requestType = pt.tecnico.addressbook.grpc.DeletePersonRequest.class,
      responseType = pt.tecnico.addressbook.grpc.DeletePersonResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<pt.tecnico.addressbook.grpc.DeletePersonRequest,
      pt.tecnico.addressbook.grpc.DeletePersonResponse> getDeletePersonMethod() {
    io.grpc.MethodDescriptor<pt.tecnico.addressbook.grpc.DeletePersonRequest, pt.tecnico.addressbook.grpc.DeletePersonResponse> getDeletePersonMethod;
    if ((getDeletePersonMethod = AddressBookServiceGrpc.getDeletePersonMethod) == null) {
      synchronized (AddressBookServiceGrpc.class) {
        if ((getDeletePersonMethod = AddressBookServiceGrpc.getDeletePersonMethod) == null) {
          AddressBookServiceGrpc.getDeletePersonMethod = getDeletePersonMethod =
              io.grpc.MethodDescriptor.<pt.tecnico.addressbook.grpc.DeletePersonRequest, pt.tecnico.addressbook.grpc.DeletePersonResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "deletePerson"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pt.tecnico.addressbook.grpc.DeletePersonRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pt.tecnico.addressbook.grpc.DeletePersonResponse.getDefaultInstance()))
              .setSchemaDescriptor(new AddressBookServiceMethodDescriptorSupplier("deletePerson"))
              .build();
        }
      }
    }
    return getDeletePersonMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static AddressBookServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AddressBookServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AddressBookServiceStub>() {
        @java.lang.Override
        public AddressBookServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AddressBookServiceStub(channel, callOptions);
        }
      };
    return AddressBookServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static AddressBookServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AddressBookServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AddressBookServiceBlockingStub>() {
        @java.lang.Override
        public AddressBookServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AddressBookServiceBlockingStub(channel, callOptions);
        }
      };
    return AddressBookServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static AddressBookServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AddressBookServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AddressBookServiceFutureStub>() {
        @java.lang.Override
        public AddressBookServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AddressBookServiceFutureStub(channel, callOptions);
        }
      };
    return AddressBookServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class AddressBookServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void listPeople(pt.tecnico.addressbook.grpc.ListPeopleRequest request,
        io.grpc.stub.StreamObserver<pt.tecnico.addressbook.grpc.AddressBookList> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListPeopleMethod(), responseObserver);
    }

    /**
     */
    public void addPerson(pt.tecnico.addressbook.grpc.PersonInfo request,
        io.grpc.stub.StreamObserver<pt.tecnico.addressbook.grpc.AddPersonResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAddPersonMethod(), responseObserver);
    }

    /**
     */
    public void searchPerson(pt.tecnico.addressbook.grpc.SearchPersonRequest request,
        io.grpc.stub.StreamObserver<pt.tecnico.addressbook.grpc.PersonInfo> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSearchPersonMethod(), responseObserver);
    }

    /**
     */
    public void deletePerson(pt.tecnico.addressbook.grpc.DeletePersonRequest request,
        io.grpc.stub.StreamObserver<pt.tecnico.addressbook.grpc.DeletePersonResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDeletePersonMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getListPeopleMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                pt.tecnico.addressbook.grpc.ListPeopleRequest,
                pt.tecnico.addressbook.grpc.AddressBookList>(
                  this, METHODID_LIST_PEOPLE)))
          .addMethod(
            getAddPersonMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                pt.tecnico.addressbook.grpc.PersonInfo,
                pt.tecnico.addressbook.grpc.AddPersonResponse>(
                  this, METHODID_ADD_PERSON)))
          .addMethod(
            getSearchPersonMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                pt.tecnico.addressbook.grpc.SearchPersonRequest,
                pt.tecnico.addressbook.grpc.PersonInfo>(
                  this, METHODID_SEARCH_PERSON)))
          .addMethod(
            getDeletePersonMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                pt.tecnico.addressbook.grpc.DeletePersonRequest,
                pt.tecnico.addressbook.grpc.DeletePersonResponse>(
                  this, METHODID_DELETE_PERSON)))
          .build();
    }
  }

  /**
   */
  public static final class AddressBookServiceStub extends io.grpc.stub.AbstractAsyncStub<AddressBookServiceStub> {
    private AddressBookServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AddressBookServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AddressBookServiceStub(channel, callOptions);
    }

    /**
     */
    public void listPeople(pt.tecnico.addressbook.grpc.ListPeopleRequest request,
        io.grpc.stub.StreamObserver<pt.tecnico.addressbook.grpc.AddressBookList> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListPeopleMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void addPerson(pt.tecnico.addressbook.grpc.PersonInfo request,
        io.grpc.stub.StreamObserver<pt.tecnico.addressbook.grpc.AddPersonResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAddPersonMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void searchPerson(pt.tecnico.addressbook.grpc.SearchPersonRequest request,
        io.grpc.stub.StreamObserver<pt.tecnico.addressbook.grpc.PersonInfo> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSearchPersonMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void deletePerson(pt.tecnico.addressbook.grpc.DeletePersonRequest request,
        io.grpc.stub.StreamObserver<pt.tecnico.addressbook.grpc.DeletePersonResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDeletePersonMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class AddressBookServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<AddressBookServiceBlockingStub> {
    private AddressBookServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AddressBookServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AddressBookServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public pt.tecnico.addressbook.grpc.AddressBookList listPeople(pt.tecnico.addressbook.grpc.ListPeopleRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListPeopleMethod(), getCallOptions(), request);
    }

    /**
     */
    public pt.tecnico.addressbook.grpc.AddPersonResponse addPerson(pt.tecnico.addressbook.grpc.PersonInfo request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAddPersonMethod(), getCallOptions(), request);
    }

    /**
     */
    public pt.tecnico.addressbook.grpc.PersonInfo searchPerson(pt.tecnico.addressbook.grpc.SearchPersonRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSearchPersonMethod(), getCallOptions(), request);
    }

    /**
     */
    public pt.tecnico.addressbook.grpc.DeletePersonResponse deletePerson(pt.tecnico.addressbook.grpc.DeletePersonRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDeletePersonMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class AddressBookServiceFutureStub extends io.grpc.stub.AbstractFutureStub<AddressBookServiceFutureStub> {
    private AddressBookServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AddressBookServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AddressBookServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<pt.tecnico.addressbook.grpc.AddressBookList> listPeople(
        pt.tecnico.addressbook.grpc.ListPeopleRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListPeopleMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<pt.tecnico.addressbook.grpc.AddPersonResponse> addPerson(
        pt.tecnico.addressbook.grpc.PersonInfo request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAddPersonMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<pt.tecnico.addressbook.grpc.PersonInfo> searchPerson(
        pt.tecnico.addressbook.grpc.SearchPersonRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSearchPersonMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<pt.tecnico.addressbook.grpc.DeletePersonResponse> deletePerson(
        pt.tecnico.addressbook.grpc.DeletePersonRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDeletePersonMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_LIST_PEOPLE = 0;
  private static final int METHODID_ADD_PERSON = 1;
  private static final int METHODID_SEARCH_PERSON = 2;
  private static final int METHODID_DELETE_PERSON = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AddressBookServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(AddressBookServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_LIST_PEOPLE:
          serviceImpl.listPeople((pt.tecnico.addressbook.grpc.ListPeopleRequest) request,
              (io.grpc.stub.StreamObserver<pt.tecnico.addressbook.grpc.AddressBookList>) responseObserver);
          break;
        case METHODID_ADD_PERSON:
          serviceImpl.addPerson((pt.tecnico.addressbook.grpc.PersonInfo) request,
              (io.grpc.stub.StreamObserver<pt.tecnico.addressbook.grpc.AddPersonResponse>) responseObserver);
          break;
        case METHODID_SEARCH_PERSON:
          serviceImpl.searchPerson((pt.tecnico.addressbook.grpc.SearchPersonRequest) request,
              (io.grpc.stub.StreamObserver<pt.tecnico.addressbook.grpc.PersonInfo>) responseObserver);
          break;
        case METHODID_DELETE_PERSON:
          serviceImpl.deletePerson((pt.tecnico.addressbook.grpc.DeletePersonRequest) request,
              (io.grpc.stub.StreamObserver<pt.tecnico.addressbook.grpc.DeletePersonResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class AddressBookServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    AddressBookServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return pt.tecnico.addressbook.grpc.AddressBookProtobuf.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("AddressBookService");
    }
  }

  private static final class AddressBookServiceFileDescriptorSupplier
      extends AddressBookServiceBaseDescriptorSupplier {
    AddressBookServiceFileDescriptorSupplier() {}
  }

  private static final class AddressBookServiceMethodDescriptorSupplier
      extends AddressBookServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    AddressBookServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (AddressBookServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new AddressBookServiceFileDescriptorSupplier())
              .addMethod(getListPeopleMethod())
              .addMethod(getAddPersonMethod())
              .addMethod(getSearchPersonMethod())
              .addMethod(getDeletePersonMethod())
              .build();
        }
      }
    }
    return result;
  }
}
