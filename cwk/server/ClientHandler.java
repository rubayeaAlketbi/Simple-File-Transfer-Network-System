import java.io.*;
import java.net.Socket;

/**
 * The ClientHandler class extends the Thread class and handles client requests.
 * It has a Socket object to communicate with the client.
 *
 * The constructor of the ClientHandler class takes a Socket object as an argument and initializes the clientSocket.
 *
 * The run method is overridden from the Thread class. It creates a FileManager object for managing files,
 * a BufferedReader for reading input from the client, and a PrintWriter for sending output to the client.
 * It then reads a request from the client and handles it based on its type.
 *
 * If the request is "list", it calls the listFiles method of the FileManager object and logs the request.
 * If the request is "put", it reads the file name from the client, calls the handlePutRequest method, and logs the request.
 *
 * After handling the request, it closes the client socket.
 * If an IOException occurs during the execution of the run method, it prints the exception message.
 */


public class ClientHandler extends Thread 
{
    // Socket object to communicate with the client
    private Socket clientSocket;

    // Constructor of ClientHandler class
    public ClientHandler(Socket socket) 
    {
        // Initialize the clientSocket
        this.clientSocket = socket;
    }

    // Method to handle client requests and communicate with the client
    @Override
    public void run()
    {
        try 
        {
            // Create a FileManager object for managing files and conneect it to the clientSocket
            FileManager fileManager = new FileManager(clientSocket);
            // Create a BufferedReader for reading input from the client
            BufferedReader sin = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            // Create a PrintWriter for sending output to the client
            PrintWriter sout = new PrintWriter(clientSocket.getOutputStream(), true);
            // Read a request from the client
            String request = sin.readLine();
            // Simulate processing delay
            System.out.println("Handling request [" + request + "] from client: " + clientSocket.getInetAddress().getHostAddress());

            // Initialize the log file
            Log log = new Log("log.txt");
            
            // Check if the request is of type "list"
            if ("list".equals(request)) 
            {
                // If it is, call the listFiles method of the FileManager object and pass the PrintWriter object to it
                fileManager.listFiles(sout);
                // Log the request along with the client's IP address
                log.logging(clientSocket.getInetAddress().getHostAddress(), request);
            } 
            // Check if the request is of type "put"
            else if ("put".equals(request)) 
            {
                // If it is, read the file name from the client
                String fileName = sin.readLine();
                // Call the handlePutRequest method and pass the FileManager object, PrintWriter object, and file name to it
                handlePutRequest(fileManager, sout, fileName);
                // Log the request along with the client's IP address
                log.logging(clientSocket.getInetAddress().getHostAddress(), request);
            }
            // Close the client socket
            clientSocket.close();
        // Print the exception message if an IOException occurs
        } catch (IOException e)
        {
            // Print the exception message if an IOException occurs
            System.out.println("Error handling client request: " + e.getMessage());
            e.printStackTrace();
        }

    }

    /**
     * This method handles the "put" request from the client.
     * It takes a FileManager object, a PrintWriter object, and a file name as arguments.
     *
     * First, it checks if the file already exists on the server using the fileExists method of the FileManager object.
     * If the file exists, it sends a message "File already exists" to the client.
     *
     * If the file does not exist, it sends a message "Ready to receive file" to the client,
     * then it calls the saveFile method of the FileManager object to receive and save the file from the client.
     * After successfully receiving the file, it sends a message "File received successfully" to the client.
     *
     * @param fileManager The FileManager object for managing files.
     * @param sout The PrintWriter object for sending messages to the client.
     * @param fileName The name of the file to be received from the client.
     * @throws IOException If an I/O error occurs while receiving the file.
     */
    private void handlePutRequest(FileManager fileManager, PrintWriter sout, String fileName) throws IOException {
        // Check if the file already exists on the server
        if (fileManager.fileExists(fileName)) {
            // If it does, send a message to the client
            sout.println("File already exists");
        } else {
            // If it doesn't, send a message to the client and start receiving the file
            sout.println("Ready to receive file");
            // Receive and save the file from the client
            fileManager.saveFile(clientSocket, fileName);
            // Send a message to the client after successfully receiving the file
            sout.println("File received successfully");
        }
    }
}
