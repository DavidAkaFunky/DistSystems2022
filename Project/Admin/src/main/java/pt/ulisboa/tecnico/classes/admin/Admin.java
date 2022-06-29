package pt.ulisboa.tecnico.classes.admin;

import java.util.Scanner;
import java.util.regex.Pattern;

import pt.ulisboa.tecnico.classes.NamingServerFrontend;

public class Admin {

	private static final String EXIT_CMD = "exit";
	private static final String ACTIVATE_CMD = "activate";
	private static final String DEACTIVATE_CMD = "deactivate";
	private static final String ACTIVATE_GOSSIP_CMD = "activateGossip";
	private static final String DEACTIVATE_GOSSIP_CMD = "deactivateGossip";
	private static final String GOSSIP_CMD = "gossip";
	private static final String DUMP_CMD = "dump";
	private static final String DEBUG_FLAG = "-debug";


	/**
	 * Start Admin client.
	 * @param args The arguments needed to run the program (naming server's IP and port).
	 */
	public static void main(String[] args) {
		System.out.println(Admin.class.getSimpleName());

		int length = args.length;
		//Receive and print arguments
		System.out.printf("Received %d Argument(s)%n", args.length);
		for (int i = 0; i < length; ++i) {
			System.out.printf("args[%d] = %s%n", i, args[i]);
		}

		// Check that the arguments are well-formatted
		if (length > 3) {
			System.out.println("Wrong format! Too many arguments.");
			return;
		}
		if (length < 2 || !Pattern.matches("[0-9]+[\\.]?[0-9]*", args[1])) {
			System.out.println("Wrong format! You need to specify the IP and port of the naming server.");
			return;
		}

		if (length > 3 || (length == 3 && !args[2].equals(DEBUG_FLAG))){
			System.out.println("Wrong format! Too many arguments.");
			return;
		}

		String HOST = args[0];
		Integer NAMING_PORT = Integer.parseInt(args[1]);
		boolean debug = length == 3;
	
		NamingServerFrontend namingServer = new NamingServerFrontend(HOST, NAMING_PORT);
		AdminFrontend admin = new AdminFrontend(namingServer, debug);
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.printf("%n> ");
			String line = scanner.nextLine();
			String[] splitted = line.split(" ");
			switch (splitted[0]) {
				case EXIT_CMD -> {
					scanner.close();
					namingServer.shutdownChannel();
					return;
				}
				case DUMP_CMD -> {
					if (splitted.length == 1)
						admin.dump("P");
					else if (splitted.length == 2 && (splitted[1].equals("P") || splitted[1].equals("S")))
						admin.dump(splitted[1]);
					else
						System.out.println("ERROR: The server qualifier must be P or S.");
				}
				case ACTIVATE_CMD -> {
					if (splitted.length == 1)
						admin.activate("P");
					else if (splitted.length == 2 && (splitted[1].equals("P") || splitted[1].equals("S")))
						admin.activate(splitted[1]);
					else
						System.out.println("ERROR: The server qualifier must be P or S.");
				}
				case DEACTIVATE_CMD -> {
					if (splitted.length == 1)
						admin.deactivate("P");
					else if (splitted.length == 2 && (splitted[1].equals("P") || splitted[1].equals("S")))
						admin.deactivate(splitted[1]);
					else
						System.out.println("ERROR: The server qualifier must be P or S.");
				}
				case ACTIVATE_GOSSIP_CMD -> {
					if (splitted.length == 1)
						admin.activateGossip("P");
					else if (splitted.length == 2 && (splitted[1].equals("P") || splitted[1].equals("S")))
						admin.activateGossip(splitted[1]);
					else
						System.out.println("ERROR: The server qualifier must be P or S.");
				}
				case DEACTIVATE_GOSSIP_CMD -> {
					if (splitted.length == 1)
						admin.deactivateGossip("P");
					else if (splitted.length == 2 && (splitted[1].equals("P") || splitted[1].equals("S")))
						admin.deactivateGossip(splitted[1]);
					else
						System.out.println("ERROR: The server qualifier must be P or S.");
				}
				case GOSSIP_CMD -> {
					if (splitted.length == 1)
						admin.gossip("P");
					else if (splitted.length == 2 && (splitted[1].equals("P") || splitted[1].equals("S")))
						admin.gossip(splitted[1]);
					else
						System.out.println("ERROR: The server qualifier must be P or S.");
				}
				default -> System.out.println("ERROR: Unknown command.");
			}
		}
	}
}
