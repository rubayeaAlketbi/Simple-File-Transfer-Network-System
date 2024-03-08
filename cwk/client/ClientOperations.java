import java.io.*;
import java.net.*;
import java.nio.file.Files;

public class ClientOperations {
    private PrintWriter cout;
    private BufferedReader cin;
    private Socket clientSocket;

    public ClientOperations(Socket clientSocket) throws IOException {
        this.cout = new PrintWriter(clientSocket.getOutputStream(), true);
        this.cin = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.clientSocket = clientSocket;
    }

    public void requestList() throws IOException {
        cout.println("list");
        String line;
        while ((line = cin.readLine()) != null) {
            System.out.println(line);
        }
    }

    public void requestPut(String fileName) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("File does not exist");
            return;
        }
    
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