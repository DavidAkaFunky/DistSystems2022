package pt.tecnico.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.36.0)",
    comments = "Source: Banking.proto")
public final class BankingServiceGrpc {

  private BankingServiceGrpc() {}

  public static final String SERVICE_NAME = "pt.tecnico.grpc.BankingService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<pt.tecnico.grpc.Banking.RegisterRequest,
      pt.tecnico.grpc.Banking.RegisterResponse> getRegisterMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "register",
      requestType = pt.tecnico.grpc.Banking.RegisterRequest.class,
      responseType = pt.tecnico.grpc.Banking.RegisterResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<pt.tecnico.grpc.Banking.RegisterRequest,
      pt.tecnico.grpc.Banking.RegisterResponse> getRegisterMethod() {
    io.grpc.MethodDescriptor<pt.tecnico.grpc.Banking.RegisterRequest, pt.tecnico.grpc.Banking.RegisterResponse> getRegisterMethod;
    if ((getRegisterMethod = BankingServiceGrpc.getRegisterMethod) == null) {
      synchronized (BankingServiceGrpc.class) {
        if ((getRegisterMethod = BankingServiceGrpc.getRegisterMethod) == null) {
          BankingServiceGrpc.getRegisterMethod = getRegisterMethod =
              io.grpc.MethodDescriptor.<pt.tecnico.grpc.Banking.RegisterRequest, pt.tecnico.grpc.Banking.RegisterResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "register"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pt.tecnico.grpc.Banking.RegisterRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pt.tecnico.grpc.Banking.RegisterResponse.getDefaultInstance()))
              .setSchemaDescriptor(new BankingServiceMethodDescriptorSupplier("register"))
              .build();
        }
      }
    }
    return getRegisterMethod;
  }

  private static volatile io.grpc.MethodDescriptor<pt.tecnico.grpc.Banking.ConsultRequest,
      pt.tecnico.grpc.Banking.ConsultResponse> getConsultMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "consult",
      requestType = pt.tecnico.grpc.Banking.ConsultRequest.class,
      responseType = pt.tecnico.grpc.Banking.ConsultResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<pt.tecnico.grpc.Banking.ConsultRequest,
      pt.tecnico.grpc.Banking.ConsultResponse> getConsultMethod() {
    io.grpc.MethodDescriptor<pt.tecnico.grpc.Banking.ConsultRequest, pt.tecnico.grpc.Banking.ConsultResponse> getConsultMethod;
    if ((getConsultMethod = BankingServiceGrpc.getConsultMethod) == null) {
      synchronized (BankingServiceGrpc.class) {
        if ((getConsultMethod = BankingServiceGrpc.getConsultMethod) == null) {
          BankingServiceGrpc.getConsultMethod = getConsultMethod =
              io.grpc.MethodDescriptor.<pt.tecnico.grpc.Banking.ConsultRequest, pt.tecnico.grpc.Banking.ConsultResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "consult"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pt.tecnico.grpc.Banking.ConsultRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pt.tecnico.grpc.Banking.ConsultResponse.getDefaultInstance()))
              .setSchemaDescriptor(new BankingServiceMethodDescriptorSupplier("consult"))
              .build();
        }
      }
    }
    return getConsultMethod;
  }

  private static volatile io.grpc.MethodDescriptor<pt.tecnico.grpc.Banking.SubsidizeRequest,
      pt.tecnico.grpc.Banking.SubsidizeResponse> getSubsidizeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "subsidize",
      requestType = pt.tecnico.grpc.Banking.SubsidizeRequest.class,
      responseType = pt.tecnico.grpc.Banking.SubsidizeResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<pt.tecnico.grpc.Banking.SubsidizeRequest,
      pt.tecnico.grpc.Banking.SubsidizeResponse> getSubsidizeMethod() {
    io.grpc.MethodDescriptor<pt.tecnico.grpc.Banking.SubsidizeRequest, pt.tecnico.grpc.Banking.SubsidizeResponse> getSubsidizeMethod;
    if ((getSubsidizeMethod = BankingServiceGrpc.getSubsidizeMethod) == null) {
      synchronized (BankingServiceGrpc.class) {
        if ((getSubsidizeMethod = BankingServiceGrpc.getSubsidizeMethod) == null) {
          BankingServiceGrpc.getSubsidizeMethod = getSubsidizeMethod =
              io.grpc.MethodDescriptor.<pt.tecnico.grpc.Banking.SubsidizeRequest, pt.tecnico.grpc.Banking.SubsidizeResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "subsidize"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pt.tecnico.grpc.Banking.SubsidizeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pt.tecnico.grpc.Banking.SubsidizeResponse.getDefaultInstance()))
              .setSchemaDescriptor(new BankingServiceMethodDescriptorSupplier("subsidize"))
              .build();
        }
      }
    }
    return getSubsidizeMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static BankingServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<BankingServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<BankingServiceStub>() {
        @java.lang.Override
        public BankingServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new BankingServiceStub(channel, callOptions);
        }
      };
    return BankingServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static BankingServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<BankingServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<BankingServiceBlockingStub>() {
        @java.lang.Override
        public BankingServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new BankingServiceBlockingStub(channel, callOptions);
        }
      };
    return BankingServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static BankingServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<BankingServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<BankingServiceFutureStub>() {
        @java.lang.Override
        public BankingServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new BankingServiceFutureStub(channel, callOptions);
        }
      };
    return BankingServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class BankingServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void register(pt.tecnico.grpc.Banking.RegisterRequest request,
        io.grpc.stub.StreamObserver<pt.tecnico.grpc.Banking.RegisterResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRegisterMethod(), responseObserver);
    }

    /**
     */
    public void consult(pt.tecnico.grpc.Banking.ConsultRequest request,
        io.grpc.stub.StreamObserver<pt.tecnico.grpc.Banking.ConsultResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getConsultMethod(), responseObserver);
    }

    /**
     */
    public void subsidize(pt.tecnico.grpc.Banking.SubsidizeRequest request,
        io.grpc.stub.StreamObserver<pt.tecnico.grpc.Banking.SubsidizeResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSubsidizeMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getRegisterMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                pt.tecnico.grpc.Banking.RegisterRequest,
                pt.tecnico.grpc.Banking.RegisterResponse>(
                  this, METHODID_REGISTER)))
          .addMethod(
            getConsultMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                pt.tecnico.grpc.Banking.ConsultRequest,
                pt.tecnico.grpc.Banking.ConsultResponse>(
                  this, METHODID_CONSULT)))
          .addMethod(
            getSubsidizeMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                pt.tecnico.grpc.Banking.SubsidizeRequest,
                pt.tecnico.grpc.Banking.SubsidizeResponse>(
                  this, METHODID_SUBSIDIZE)))
          .build();
    }
  }

  /**
   */
  public static final class BankingServiceStub extends io.grpc.stub.AbstractAsyncStub<BankingServiceStub> {
    private BankingServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected BankingServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new BankingServiceStub(channel, callOptions);
    }

    /**
     */
    public void register(pt.tecnico.grpc.Banking.RegisterRequest request,
        io.grpc.stub.StreamObserver<pt.tecnico.grpc.Banking.RegisterResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRegisterMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void consult(pt.tecnico.grpc.Banking.ConsultRequest request,
        io.grpc.stub.StreamObserver<pt.tecnico.grpc.Banking.ConsultResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getConsultMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void subsidize(pt.tecnico.grpc.Banking.SubsidizeRequest request,
        io.grpc.stub.StreamObserver<pt.tecnico.grpc.Banking.SubsidizeResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSubsidizeMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class BankingServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<BankingServiceBlockingStub> {
    private BankingServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected BankingServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new BankingServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public pt.tecnico.grpc.Banking.RegisterResponse register(pt.tecnico.grpc.Banking.RegisterRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRegisterMethod(), getCallOptions(), request);
    }

    /**
     */
    public pt.tecnico.grpc.Banking.ConsultResponse consult(pt.tecnico.grpc.Banking.ConsultRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getConsultMethod(), getCallOptions(), request);
    }

    /**
     */
    public pt.tecnico.grpc.Banking.SubsidizeResponse subsidize(pt.tecnico.grpc.Banking.SubsidizeRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSubsidizeMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class BankingServiceFutureStub extends io.grpc.stub.AbstractFutureStub<BankingServiceFutureStub> {
    private BankingServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected BankingServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new BankingServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<pt.tecnico.grpc.Banking.RegisterResponse> register(
        pt.tecnico.grpc.Banking.RegisterRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRegisterMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<pt.tecnico.grpc.Banking.ConsultResponse> consult(
        pt.tecnico.grpc.Banking.ConsultRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getConsultMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<pt.tecnico.grpc.Banking.SubsidizeResponse> subsidize(
        pt.tecnico.grpc.Banking.SubsidizeRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSubsidizeMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_REGISTER = 0;
  private static final int METHODID_CONSULT = 1;
  private static final int METHODID_SUBSIDIZE = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final BankingServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(BankingServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_REGISTER:
          serviceImpl.register((pt.tecnico.grpc.Banking.RegisterRequest) request,
              (io.grpc.stub.StreamObserver<pt.tecnico.grpc.Banking.RegisterResponse>) responseObserver);
          break;
        case METHODID_CONSULT:
          serviceImpl.consult((pt.tecnico.grpc.Banking.ConsultRequest) request,
              (io.grpc.stub.StreamObserver<pt.tecnico.grpc.Banking.ConsultResponse>) responseObserver);
          break;
        case METHODID_SUBSIDIZE:
          serviceImpl.subsidize((pt.tecnico.grpc.Banking.SubsidizeRequest) request,
              (io.grpc.stub.StreamObserver<pt.tecnico.grpc.Banking.SubsidizeResponse>) responseObserver);
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

  private static abstract class BankingServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    BankingServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return pt.tecnico.grpc.Banking.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("BankingService");
    }
  }

  private static final class BankingServiceFileDescriptorSupplier
      extends BankingServiceBaseDescriptorSupplier {
    BankingServiceFileDescriptorSupplier() {}
  }

  private static final class BankingServiceMethodDescriptorSupplier
      extends BankingServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    BankingServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (BankingServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new BankingServiceFileDescriptorSupplier())
              .addMethod(getRegisterMethod())
              .addMethod(getConsultMethod())
              .addMethod(getSubsidizeMethod())
              .build();
        }
      }
    }
    return result;
  }
}
