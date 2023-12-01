package netty.nettyChatRoom;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

public class ClientGroup {

    private final int port;
    private final String host;

    public ClientGroup(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() throws InterruptedException {

        NioEventLoopGroup clientGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();

        try {
            bootstrap.group(clientGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("encode", new StringEncoder());
                            pipeline.addLast("decode",new StringDecoder());
                            pipeline.addLast(new ClientHandler());
                        }
                    });

            ChannelFuture future = bootstrap.connect(host, port).sync();

            Channel channel = future.channel();
            System.out.println("client is start " + channel.localAddress());
            Scanner scanner = new Scanner(System.in);
            while(scanner.hasNextLine()){
                String msg = scanner.nextLine();
                channel.writeAndFlush(msg + "\r\n");
            }

        } finally {
            clientGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        try {
            new ClientGroup("127.0.0.1", 6654).run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
