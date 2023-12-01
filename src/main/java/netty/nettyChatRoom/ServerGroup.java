package netty.nettyChatRoom;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class ServerGroup {

    public void run(int port) throws InterruptedException {

        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,18)
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("decoder", new StringDecoder());
                            pipeline.addLast("encoder",new StringEncoder());

                            //IdleStateHandler:netty 提供的处理空闲状态的处理器
                            //readerIdleTime : 写空闲超时时间，吵过后会发送心跳检测包检测是否连接
                            //writerIdleTime : 读写空闲超时时间，吵过后会发送心跳检测包检测是否连接
                            //allIdleTime : 读写空闲超时时间，吵过后会发送心跳检测包检测是否连接
                            pipeline.addLast(new IdleStateHandler(3,5,7, TimeUnit.SECONDS));
                            pipeline.addLast(new ServerHandler());
                        }
                    });

            System.out.println("server started");
            ChannelFuture future = serverBootstrap.bind(port).sync();

            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) {
        try {
            new ServerGroup().run(6654);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
