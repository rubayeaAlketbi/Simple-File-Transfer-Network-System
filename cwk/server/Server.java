import java.io.*;
import java.net.*;

public class Server {
    private ServerSocket serverSocket;

    public void runServer() {
        try {
            serverSocket = new ServerSocket(9100);
            System.out.println("Server is running...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientHandler.start();
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
