package netty.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel2 {

    public static void main(String[] args) {

        String url2 = "D:\\file2.txt";

        try {
            File file = new File(url2);
            FileInputStream in1 = new FileInputStream(url2);
            FileChannel channel = in1.getChannel();

            ByteBuffer buffer = ByteBuffer.allocate((int) file.length());

            channel.read(buffer);
            System.out.println(new String(buffer.array()));
            in1.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
