package netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NIOClient {

    public static void main(String[] args) {
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            InetSocketAddress address = new InetSocketAddress("127.0.0.1",7777);

            if(!socketChannel.connect(address)){
                while(!socketChannel.finishConnect()){
                    System.out.println("连接中------");
                }
            }

            String str = "hello,this is client";
            ByteBuffer byteBuffer = ByteBuffer.wrap(str.getBytes());

            socketChannel.write(byteBuffer);
            //卡住客户端
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
