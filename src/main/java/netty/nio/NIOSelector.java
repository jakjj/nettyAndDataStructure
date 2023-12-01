package netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NIOSelector {

    public static void main(String[] args) {
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(7777));
            //设置为非阻塞
            serverSocketChannel.configureBlocking(false);

            Selector selector = Selector.open();

            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            while(true){

                if(selector.select(1000) == 0){
                    System.out.println("服务器等待1s，无连接");
                }

                // >0，代表获取到请求
                //获取selectionKeys
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                //使用迭代器遍历，多线程需要处理一个后及时删除
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while(iterator.hasNext()){
                    SelectionKey selectionKey = iterator.next();
                    System.out.println("selectionKey:"+selectionKey);
                    if(selectionKey.isAcceptable()){
                        System.out.println("建立连接请求-----"+selectionKey);
                        //创建channel，accept()阻塞，但请求已存在，会被马上处理
                        SocketChannel channel = serverSocketChannel.accept();
                        //需要与serverSocketChannel设置一致，否则报错
                        channel.configureBlocking(false);
                        //注册至selector
                        channel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                    }
                    if(selectionKey.isReadable()){
                        System.out.println("read 请求------");
                        SocketChannel channel1 = (SocketChannel)selectionKey.channel();
                        ByteBuffer byteBuffer = (ByteBuffer)selectionKey.attachment();
                        channel1.read(byteBuffer);
                        System.out.println("客户端发来内容：" + new String(byteBuffer.array()));
                        byteBuffer.clear();
                    }
                    //防止多线程重复处理
                    iterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
