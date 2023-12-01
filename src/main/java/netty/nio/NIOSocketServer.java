package netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NIOSocketServer {

    public static void main(String[] args) {
        try {
            ServerSocketChannel socketChannel = ServerSocketChannel.open();
            InetSocketAddress address = new InetSocketAddress(7666);
            socketChannel.socket().bind(address);


            ByteBuffer[] buffers = new ByteBuffer[2];
            buffers[0] = ByteBuffer.allocate(8);
            buffers[1] = ByteBuffer.allocate(5);

            SocketChannel accept = socketChannel.accept();




            while(true){

                long read = accept.read(buffers);

            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
