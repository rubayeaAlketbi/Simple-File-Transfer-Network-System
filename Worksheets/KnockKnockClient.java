import java.io.*;
import java.net.*;
public class KnockKnockClient {
    //Initialize the socket, input and output streams
    private Socket kkSocket = null;
    private PrintWriter socketOutput = null;
    private BufferedReader socketInput = null;

    //Method to play the knock knock game
    public void playKnockKnock(){
        try {
            // Creating a new socket the server is assumed that it will run on localhost
            kkSocket = new Socket("localhost", 2323);
            // Chain a writing stream
            socketOutput = new PrintWriter(kkSocket.getOutputStream(), true);
            // Chain a reading stream
            socketInput = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
        } catch (UnknownHostException e) {
            // TODO: handle exception
            System.err.println("Don't know about host.\n");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to host.\n");
            System.exit(1);
        }

        // Chain the reader from the keyboard
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer;
        String fromUser;

        // Read from the server
        try{
            while((fromServer = socketInput.readLine()) != null){
                // Echo server string
                System.out.println("Server: " + fromServer);
                // Client types in response
                fromUser = stdIn.readLine();
                if(fromUser != null){
                    // Echo client string
                    System.out.println("Client: " + fromUser);
                    // Write to server
                    socketOutput.println(fromUser);
                }
            }
            socketOutput.close();
            socketInput.close();
            stdIn.close();
            kkSocket.close();

        }catch (IOException e) {
            System.err.println("I/O exception during execution\n");
            System.exit(1);
        }
    }
    
    public static void main(String[] args) {
      KnockKnockClient kkc = new KnockKnockClient();
      kkc.playKnockKnock();
    }

}
