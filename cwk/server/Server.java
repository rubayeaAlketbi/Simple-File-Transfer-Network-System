import java.io.*;
import java.net.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This is a Server class in Java. It uses the java.net and java.io libraries for networking and I/O operations.
 * It also uses the java.util.concurrent library for handling multiple client connections concurrently.
 * 
 * The Server class has a ServerSocket object for accepting client connections and an ExecutorService object for managing threads.
 * The ExecutorService is initialized with a fixed thread pool of 30 threads.
 * 
 * The runServer method is used to start the server. It creates a ServerSocket that listens on port 9100.
 * It then enters an infinite loop where it waits for client connections. When a client connects, it accepts the connection,
 * creates a new ClientHandler object for handling the client's requests, and submits it to the ExecutorService for execution.
 * 
 * If an IOException occurs during the execution of the runServer method, it prints the exception message and stack trace.
 */

public class Server 
{
    // ServerSocket object for accepting client connections
    private ServerSocket serverSocket;
    // ExecutorService object for managing threads
    private ExecutorService executor;

    // Constructor of Server class
    public Server()
    {
        // Initializing ExecutorService with a fixed thread pool of 30 threads
        executor = Executors.newFixedThreadPool(30);
    }

    // Method to start the server
    public void runServer() 
    {
        // Try to create a ServerSocket and listen on port 9100
        try {
            // Create a ServerSocket that listens on port 9100
            serverSocket = new ServerSocket(9100);
            System.out.println("Server is running...");

            // Enter an infinite loop to wait for client connections
            while (true)
            {
                // Accept the client connection
                Socket clientSocket = serverSocket.accept();

                System.out.println("Client connected: " + clientSocket.getInetAddress());

                // Create a new ClientHandler object for handling the client's requests
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                // Submit the ClientHandler to the ExecutorService for execution
                executor.submit(clientHandler);
            }
            
        } catch (IOException e) 
        {// Print the exception message and stack trace if an IOException occurs
            System.out.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) 
    {
        // Create a new Server object and start the server
        new Server().runServer();
    }
}
