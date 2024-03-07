
import java.io.*;
import java.net.*;
import java.nio.file.Files;

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
	 * The put command requires the name of the file to be uploaded to the server
	 * @param String fileName: The name of the file to be uploaded to the server
	 * @return void
	 */

	 public void requestPut(String fileName) {
		File file = new File(fileName);
		// Check if the file exists if not then print an error message
		if(!file.exists()){
			System.out.println("File does not exist");
			return;
		}

		try {
			// Create a new print writer object to send the command to the server
			cout = new PrintWriter(clientSocket.getOutputStream(), true);
			// Create a buffered OutputStream to send the file to the server
			BufferedOutputStream bos = new BufferedOutputStream(clientSocket.getOutputStream());
			// Send the list command to the server
			cout.println("put");
			cout.println(fileName);
			// Send the request to the server
			cout.flush();
			// Read the file to be uploaded to the server
			byte[] fileContent = Files.readAllBytes(file.toPath());
			// Write the file to the server
        	bos.write(fileContent);
			// Flush the output stream
        	bos.flush();
			// Display the output stream
			System.out.println("File " + fileName + " sent to server.");
			// Close the output stream
			bos.close();
			cout.close();
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		} 
	}


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

	public void receivePutResponse() throws IOException{
			// Create a BufferedReader to read the response from the server
			cin = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			// Assuming the server sends back a message indicating the action taken or data
			// requested
			String responseLine;
			// Read the response from the server
			responseLine = cin.readLine();
			// Check if the response is not empty or null from the server
			System.out.println(responseLine);
		
	}

	/**
	 * The following method will receive the response from the server
	 * after sending the put command to the server
	 * 
	 */

	public void handleListRequest() {
		requestList();
		receiveListResponse();
	}



	public static void main(String[] args) {
		Client client = new Client();
		client.BindToServer();
		if (args.length == 0) {
			System.out.println("Please enter a command");
			return;
		}
		String command = args[0];
		if (command.equals("list")) {
			client.handleListRequest();
		} else if (command.equals("put")) {
			if (args.length < 2) {
				System.out.println("Please enter a file name");
				return;
			}
			String fileName = args[1];
			client.requestPut(fileName);
		} else {
			System.out.println("Invalid command");
		}
	}
}