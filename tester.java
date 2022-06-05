import java.io.IOException;

import javax.sound.sampled.SourceDataLine;

public class tester {
   public static void main(String[] args) throws IOException {
      Client cc = new Client();
      cc.startConnection("127.0.0.1", 4444);
      System.out.println(cc.sendDirRequest());
      System.out.println(cc.vidReq());
       System.out.println(cc.sendDirRequest());
       System.out.println(cc.sendDirRequest());
   }
}
