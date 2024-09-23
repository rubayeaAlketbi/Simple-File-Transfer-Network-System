# Simple File Transfer System

## Overview

This project implements a simple file transfer system using client-server architecture in Java. The system allows clients to request a list of text files on the server and upload new files. The server handles multiple clients using a multi-threaded design.

## Features
- **Client**:
  - `list`: Lists all files on the server.
  - `put fname`: Uploads a file (`fname`) to the server. Returns an error if the file already exists.
  
- **Server**:
  - Manages up to 20 concurrent client connections using a thread pool.
  - Stores uploaded files in the `serverFiles` directory.
  - Logs all valid client requests in `log.txt`.

## Directory Structure

cwk/ ├── client/ │ ├── Client.java │ └── lipsum2.txt (example file to upload) ├── server/ │ ├── Server.java │ └── serverFiles/ │ └── lipsum1.txt (example file on the server)

## How to Run

### Server Setup
1. Navigate to the `server` directory and compile the Java files:
   ```bash
   cd cwk/server
   javac Server.java
2. Start the server:
    ```bash
   java Server
    ```
### Client Setup
1. In another terminal, navigate to the client directory and compile the Java files:
    ```bash
   cd cwk/client
   javac Client.java
2. Execute the client with the appropriate command:

      To list all files on the server:
    ```bash
      java Client list
    ```
      To upload a file to the server:
   ```
      java Client put lipsum2.txt
   ```

