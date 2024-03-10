import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class will be used to create a logging operation which 
 * logs any possible successful transaction / activtiy made by the 
 * client inside a log file called log.txt. The format of logging the 
 * activity follows the fllowing sequence of Date | time | Client IP address | request .
 * 
 */

public class Log {
    // The name of the log file
    private String logFileName;
    
    // The class constructor
    public Log(String logFileName)
    {
        this.logFileName = logFileName;
    }

    // Entry method to log a message
    public void logging(String clientIP, String request)
    {
        String formattedDateTime = getFormattedDateTime();
        String logMessage = createLogMessage(formattedDateTime, clientIP, request);
        writeToLogFile(logMessage);
    }

    // Get formatted date and time
    private String getFormattedDateTime() 
    {
        // Set the date and time format
        Date date = new Date();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
        return dateFormatter.format(date) + " | " + timeFormatter.format(date);
    }

    // Create log message
    private String createLogMessage(String formattedDateTime, String clientIP, String request)
    {
        return formattedDateTime + " | " + clientIP + " | " + request;
    }

    // Write log message to the file
    private void writeToLogFile(String logMessage)
    {
        // Write the log message to the log file
        try (FileWriter fileWriter = new FileWriter(logFileName, true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {
            printWriter.println(logMessage);
        } catch (IOException e) {
            System.out.println("Logger ERROR: " + e.getMessage());
        }
    }
}
