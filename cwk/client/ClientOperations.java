import java.io.*;
import java.net.*;
import java.nio.file.Files;

/**
 * The ClientOperations class handles the operations that a client can perform.
 * It has a PrintWriter object for sending requests to the server, a BufferedReader object for reading responses from the server,
 * and a Socket object for communicating with the server.
 *
 * The constructor of the ClientOperations class takes a Socket object as an argument and initializes the PrintWriter, BufferedReader, and Socket objects.
 *
 * The requestList method sends a "list" request to the server and prints the server's response.
 * It sends the request by writing "list" to the PrintWriter object and then reads the server's response using the BufferedReader object.
 * It continues to read the server's response until the server closes the connection.
 *
 * The requestPut method sends a "put" request to the server along with a file name.
 * It first checks if the file exists. If the file does not exist, it prints a message and returns.
 * If the file exists, it writes "put" and the file name to the PrintWriter object and then flushes the PrintWriter object.
 * It then waits for a response from the server before sending the file.
 */

public class ClientOperations
{
    // PrintWriter object for sending requests to the server
    private PrintWriter cout;
    // BufferedReader object for reading responses from the server
    private BufferedReader cin;
    // Socket object for communicating with the server
    private Socket clientSocket;

    // Constructor of ClientOperations class
    public ClientOperations(Socket clientSocket) throws IOException
    {
        // Initialize the PrintWriter, BufferedReader, and Socket objects
        this.cout = new PrintWriter(clientSocket.getOutputStream(), true);
        this.cin = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.clientSocket = clientSocket;
    }

    // Method to send a "list" request to the server and print the server's response
    public void requestList() throws IOException 
    {
        // Write "list" to the PrintWriter object to send the request to the server
        cout.println("list");
        // Read the server's response using the BufferedReader object
        String line;
        // Print the server's response
        while ((line = cin.readLine()) != null) 
        {
            // Print the server's response
            System.out.println(line);
        }
    }

    // Method to send a "put" request to the server along with a file name
    public void requestPut(String fileName) throws IOException 
    {
        // Check if the file exists
        File file = new File(fileName);
        // If the file does not exist, print a message and return
        if (!file.exists())
        {
            System.out.println("File does not exist");
            return;
        }
        // If the file exists, write "put" and the file name to the PrintWriter object to send the request to the server
        cout.println("put");
        cout.println(fileName);
        cout.flush(); 
    
        // Wait for server response before sending the file
        String response = cin.readLine();
        if ("File already exists".equals(response)) {
            System.out.println("File already exists on the server.");
            return;
        } else if ("Ready to receive file".equals(response)) {
            try (BufferedOutputStream bos = new BufferedOutputStream(clientSocket.getOutputStream())) {
                byte[] fileContent = Files.readAllBytes(file.toPath());
                bos.write(fileContent);
                bos.flush();
                System.out.println("File " + fileName + " sent to server.");
            } 
        }
    }
    
    
}