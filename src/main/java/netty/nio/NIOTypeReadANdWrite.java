package netty.nio;

import java.io.*;
import java.nio.channels.FileChannel;

public class NIOTypeReadANdWrite {

    public static void main(String[] args) {
        String uri = "E:\\picture\\r&m_day.jpg";

        try {
            FileInputStream in = new FileInputStream(new File(uri));

            FileChannel source = in.getChannel();



            source.close();
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }
}
