package netty.zbcb;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class MyClient {

    public void run() throws InterruptedException {
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(workGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ClientChannelInitializer());

            ChannelFuture connect = bootstrap.connect("127.0.0.1", 8521);
            connect.channel().closeFuture().sync();
        } finally {
            workGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        try {
            new MyClient().run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
