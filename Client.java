import java.io.*;
import java.net.*;

public class Client {
   // variables
   private Socket clientSocket;
   private PrintWriter out;
   private BufferedReader in;

   // functions

   // start connection
   public void startConnection(String ip, int port) throws IOException {
      clientSocket = new Socket(ip, port);
      out = new PrintWriter(clientSocket.getOutputStream(), true);
      in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
   }

   // stop connection
   public void stopConnection() throws IOException {
      in.close();
      out.close();
      clientSocket.close();
   }

   // send directory request
   public String sendDirRequest() throws IOException {
      out.println("1");
      String resp = in.readLine();
      return resp;
   }

   // send file to server

   // ask to stream a video
   public String vidReq() throws IOException {
      out.println("3");
      String resp = in.readLine();
      return resp;
   }

   // ask to perform computation
   public String compReq() throws IOException {
      out.println("4");
      String resp = in.readLine();
      return resp;
   }

   // send string
   public String sendMessage(String msg) throws IOException {
      out.println("2" + msg);
      String resp = in.readLine();
      return resp;
   }

   // request file
   public String reqFile() throws IOException {
      out.println("5");
      // String resp = in.readLine();
      // return resp;

      DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream());

      int bytes = 0;
      FileOutputStream fileOutputStream = new FileOutputStream("C:\\temp\\why.txt");

      long size = dataInputStream.readLong(); // read file size
      byte[] buffer = new byte[4 * 1024];
      while (size > 0 && (bytes = dataInputStream.read(buffer, 0,
            (int) Math.min(buffer.length, size))) != -1) {
         fileOutputStream.write(buffer, 0, bytes);
         size -= bytes; // read upto file size
      }
      fileOutputStream.close();
      return "done";
   }
}