/////////////////////////////////////////////////////////////
//
// Authors:  Shane Stacy, Kyler Kupres
// Date: 01/19/2020
// Description:  This class is a static web server serving HTML, images, and other documents.
//               Handles 404, 400 errors as well as generates directory indexes on-the-fly.
/////////////////////////////////////////////////////////////
import java.net.*;
import java.io.*;
import java.util.*;

// This is mostly a copy of the sample code.  Use what you want, delete the rest.
// Note: This code is not well organized. It should be broken into smaller methods.

public class MyStaticWebServer {

    // You can use this to map extensions to MIME types. Feel free to add other mappings as desired.
    private static final HashMap<String, String> extensions = new HashMap<String, String>();
    static {
        extensions.put("jpeg", "image/jpeg");
        extensions.put("jpg", "image/jpeg");
        extensions.put("png", "image/png");
        extensions.put("gif", "image/gif");
        extensions.put("html", "text/html");
        extensions.put("htm", "text/html");
        extensions.put("pdf", "application/pdf");
        extensions.put("ico", "image/vnd.microsoft.icon");
    }

    //  This method retrieves the content type from the hash map
    public static String getType(String filename) {
        String type = "text/plain";
        String extension = getExtension(filename);
        if (!extension.equals("unknown")) {
            if (extensions.containsKey(extension)) {
                type = extensions.get(extension);
            }
        }
        return type;
    }

    //  This method retrieves the file extension
    public static String getExtension(String filename) {
        String extension = "unknown";
        int i = filename.lastIndexOf('.');
        if (i > 0) {
            extension = filename.substring(i + 1);
        }
        return extension;
    }

    //  send out the 404 response
    public static void send404(PrintStream out, String toPrint) {
        out.println("HTTP/1.1 404 Not Found");
        out.println("Content-Type: text/html");
        out.println("Content-Length: " + toPrint.length());
        out.println("Connection: close");
        out.println("");
        out.println(toPrint);
    }

    //  send out the 400 response
    public static void send400(PrintStream out, String toPrint) {
        out.println("HTTP/1.1 400 Bad Request");
        out.println("Content-Type: text/html");
        out.println("Content-Length: " + toPrint.length());
        out.println("Conncetion: close");
        out.println("");
        out.println(toPrint);
    }

    //  method streamlines sending files
    public static void sendFile(PrintStream out, String filename) throws IOException {
        File f = new File(filename);
        FileInputStream fis = new FileInputStream(f);
        String contentType = getType(filename);
        // Respond
        out.println("HTTP/1.1 200 OK");
        out.println("Content-Type: " + contentType);
        out.println("Content-Length: " + f.length());
        out.println("Connection: close");
        out.println("");
        // read data from the file and send it to the client.
        byte[] buffer = new byte[8192];
        int read = fis.read(buffer);
        while (read != -1) {
            out.write(buffer, 0, read);
            read = fis.read(buffer);
        }
        fis.close();
    }

    //  build and send a directory's contents with links to the content the directory has in it
    public static void buildAndSendDirectory (PrintStream out, String filename) throws IOException {
        File folder = new File(filename);
        File[] listOfFiles = folder.listFiles();
        List<File> list = Arrays.asList(listOfFiles);
        System.out.println(list);
        File f = new File(filename + "index.html");
        if (list.contains(f) && f.exists() && f.isFile() && f.canRead()) {
            sendFile(out, filename + "index.html");
        }
        else {
            //  start the toPrint with the html header
            String toPrint = "<html><body>In this directory: " + filename;

            //  construct the index.html with hyperlinks to the resources
            for (int i = 0; i < listOfFiles.length; ++i) {
                if (listOfFiles[i].isFile()) {
                    toPrint += "<br>File <a href=\"" + listOfFiles[i].getName() + "\">" + listOfFiles[i].getName() + "</a> ";
                } else if (listOfFiles[i].isDirectory()) {
                    toPrint += "<br>Directory <a href=\"" + listOfFiles[i].getName() + "/\">" + listOfFiles[i].getName() + "</a> ";
                }
            }
            //  close the html
            toPrint += "</body></html>";
            //  dispatch the response
            out.println("HTTP/1.1 200 OK");
            out.println("Content-Type: text/html");
            out.println("Content-Length: " + toPrint.length());
            out.println("Connection: close");
            out.println("");
            out.println(toPrint);
        }
    }

    //  main execution for the java web server
    public static void main(String[] args) throws IOException {

        // Create a socket that listens on port 8534.
        int port = 8534;
        ServerSocket serverSocket = new ServerSocket(port);

        // Handle multiple requests sequentially
        while (true) {
            System.out.println("\n\nAwaiting new connection on port " + port);
            System.out.println("Working directory is " + System.getProperty("user.dir"));

            // Return a Socket object for the next connection in the queue
            Socket socket = serverSocket.accept();

            // Created a BufferedReader that can read from the socket
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Create a PrintStream than can write to the socket
            // Passing "true" as the second parameter causes each write to be followed by a
            // flush.
            PrintStream out = new PrintStream(socket.getOutputStream(), true);

            // Read the main command.
            String command = input.readLine();
            System.out.println("Command Received: =>" + command + "<=");

            // Read the request headers
            System.out.println("\nRequest Headers:");
            String headerLine = input.readLine();
            while (headerLine != null && !headerLine.isEmpty()) {
                System.out.println("\t" + headerLine);
                headerLine = input.readLine();
            }

            // split the command by spaces.
            String[] parts = command.split("\\s+");
            if (command==null) {
                continue;
            }

            System.out.printf("Command; %s; path %s; protocol %s\n", parts[0], parts[1], parts[2]);

            String filename = parts[1];
            boolean isFile = false;

            // If the path begins with "/", remove the "/".
            if (filename.startsWith("/")) {
                filename = filename.substring(1);
            }

            // if the path ends with a /, it's not a file
            if (!filename.endsWith("/")) {
                isFile = true;
            }

            //  if the filename leads to a file, handle it like a file
            if (isFile == true) {
                File f = new File(filename);

                // send 404 if file doesn't exist, or is not readable.
                if (!f.exists() || !f.isFile() || !f.canRead()) {
                    System.out.println(filename + " not found.  Returning 404.");
                    String toPrint = "<html><body>Problem finding/reading \"" + filename + "\"</body></html>";
                    send404(out, toPrint);
                    socket.close();
                    continue;
                }

                //  send 400 if the file can't be read from the server / server does not
                //  understand the command.
                try {
                    FileInputStream fis = new FileInputStream(f);
                    fis.close();
                }
                catch (Exception e) {
                    String toPrint = "<html><body>Problem opening/reading \"" + filename + "\"</body></html>";
                    send400(out, toPrint);
                    socket.close();
                    break;
                }
                sendFile(out, filename);
            }
            else {
                //  otherwise, handle it like a directory.
                //  meaning we have to return an index.html if it exists,
                //  otherwise, we must make one on-the-fly
                buildAndSendDirectory(out, filename);
            }
            socket.close();
        }
        serverSocket.close();
        // When the connection ends, so does this program.
    }
}
