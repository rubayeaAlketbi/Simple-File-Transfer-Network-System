import java.io.*;
import java.net.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server {
    private ServerSocket serverSocket;
    private ExecutorService executor;

    public Server()
    {
        executor = Executors.newFixedThreadPool(30);
    }

    public void runServer() {
        try {
            serverSocket = new ServerSocket(9100);
            System.out.println("Server is running...");

            while (true)
            {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                executor.submit(clientHandler);
            }
        } catch (IOException e) {
            System.out.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server().runServer();
    }
}
