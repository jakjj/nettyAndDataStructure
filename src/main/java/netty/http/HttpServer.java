package netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class HttpServer {
    public static void main(String[] args) {

        try {
            NioEventLoopGroup bossGroup = new NioEventLoopGroup();
            NioEventLoopGroup workGroup = new NioEventLoopGroup();

            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .childHandler(new HttpChannelInitializer());

            ChannelFuture future = serverBootstrap.bind(6658).sync();

            future.channel().closeFuture().sync();

            future.addListener((ChannelFutureListener) future1 -> {
                if(future1.isSuccess()){
                    System.out.println("服务器 监听6658 port 成功");
                }else{
                    System.out.println("服务器 监听6658 port 失败");
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
