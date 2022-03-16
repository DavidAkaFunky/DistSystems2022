package pt.tecnico.grpc.client;

import java.util.Scanner;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import pt.tecnico.grpc.BankingServiceGrpc;
import pt.tecnico.grpc.Banking.*;

/** Client application main code. */
public class BankingApp {


	private static final String EXIT_CMD = "exit";
	private static final String REGISTER_CMD = "register";

	public static void main(String[] args) {
		System.out.println(BankingApp.class.getSimpleName());

		// receive and print arguments
		System.out.printf("Received %d arguments%n", args.length);
		for (int i = 0; i < args.length; i++) {
			System.out.printf("arg[%d] = %s%n", i, args[i]);
		}

		// check arguments
		if (args.length < 2) {
			System.out.println("Argument(s) missing!");
			System.out.printf("Usage: java %s host port%n", BankingApp.class.getName());
			return;
		}

		final String host = args[0];
		final int port = Integer.parseInt(args[1]);

		final ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();

		// Create a blocking stub.

		final BankingServiceGrpc.BankingServiceBlockingStub stub = BankingServiceGrpc.newBlockingStub(channel);
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.printf("> Type the operation you wish to perform (`exit` to quit)%n> ");


			String line = scanner.nextLine();

			// exit
			if (EXIT_CMD.equals(line)) {
				scanner.close();
				break;
			}

			else if (REGISTER_CMD.equals(line)) {
				System.out.printf("> Type username you want to register%n> ");
				String client = scanner.nextLine();
				System.out.printf("> What is this user's initial balance%n> ");
				String balance = scanner.nextLine();
				stub.register(RegisterRequest.newBuilder().setClient(client).setBalance(Integer.parseInt(balance)).build());
				System.out.println("\n\n");
			}

			else if ("consult".equals(line)){
				System.out.printf("> Type username you want to register%n> ");
				String client = scanner.nextLine();
				try {
					ConsultRequest requestConsult = ConsultRequest.newBuilder().setClient(client).build();
					ConsultResponse responseConsult = stub.consult(requestConsult);
					System.out.println(responseConsult.getBalance());
				} catch (StatusRuntimeException e) {
					System.out.println("Caught exception with description: " +
							e.getStatus().getDescription());
				}
			}

			else if ("subsidize".equals(line)){
				System.out.printf("> Type the threshold%n> ");
				String threshold = scanner.nextLine();
				System.out.printf("> Type the amount to susbidize%n> ");
				String amount = scanner.nextLine();
				try {
					SubsidizeRequest requestSubsidize = SubsidizeRequest.newBuilder()
																		.setThreshold(Integer.parseInt(threshold))
																		.setAmount(Integer.parseInt(amount))
																		.build();
					SubsidizeResponse responseSubsidize = stub.subsidize(requestSubsidize);
				} catch (StatusRuntimeException e) {
					System.out.println("Caught exception with description: " +
							e.getStatus().getDescription());
				}
			}
		}
	}
}