package pt.ulisboa.tecnico.classes.professor;
import java.util.Scanner;
import java.util.regex.Pattern;

import pt.ulisboa.tecnico.classes.NamingServerFrontend;

public class Professor {

  	private static final String OPEN_ENROLLMENTS_CMD = "openEnrollments";
	private static final String CLOSE_ENROLLMENTS_CMD = "closeEnrollments";
	private static final String LIST_CLASS_CMD = "list";
	private static final String CANCEL_ENROLLMENT_CMD = "cancelEnrollment";
	private static final String EXIT_CMD = "exit";
	private static final String STUDENT_ID_PREFIX = "aluno";
	private static final String DEBUG_FLAG = "-debug";


	/**
	 * Start Professor client.
	 * @param args The arguments needed to run the program (naming server's IP and port)
	 */
	public static void main(String[] args) {
		System.out.println(Professor.class.getSimpleName());

		int length = args.length;

		// Receive and print arguments
		System.out.printf("Received %d Argument(s)%n", args.length);
		for (int i = 0; i < length; ++i) {
			System.out.printf("args[%d] = %s%n", i, args[i]);
		}

		// Check that the arguments are well-formatted
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
		boolean debug = length == 4;

		NamingServerFrontend namingServer = new NamingServerFrontend(HOST, NAMING_PORT);
		ProfessorFrontend professor = new ProfessorFrontend(namingServer, debug);

		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.printf("%n> ");
			String line = scanner.nextLine();
			boolean formatted = true;
			switch (line){
				case CLOSE_ENROLLMENTS_CMD -> professor.closeEnrollments();
				case LIST_CLASS_CMD -> professor.listClass();
				case EXIT_CMD -> {
					scanner.close();
					namingServer.shutdownChannel();
					return;
				}
				default -> {
					String[] splitted = line.split(" ");
					if (splitted.length == 2){
						// The student ID must start with STUDENT_ID_PREFIX and
						// the second part of the ID must be an integer with a length of 4
						if (splitted[0].equals(CANCEL_ENROLLMENT_CMD) &&
							splitted[1].length() == 9 &&
							splitted[1].startsWith(STUDENT_ID_PREFIX) &&
							Pattern.matches("[0-9]+[\\.]?[0-9]*", splitted[1].substring(5))){
							professor.cancelEnrollment(splitted[1]);
						}
						else if (splitted[0].equals(OPEN_ENROLLMENTS_CMD) &&
								 Pattern.matches("[0-9]+[\\.]?[0-9]*", splitted[1])) {
							try {
								int capacity = Integer.parseInt(splitted[1]);
								professor.openEnrollments(capacity);
								break;
							} catch (NumberFormatException e) {
								formatted = false;
								break;
							}
						}
					}
					else{formatted = false;}
				}
			}
			if (!formatted)
				System.out.println("ERROR: Unknown command.");
		}
	}

}
