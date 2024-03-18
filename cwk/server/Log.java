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
        // Set the log file name
        this.logFileName = logFileName;
    }

    // Entry method to log a message
    public void logging(String clientIP, String request)
    {
        // Get the formatted date and time
        String formattedDateTime = getFormattedDateTime();
        // Create the log message
        String logMessage = createLogMessage(formattedDateTime, clientIP, request);
        // Write the log message to the log file
        writeToLogFile(logMessage);
    }

    // Get formatted date and time
    private String getFormattedDateTime() 
    {
        // Set the date and time format
        Date date = new Date();
        // Create a date and time formatter
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
        // Return the formatted date and time
        return dateFormatter.format(date) + " | " + timeFormatter.format(date);
    }

    // Create log message
    private String createLogMessage(String formattedDateTime, String clientIP, String request)
    {
        // Return the log message
        return formattedDateTime + " | " + clientIP + " | " + request;
    }

    // Write log message to the file
    private void writeToLogFile(String logMessage)
    {
        // Write the log message to the log file
        try (FileWriter fileWriter = new FileWriter(logFileName, true);
            // Create a PrintWriter object for writing to the file
             PrintWriter printWriter = new PrintWriter(fileWriter)) {
                // Write the log message to the file
                printWriter.println(logMessage);
        } catch (IOException e) {
            System.out.println("Logger ERROR: " + e.getMessage());
        }
    }
}
