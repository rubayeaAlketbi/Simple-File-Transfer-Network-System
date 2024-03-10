import java.io.*;
import java.net.*;


public class Server {
    private ServerSocket serverSocket = null;

    public void runServer() {
        try {
            // Create a server socket and bind it to port 4242
            ServerSocket serverSocket = new ServerSocket(9100);
            System.out.println("Server is running...");

            // The server will run continously
            while (true) {
                // Wait for a client to connect and returns the clinet socket
                Socket clientSocket = serverSocket.accept();
                FileManager fileManager = new FileManager(clientSocket);

                try {
                    // Initialize the input stream of the client socket
                    BufferedReader sin = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    // Initialize the output stream of the client socket
                    PrintWriter sout = new PrintWriter(clientSocket.getOutputStream(), true);
                    // Read the request from the client
                    String request = sin.readLine();
                    // If the request is list, send the list of files in the server's folder to the
                    // client
                    if (request.equals("list")) {
                        fileManager.listFiles(sout);
                        Log logger = new Log("log.txt");
                        logger.logging(clientSocket.getInetAddress().getHostAddress(), request);

                    } else if (request.equals("put")) {
                        String fileName = sin.readLine();

                        if (fileManager.fileExists(fileName)) {
                            sout.println("File already exists");
                        } else {
                            sout.println("Ready to receive file"); // Indicate ready to receive file content
                            fileManager.saveFile(clientSocket, fileName);
                            sout.println("File received successfully");
                            // Log the request
                            Log logger = new Log("log.txt");
                            logger.logging(clientSocket.getInetAddress().getHostAddress(), request + " " + fileName);
                        }
                    }
                    // Print the request to the console
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
                clientSocket.close();

            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.runServer();
    }
}