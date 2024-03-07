import java.net.*;
import java.io.*;

public class KnockKnockServer {
    // Intialize the server socket
    private ServerSocket serverSocket = null;
    private KnockKnockProtocol kkp = null;

    // Initialize the client socket
    public KnockKnockServer(){
        try {
            serverSocket = new ServerSocket(2323);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 2323.\n");
            System.exit(1);
        }
        kkp = new KnockKnockProtocol();
    } 

    // Method to play the knock knock game
    public void runServer(){
        Socket clientSocket = null;
        while(true){
            try {
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                // TODO: handle exception
                System.err.println("Accept failed.\n");
                System.exit(1);
            }
            System.out.println("Client Socket Port : " + clientSocket.getPort());
            // Open a stream to read from the client
            try {
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                                        new InputStreamReader(
                                            clientSocket.getInputStream()));

                String inputLine, outputLine;
                outputLine = kkp.processInput(null);
                out.println(outputLine);

                while((inputLine = in.readLine()) != null){
                    outputLine = kkp.processInput(inputLine);
                    out.println(outputLine);
                    if(outputLine.equals("Bye.")){
                        break;
                    }
                }
                out.close();
                in.close();
                clientSocket.close();
            } catch (Exception e) {
                // TODO: handle exception
                System.err.println("I/O exception during execution\n");
            }
        }
    }

    public static void main(String[] args) {
        KnockKnockServer kks = new KnockKnockServer();
        kks.runServer();
    }
    
}
