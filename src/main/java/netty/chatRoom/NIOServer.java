package netty.chatRoom;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {

    private Selector selector;

    private ServerSocketChannel serverSocketChannel;

    private static final int PORT = 6655;

    public static void main(String[] args) {
        NIOServer server = new NIOServer();
        server.listen();
    }

    public NIOServer() {

        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void listen(){

        try {
            while(true){

                if(selector.select(1000) == 0){
//                    System.out.println("等待连接--");
                }else{
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while(iterator.hasNext()){
                        SelectionKey key = iterator.next();
                        if(key.isAcceptable()){
                            System.out.println("建立连接请求---");
                            SocketChannel socketChannel = serverSocketChannel.accept();
                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                            String s = socketChannel.getRemoteAddress() + " 已上线";

                            System.out.println(s);
                            sendOthers(null,s);
                        }

                        //read逻辑
                        if(key.isReadable()){
                            readData(key);
                        }
                        iterator.remove();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void readData(SelectionKey key){
        SocketChannel channel = null;
        try {
            channel = (SocketChannel)key.channel();
            ByteBuffer attachment = (ByteBuffer)key.attachment();
            int read = channel.read(attachment);
            if(read > 0){
                String msg = new String(attachment.array());
                System.out.println("客户端发来信息：" + msg);
                sendOthers(key,msg);
            }
        } catch (IOException e) {
            try {
                String s = channel.getRemoteAddress() + " 已下线";
                System.out.println(s);
                sendOthers(key,s);
                key.cancel();
                channel.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public void sendOthers(SelectionKey key,String msg) throws IOException {
        Set<SelectionKey> keys = selector.keys();
        for(SelectionKey item : keys){
            Channel channel = item.channel();
            if(item != key && channel instanceof SocketChannel){
                SocketChannel target = (SocketChannel) channel;
                ByteBuffer wrap = ByteBuffer.wrap(msg.getBytes());
                target.write(wrap);
                wrap.flip();
            }
        }
    }

}
