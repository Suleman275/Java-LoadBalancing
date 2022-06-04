import java.io.*;
import java.net.*;

public class Server {
   // variables
   private ServerSocket serverSocket;

   // functions

   // start function
   public void start(int port) throws IOException {
      serverSocket = new ServerSocket(port);
      while (true)
         new EchoClientHandler(serverSocket.accept()).start();
   }

   // stop function
   public void stop() throws IOException {
      serverSocket.close();
   }

   // client handler for multithreading
   private static class EchoClientHandler extends Thread {
      private Socket clientSocket;
      private PrintWriter out;
      private BufferedReader in;

      // constructor
      public EchoClientHandler(Socket socket) {
         this.clientSocket = socket;
      }

      public void run() {
         try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String inputLine;

            while ((inputLine = in.readLine()) != null) {
               if (".".equals(inputLine)) {
                  out.println("bye");
                  break;
               }
               out.println(inputLine);
            }

            in.close();
            out.close();
            clientSocket.close();
         } catch (IOException e) {
            e.toString();
         }
      }
   }
}