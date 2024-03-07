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

	public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please enter a command ('list' or 'put <filename>')");
            return;
        }

        String command = args[0];
        String fileName = null;
        if ("put".equals(command)) {
            if (args.length < 2) {
                System.out.println("Please specify a file name for the 'put' command.");
                return;
            }
            fileName = args[1];
        }

        try (Socket clientSocket = new Socket("localhost", 9100)) {
            ClientOperations operations = new ClientOperations(clientSocket);

            if ("list".equals(command)) {
                operations.requestList();
            } else if ("put".equals(command) && fileName != null) {
                operations.requestPut(fileName);
            }

        } catch (IOException e) {
            System.out.println("Connection error: " + e.getMessage());
        }
    }
}
