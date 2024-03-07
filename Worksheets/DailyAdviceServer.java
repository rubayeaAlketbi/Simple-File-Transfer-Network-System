import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.util.Date;


public class DailyAdviceServer {
    
    // Feel free to add any 'hilarious' one-line advice strings here.
    private String[] adviceList = {
        "Take smaller bites",
        "Go for the tight jeans. No they do NOT make you look fat",
        "One word: inappropriate",
        "Just for today, be honest. Tell your boss what you *really* think",
        "You might want to rethink that haircut"
    };

    // The advice sent to the client is just randomly selected from the above list.
    private String getAdvice() {
        int random = (int) (Math.random() * adviceList.length);
        return adviceList[random];
    }
    /*Running the server so it establish a connection between
     * the server and the client
    */
    public void runServer(){
        try {
            // Create a server socket and bind it to port 4242
            ServerSocket serverSocket = new ServerSocket(4242);
            while(true){
                // Wait for a client to connect
                Socket socket = serverSocket.accept();
                // Get the information about the connection 
                InetAddress inetAddress = socket.getInetAddress();
                Date date = new Date();
                System.out.println("\nDate " + date.toString());
                System.out.println("Connection made from " + inetAddress.getHostName());

                // Send a single line of text to the client.
                PrintWriter writer = new PrintWriter(socket.getOutputStream());
                String advice = getAdvice();
                writer.println(advice);    		 // Write to client
                writer.close();
                System.out.println(advice);		 // Local server echo
                socket.close();
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static void main(String[] args) {
        DailyAdviceServer server = new DailyAdviceServer();
        server.runServer();
    }
}
