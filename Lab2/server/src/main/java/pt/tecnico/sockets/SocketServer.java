package pt.tecnico.sockets;

import java.io.*;
import java.net.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class SocketServer {

	public static void main(String[] args) throws IOException, java.text.ParseException {
		// Check arguments
		if (args.length < 1) {
			System.err.println("Argument(s) missing!");
			System.err.printf("Usage: java %s port%n", SocketServer.class.getName());
			return;
		}

		// Convert port from String to int
		final int port = Integer.parseInt(args[0]);

		// Create server socket
		ServerSocket serverSocket = new ServerSocket(port);
		System.out.printf("Server accepting connections on port %d %n", port);

		// wait for and then accept client connection
		// a socket is created to handle the created connection
		Socket clientSocket = serverSocket.accept();
		final String clientAddress = clientSocket.getInetAddress().getHostAddress();
		final int clientPort = clientSocket.getPort();
		System.out.printf("Connected to client %s on port %d %n", clientAddress, clientPort);

		// Create buffered stream to receive data from client, one line at a time
		BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

		OutputStream outputStream;
		DataOutputStream dataOutputStream;

		// Receive data until client closes the connection
		String response;
		String msg;
		SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
		Date date;
		int number = 0;
		for (int i = 0; i < 3; i++) {
			// Read a line of text.
			// A line ends with a line feed ('\n').
			response = in.readLine();
			if (response == null) {
				break;
			}

			System.out.printf("Received message with content: %s\n", response);
			switch (i){
				case 0:
					msg = response;
					break;
				case 1:
					number = Integer.parseInt(response);
					break;
				case 2:
					date = formatter.parse(response);
					System.out.println(date.toString());
					break;
			}
		}
		outputStream = clientSocket.getOutputStream();
		dataOutputStream = new DataOutputStream(outputStream);
		System.out.println("Sending string to the ServerSocket");
		dataOutputStream.writeBytes(Integer.toString(number+1) + '\n');
		dataOutputStream.flush(); // send the message
		dataOutputStream.close(); // close the output stream when we're done.
		// Close connection to current client
		clientSocket.close();
		System.out.println("Closed connection with client");

		// Close server socket
		serverSocket.close();
		System.out.println("Closed server socket");
	}

}
