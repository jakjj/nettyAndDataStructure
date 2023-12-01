package netty.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class NIOMapperedBuffer {
    public static void main(String[] args) {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile("1.txt","rw");
            FileChannel channel = randomAccessFile.getChannel();

            //参数一：模式
            //参数二：起始位置
            //参数三：映射到内存中的大小（可修改内容size
            MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 50);
            mappedByteBuffer.put(0,(byte)'H');
            mappedByteBuffer.put(5,(byte)'9');
            channel.close();
            randomAccessFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
