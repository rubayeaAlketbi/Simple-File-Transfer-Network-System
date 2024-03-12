import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            FileManager fileManager = new FileManager(clientSocket);
            BufferedReader sin = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter sout = new PrintWriter(clientSocket.getOutputStream(), true);

            String request = sin.readLine();
            // Simulate processing delay
            System.out.println("Handling request [" + request + "] from client: " + clientSocket.getInetAddress().getHostAddress());
            Thread.sleep(5000); // Sleep for 5 seconds

            // Initialize the log file
            Log log = new Log("log.txt");

            if ("list".equals(request)) {
                fileManager.listFiles(sout);
                log.logging(clientSocket.getInetAddress().getHostAddress(), request);
            } else if ("put".equals(request)) {
                String fileName = sin.readLine();
                handlePutRequest(fileManager, sout, fileName);
                log.logging(clientSocket.getInetAddress().getHostAddress(), request);
            }
            
            clientSocket.close();
        } catch (IOException e) {
            System.out.println("Error handling client request: " + e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            System.out.println("Thread was already interrupted: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void handlePutRequest(FileManager fileManager, PrintWriter sout, String fileName) throws IOException {
        if (fileManager.fileExists(fileName)) {
            sout.println("File already exists");
        } else {
            sout.println("Ready to receive file");
            fileManager.saveFile(clientSocket, fileName);
            sout.println("File received successfully");
        }
    }
}
