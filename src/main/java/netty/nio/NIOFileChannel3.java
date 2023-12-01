package netty.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel3 {

    public static void main(String[] args) {

        String url1 = "D:\\file1.txt";
        String url2 = "D:\\file2.txt";

        File file = new File(url1);
        FileInputStream in = null;
        FileOutputStream out = null;
        try{
            in = new FileInputStream(file);
            out = new FileOutputStream(url2);
            FileChannel inChannel = in.getChannel();
            FileChannel outChannel1 = out.getChannel();

            ByteBuffer buffer = ByteBuffer.allocate(5);
            while(true){
                buffer.clear();
                int read = inChannel.read(buffer);
                if(read != -1){
                    buffer.flip();
                    outChannel1.write(buffer);
//                    buffer.flip();
                }else{
                    return;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(in != null){
                    in.close();
                }
                if(out != null){
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
