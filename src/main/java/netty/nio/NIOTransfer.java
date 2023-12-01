package netty.nio;

import java.io.*;
import java.nio.channels.FileChannel;

public class NIOTransfer {
    public static void main(String[] args) {
        String uri = "E:\\picture\\r&m_day.jpg";
        String uri2 = "E:\\picture\\r&m_day_copy.jpg";

        try {
            FileInputStream in = new FileInputStream(new File(uri));
            FileOutputStream out = new FileOutputStream(new File(uri2));

            FileChannel source = in.getChannel();
            FileChannel target = out.getChannel();

            source.transferTo(0,source.size(),target);

            source.close();
            target.close();
            in.close();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }
}
