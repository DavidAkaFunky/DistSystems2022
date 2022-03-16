package pt.tecnico.grpc.server;

import pt.tecnico.grpc.Banking.*;
import pt.tecnico.grpc.BankingServiceGrpc;
import static io.grpc.Status.INVALID_ARGUMENT;

import io.grpc.stub.StreamObserver;

public class BankingServiceImpl extends BankingServiceGrpc.BankingServiceImplBase {
	private Bank bank = new Bank();

	@Override
	public void register(RegisterRequest request, StreamObserver<RegisterResponse> responseObserver) {
		bank.register(request.getClient(), request.getBalance());

		responseObserver.onNext(RegisterResponse.getDefaultInstance());
		responseObserver.onCompleted();
	}

	@Override
	public void consult(ConsultRequest request, StreamObserver responseObserver) {
		String client = request.getClient();
		if (bank.isClient(client) == false) {
			responseObserver.onError(INVALID_ARGUMENT.withDescription("Input has to be a valid user!").asRuntimeException());
		}
		Integer balance = bank.getBalance(client);

		ConsultResponse response = ConsultResponse.newBuilder().setBalance(balance).build();

		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}

	@Override
	public void subsidize(SubsidizeRequest request, StreamObserver responseObserver) {
		boolean subsidized = bank.subsidize(request.getThreshold(), request.getAmount());
		if (subsidized == false) {
			responseObserver.onError(INVALID_ARGUMENT.withDescription("No users were subsidized.").asRuntimeException());
		}
		responseObserver.onNext(SubsidizeResponse.getDefaultInstance());
		responseObserver.onCompleted();
	}
}
