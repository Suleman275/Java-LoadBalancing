import java.io.*;
import java.net.*;

public class Server {
   // variables
   private ServerSocket serverSocket;

   // functions

   // start function
   public void start(int port) throws IOException {
      serverSocket = new ServerSocket(port);
      System.out.println("Starting Server");
      while (true)
         new ClientHandler(serverSocket.accept()).start();
   }

   // stop function
   public void stop() throws IOException {
      serverSocket.close();
   }

   // client handler for multithreading
   private static class ClientHandler extends Thread {
      private Socket clientSocket;
      private PrintWriter out;
      private BufferedReader in;

      // constructor
      public ClientHandler(Socket socket) {
         this.clientSocket = socket;
      }

      public void run() {
         try {
            System.out.println("Thread Created");
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());

            String inputLine;

            // serving request until termination clause
            while ((inputLine = in.readLine()) != null) {
               System.out.println("Server is running");
               if (".".equals(inputLine)) {
                  out.println("bye");
                  break;
               } else if (inputLine.charAt(0) == '1') { // directory
                  out.println(
                        "This server is able to show you the directory and a video! You can also request to perform a computation or upload a file!");
               } else if (inputLine.charAt(0) == '2') { // message to upper case
                  out.println(inputLine.toUpperCase());
               } else if (inputLine.charAt(0) == '3') { // video streaming
                  System.out.println("Server is showing a video for 10 seconds");
                  out.println("Server is showing a video for 10 seconds");
                  Thread.sleep(10000);
               } else if (inputLine.charAt(0) == '4') { // computation
                  System.out.println("Server is performing a compitation for 10 seconds");
                  out.println("Server is performing a compitation for 10 seconds");
                  Thread.sleep(10000);
               } else if (inputLine.charAt(0) == '5') { // send file
                  System.out.println("Server is sending file");
                  // out.println("Server is performing a compitation for 10 seconds");
                  // Thread.sleep(10000);
                  
                  

                  int bytes = 0;
                  File file = new File("C:\\temp\\how.txt");
                  FileInputStream fileInputStream = new FileInputStream(file);

                  // send file size
                  dataOutputStream.writeLong(file.length());
                  // break file into chunks
                  byte[] buffer = new byte[4 * 1024];
                  while ((bytes = fileInputStream.read(buffer)) != -1) {
                     dataOutputStream.write(buffer, 0, bytes);
                     dataOutputStream.flush();
                  }
                  fileInputStream.close();
               }
            }

            // closing connectionn
            System.out.println("Closing connection");
            in.close();
            out.close();
            clientSocket.close();
         } catch (Exception e) {
            e.toString();
         }
      }
   }

   public static void main(String[] args) throws IOException {
      Server server = new Server();
      server.start(4444);
   }
}