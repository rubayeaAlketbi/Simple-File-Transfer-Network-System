//
// Simple client that reads from a server running the DailyAdviceServer application,
// which opens up port 4242 and sends single lines of advice. This client reads that
// advice, displays it, and then closes the connection.
//

import java.io.*;
import java.net.*;

public class DailyAdviceClient
{
    // Open a socket connection to the server, read a single line of text from the server,
    // and then close the connection.
    public void connect(){
        try {
            // Open a socket connection with the host and with the port number 4242
            Socket s = new Socket("localhost", 4242);
            // Buffer the input stream from the socket for performance
            BufferedReader reader = new BufferedReader(
                                        new InputStreamReader(
                                            s.getInputStream()) );
            // Read a single line of text from the server
            String advice = reader.readLine();
            System.out.println(" The advice from the server is: " + advice);
            // Close the input stream reader and the connection
            reader.close();
            s.close();
        } catch (IOException e) {
            // TODO: handle exception
            System.out.println(e);
        }

    }

    public static void main(String[] args)
    {
        DailyAdviceClient client = new DailyAdviceClient();
        client.connect();
    }
}