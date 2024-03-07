import java.io.*;
import java.net.*;

public class FileManager {

    private final File serverDirectory = new File("serverFiles");
    private Socket socket;

    public FileManager(Socket socket) {
        if (!serverDirectory.exists()) {
            serverDirectory.mkdirs();
        }
        this.socket = socket;
    }

    public boolean fileExists(String fileName) {
        File file = new File(serverDirectory, fileName);
        return file.exists();
    }

    public void saveFile(Socket clientSocket, String fileName) throws IOException {
        // Check if the file exists if not then print an error message
        File file = new File(serverDirectory, fileName);
        if (file.exists()) {
            throw new IOException("File already exists");
        }
        BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
        FileOutputStream fos = new FileOutputStream(file);
        BufferedOutputStream bos = new BufferedOutputStream(fos);

        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = bis.read(buffer)) != -1) {
            bos.write(buffer, 0, bytesRead);
        }
        
        bos.close();
        fos.close();
        bis.close();
    }

    public void listFiles(PrintWriter out) {
        File[] files = serverDirectory.listFiles();
        for (File file : files) {
            out.println(file.getName());
        }
    }
}
