package netty.chatRoom;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class NIOClient {

    private static final int SERVER_PORT = 6655;
    private static final String SERVER_HOST = "127.0.0.1";

    private Selector selector;
    private SocketChannel socketChannel;
    private String username;

    public NIOClient() {

        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open(new InetSocketAddress(SERVER_HOST,SERVER_PORT));
            socketChannel.configureBlocking(false);

            socketChannel.register(selector, SelectionKey.OP_READ,ByteBuffer.allocate(1024));

            username = socketChannel.getLocalAddress().toString();

            System.out.println("------ username:" + username);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendInfo(String info){
        info = username + "说：" + info;
        try {
            socketChannel.write(ByteBuffer.wrap(info.getBytes()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readInfo(){

        try {
            int select = selector.select();
            if(select > 0){
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while(iterator.hasNext()){
                    SelectionKey next = iterator.next();
                    if(next.isReadable()){
                        SocketChannel channel = (SocketChannel)next.channel();
                        ByteBuffer attachment = (ByteBuffer)next.attachment();
                        attachment.clear();
                        channel.read(attachment);
                        System.out.println(new String(attachment.array()));
                    }
                    iterator.remove();
                }
            }else{
//                System.out.println("无消息");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {

        NIOClient client = new NIOClient();

        new Thread(){
            @Override
            public void run() {

                while(true){
                    client.readInfo();

                    try {
                        sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }.start();

        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNextLine()){
            client.sendInfo(scanner.nextLine());
        }

    }
}
