
import java.io.*;
import java.net.*;

/**
 * This class is the client side of the client-server communication. It will
 * connect to the server and send a message to it. The clinet should accept one
 * of the
 * following command line arguments:
 * arguments to perform the stated tasks:
 * list : which list all of the files on the server's folder in serverFiles
 * put fname: which uploads the file fname to the server and add it to the
 * server's folder in serverFiles
 * Exits the connection after completing each task
 */
public class Client {
	// Client socket for connection to the server
	private Socket clientSocket = null;
	// The server name
	private String serverName = "localhost";
	// The ip address of the server
	private InetAddress serverAddress = null;
	// The input stream of the client socket
	private BufferedReader cin = null;
	// The output stream of the client socket
	private PrintWriter cout = null;

	/**
	 * The following method will try to connect the socket to the server
	 * via one of the reserved prot numbers. Both clients and servers listens
	 * to ports in the range of 9100 to 9999. Both run on the same host: locaalhost.
	 * 
	 * @param void
	 * @return void
	 */
	public void BindToServer() {// Start connectToServer
		try {// Connecting to a socket
			clientSocket = new Socket(serverName, 9100);
			// Print the following message if the connection is successful
			System.out.println(
					"Connected to " + clientSocket.getRemoteSocketAddress() + " on port " + clientSocket.getPort());
		} // End tring to connect to a socket
		catch (UnknownHostException e) {// If the host is not found
			System.out.println("Error: " + e.getMessage());
		} // End try-catch block
		catch (IOException e) {// If there is an error in the input/output
			System.out.println("Error: " + e.getMessage());
		} // End try-catch block
	}// End connectToServer

	/**
	 * The following method will send the list command to the server
	 * to request a list of all the files in the server's folder
	 * 
	 * @param void
	 * @return void
	 */
	public void requestList() {// Start requestList
		try {
			// Create a new print writer object to send the list command to the server
			cout = new PrintWriter(clientSocket.getOutputStream(), true);
			// Get the number of files in the server's folder
			String request = "list";
			// Send the list command to the server
			cout.println(request);
		} catch (IOException e) {
			// TODO: handle exception
			System.out.println("Error: " + e.getMessage());
		}
	}

	/**
	 * The following method will send the put command to the server
	 * to request to upload a file to the server's folder
	 * 
	 */
	// public void requestPut(String filePath) {
		
	// }
	

	/**
	 * The following method will receive the response from the server
	 * after sending the list command to the server
	 * 
	 * @param void
	 * @return void
	 * 
	 */
	public void receiveListResponse() {
		try {
			// Create a BufferedReader to read the response from the server
			cin = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			// Assuming the server sends back a message indicating the action taken or data
			// requested
			String responseLine;
			// Read the response from the server
			responseLine = cin.readLine();
			// Check if the response is not empty or null from the server
			System.out.println("Listing " + responseLine + " file(s): ");
			// If the response is not empty or null, print the response to the console
			while ((responseLine = cin.readLine()) != null) {
				System.out.println(responseLine);
			}

		} catch (IOException e) {
			System.out.println("Error receiving response from the server: " + e.getMessage());
		}
	}

	/**
	 * The following method will receive the response from the server
	 * after sending the put command to the server
	 * 
	 */
	public void receivePutResponse()
	{
		try {
			// Create a BufferedReader to read the response from the server
			cin = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			// Assuming the server sends back a message indicating the action taken or data
			// requested
			String responseLine;
			// Read the response from the server
			responseLine = cin.readLine();
			// Check if the response is not empty or null from the server
			System.out.println(responseLine);

		} catch (IOException e) {
			System.out.println("Error receiving response from the server: " + e.getMessage());
		}
	
	}

	public void handleListRequest() {
		requestList();
		receiveListResponse();
	}

	// public void handlePutRequest() {
	// 	requestPut();
	// 	receivePutResponse();
	// }

	public void connect(String command) {
		// Connect to the server
		BindToServer();
		// Send the list command to the server
		if (command.equals("list")) {
			handleListRequest();
		}
		// }else if (command.equals("put")) {
		// 	handlePutRequest();
		// }
	}

	public static void main(String[] args) {
		Client client = new Client();
		client.connect(args[0]);
	}
}