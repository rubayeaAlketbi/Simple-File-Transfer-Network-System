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
        // Check if the file exists if not then print an error message
        if(!file.exists()){
            System.out.println("File does not exist");
            return;
        }
        BufferedOutputStream bos = new BufferedOutputStream(clientSocket.getOutputStream());
        // Send the list command to the server
        cout.println("put");
        cout.println(fileName);
        // Send the request to the server
        cout.flush();
        // Read the file to be uploaded to the server
        byte[] fileContent = Files.readAllBytes(file.toPath());
        // Write the file to the server
        bos.write(fileContent);
        // Flush the output stream
        bos.flush();
         // Display the output stream
        System.out.println("File " + fileName + " sent to server.");
         // Close the output stream
        bos.close();
        cout.close();  
    }      
    
}