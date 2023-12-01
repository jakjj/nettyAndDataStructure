package netty.nio;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel {

    public static void main(String[] args) {
        String url1 = "D:\\file1.txt";
        String url2 = "D:\\file2.txt";

        String msg = "test2 Print Msg";
        try {
            FileOutputStream out1 = new FileOutputStream(url1);
            FileChannel fileChannel1 = out1.getChannel();
            FileOutputStream out2 = new FileOutputStream(url2);
            FileChannel fileChannel2 = out2.getChannel();

            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.put(msg.getBytes());
            buffer.flip();
            fileChannel1.write(buffer);
            buffer.flip();
            fileChannel2.write(buffer);

            out1.close();
            out2.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }
}
