package pt.ulisboa.tecnico.classes.student;

import pt.ulisboa.tecnico.classes.NamingServerFrontend;
import java.util.Scanner;
import java.util.Arrays;
import java.util.regex.Pattern;

public class Student {

	private static final String EXIT_CMD = "exit";
	private static final String ENROLL_CMD = "enroll";
	private static final String LIST_CLASS_CMD = "list";
	private static final String STUDENT_ID_PREFIX = "aluno";
	private static final String DEBUG_FLAG = "-debug";

	/**
	 * Start student client.
	 * @param args The arguments needed to run the program (naming server's IP and port, students ID and number).
	 */
  	public static void main(String[] args) {
		System.out.println(Student.class.getSimpleName());

		int length = args.length;
		String studentNumber;
		String studentName;
		String HOST;
		Integer NAMING_PORT;

		//Receive and print arguments
		System.out.printf("Received %d Argument(s)%n", args.length);
		for (int i = 0; i < length; ++i) {
			System.out.printf("args[%d] = %s%n", i, args[i]);
		}
		
		// Check that the arguments are well-formatted
		if (length <= 0) {
			System.out.println("Wrong format! Try <<HOST>> <<PORT>> alunoXXXX <<student name>> -debug .");
			return;
		}
		boolean debug = args[length-1].equals(DEBUG_FLAG);
		if ((debug && length < 5) || length < 4 || !Pattern.matches("[0-9]+[\\.]?[0-9]*", args[1])) {
			if (debug) {
				System.out.println("Wrong format! Try <<HOST>> <<PORT>> alunoXXXX <<student name>> -debug .");
				System.out.println("Note: <<PORT>> and XXXX must be an integer.");
			}
			else {
				System.out.println("Wrong format! Try <<HOST>> <<PORT>> alunoXXXX <<student name>>.");
				System.out.println("Note: <<PORT>> must be an integer.");	
			}
			return;
		}

		HOST = args[0]; 
		NAMING_PORT = Integer.parseInt(args[1]);
		studentNumber = args[2];

		// Join all the student's names into one string
		if (debug)
			studentName = String.join(" ", Arrays.asList(args).subList(3,length-1));
		else
			studentName = String.join(" ", Arrays.asList(args).subList(3,length));


		// Check if the student name and number are well-formatted
		if (studentNumber.length() != 9 ||
			!studentNumber.startsWith(STUDENT_ID_PREFIX) ||
			!Pattern.matches("[0-9]+[\\.]?[0-9]*", studentNumber.substring(5)) ||
			studentName.length() > 30 || studentName.length() < 3) {
			System.out.println("Wrong format! Try alunoXXXX <<student name>> <<HOST>> <<PORT>>.");
			System.out.println("Note: XXXX must be an integer and the students' name between 3 and 30 characters.");
			return;
		}

		System.out.println("You are registed as " + studentName + ".");

		NamingServerFrontend namingServer = new NamingServerFrontend(HOST, NAMING_PORT);
		StudentFrontend student = new StudentFrontend(namingServer, debug);
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.printf("%n> ");
			String line = scanner.nextLine();
			switch (line){
				case ENROLL_CMD:
					student.enroll(studentNumber, studentName);
					break;
				case LIST_CLASS_CMD:
					student.listClass(studentNumber);
					break;
				case EXIT_CMD:
					scanner.close();
					namingServer.shutdownChannel();
					return;
				default:
					System.out.println("ERROR: Unknown command.");
			}
		}
  	}
}
