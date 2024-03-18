import java.io.*;
import java.net.*;

/**
 * This is the Client class in Java. It is the client side of the client-server communication.
 * It connects to the server and sends a message to it. The client should accept one of the following command line arguments to perform the stated tasks:
 * - 'list': which lists all of the files on the server's folder in serverFiles
 * - 'put fname': which uploads the file fname to the server and adds it to the server's folder in serverFiles
 * The connection is exited after completing each task.
 *
 * The main method is the entry point of the program. It first checks if any command line arguments have been passed.
 * If not, it prints a message and returns. If a command has been passed, it checks if the command is 'put'.
 * If it is, it checks if a file name has been specified. If not, it prints a message and returns.
 * If a file name has been specified, it stores the file name in a variable.
 *
 * It then tries to create a Socket object for communicating with the server. If successful, it creates a ClientOperations object for performing operations.
 * If the command is 'list', it calls the requestList method of the ClientOperations object.
 */

public class Client {
    // The main method is the entry point of the program
	public static void main(String[] args) 
    {
        // Check if any command line arguments have been passed
        if (args.length == 0)
        {

            System.out.println("Please enter a command ('list' or 'put <filename>')");
            return;
        }
        // Store the command in a variable
        String command = args[0];
        // Store the file name in a variable if the command is 'put'
        String fileName = null;
        // Check if the command is 'put' and a file name has been specified
        if ("put".equals(command))
        {
            // If a file name has not been specified, print a message and return
            if (args.length < 2) {
                System.out.println("Please specify a file name for the 'put' command.");
                return;
            }
            // Store the file name in a variable
            fileName = args[1];
        }
        try (Socket clientSocket = new Socket("localhost", 9100)) 
        {
            // Create a ClientOperations object for performing operations
            ClientOperations operations = new ClientOperations(clientSocket);

            // Check if the command is 'list'
            if ("list".equals(command)) 
            {
                // If it is, call the requestList method of the ClientOperations object
                operations.requestList();
            } else if ("put".equals(command) && fileName != null) 
            {   
                // If the command is 'put' and a file name has been specified, call the requestPut method of the ClientOperations object
                operations.requestPut(fileName);
            }

        } catch (IOException e) {
            System.out.println("Connection error: " + e.getMessage());
        }
    }
}
