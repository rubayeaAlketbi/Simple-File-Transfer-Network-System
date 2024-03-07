import java.io.*;
import java.net.*;

/**
 * The server application shall listen to a port and accept connections from clients.
 * The server shall be running continously and use an Executioner to manage a fixed pool of 
 * threads. The clinet request and query the local folder and returns the list of files in the folder.
 * The server shall also accept files from the client and store them in the local folder.
 * If the file already exists, the server shall oreturn an error message to the clinet otherwise it
 * saves it in the serverFile. Besides that it shall also log all of the valid request made by the client
 * in a log file.
*/
public class Server 
{
    private ServerSocket serverSocket = null;
    private BufferedReader sin = null;
    private PrintWriter sout = null;


	public void runServer(){
        try {
            // Create a server socket and bind it to port 4242
            ServerSocket serverSocket = new ServerSocket(9100);
            // The server will run continously
            while(true){
                // Wait for a client to connect and returns the clinet socket
                Socket socket = serverSocket.accept();
                // Initialize the input stream of the client socket
                try {
                    sin = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    // Initialize the output stream of the client socket
                    sout = new PrintWriter(socket.getOutputStream(), true);
                    // Read the request from the client
                    String request = sin.readLine();
                    // If the request is list, send the list of files in the server's folder to the client
                    if (request.equals("list"))
                    {
                        sendList(socket);
                    }
                    else
                    {
                        // Print the request to the console
                        receiveFile(socket);
                    }
                    // Print the request to the console
                    System.out.println("Request: " + request); 
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
                socket.close();
                
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    /**
     * The following is the method which will fetch the list of files in the server's folder
     * and send it to the client
     * @param clientSocket: The socket of the client which is connected to the server
     * @return void
     */
    public void sendList(Socket clientSocket)
    {
        // Create a new file object to get the list of files in the server's folder
        File folder = new File("serverFiles");
        // Check if the server's folder exists
        if(!folder.exists()){
            throw new NullPointerException("Server folder does not exist. try again.");
        }
        // Create a new array of files to store the list of files in the server's folder
        File[] listOfFiles = folder.listFiles();
        try 
        {
            sout = new PrintWriter(clientSocket.getOutputStream(), true);
            // Send the number of files in the server's folder to the client
            sout.println(listOfFiles.length);
            // Send the list of files in the server's folder to the client
            for (int i = 0; i < listOfFiles.length; i++) 
            {
                sout.println(listOfFiles[i].getName());
            }
        } catch (IOException e) 
        {
            System.out.println("There was an error sending the list of files to the client: " + e.getMessage());
        }
    }

    /**
     * The following is a method which handles the put command from the server and saves the file in the server's folder
     * @param clientSocket: The socket of the client which is connected to the server
     * @return void
     */
    public void receiveFile(Socket clientSocket)
    {
        try {
            sout = new PrintWriter(clientSocket.getOutputStream(), true);
            sout.println("ready");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
    
	public static void main( String[] args )
	{
		Server server = new Server();
		server.runServer();
	}
}