package pt.tecnico.grpc.server;

import pt.tecnico.grpc.Banking.*;
import pt.tecnico.grpc.BankingServiceGrpc;
import static io.grpc.Status.INVALID_ARGUMENT;
import static io.grpc.Status.NOT_FOUND;

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
			return;
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
			return;
		}
		responseObserver.onNext(SubsidizeResponse.getDefaultInstance());
		responseObserver.onCompleted();
	}

	@Override
	public void terminate(TerminateRequest request, StreamObserver responseObserver) {
		int terminated = bank.terminate(request.getClient());
		if (terminated == 1) {
			responseObserver.onError(NOT_FOUND.withDescription("The client doesn't exist.").asRuntimeException());
			return;
		} else if (terminated == 2){
			responseObserver.onError(INVALID_ARGUMENT.withDescription("The client's current balance is not 0, so the account can't be closed.").asRuntimeException());
			return;
		}
		responseObserver.onNext(TerminateResponse.getDefaultInstance());
		responseObserver.onCompleted();
	}
}
