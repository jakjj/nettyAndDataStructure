package netty.webSocket.chatRoom;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.ImmediateEventExecutor;

import java.net.InetSocketAddress;

public class Server {

    private final ChannelGroup channelGroup = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);
    private Channel channel;
    NioEventLoopGroup bossGroup = new NioEventLoopGroup();

    public ChannelFuture start(InetSocketAddress port){
        String host= "127.0.0.1";


        ServerBootstrap serverBootstrap = new ServerBootstrap();

        serverBootstrap.group(bossGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChatServerInitializer(channelGroup));

        ChannelFuture future = serverBootstrap.bind(port);

        future.syncUninterruptibly();

        channel = future.channel();

        return future;
    }

    public void destory(){
        if(channel != null){
            channel.close();
        }
        channelGroup.close();
        bossGroup.shutdownGracefully();
    }

    public static void main(String[] args) {
        int port = Integer.parseInt(args[0]);

        Server server = new Server();
        ChannelFuture future = server.start(new InetSocketAddress(port));

        Runtime.getRuntime().addShutdownHook(new Thread(server::destory));

        //syncUninterruptibly()：阻塞当前线程直到执行完成，不会被`InterruptedException`中断
        future.channel().closeFuture().syncUninterruptibly();

    }

}
