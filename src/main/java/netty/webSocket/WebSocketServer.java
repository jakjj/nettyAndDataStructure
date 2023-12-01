package netty.webSocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class WebSocketServer {

    public void run() throws InterruptedException {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        try {
            serverBootstrap.group(bossGroup,workGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            //基于http的编/解码器
                            pipeline.addLast(new HttpServerCodec());
                            //以块形式写
                            pipeline.addLast(new ChunkedWriteHandler());
                            /*
                                http数据在传输过程中是分段的，HttpObjectAggregator 可以将数据整合
                                所以浏览器发送大量请求时，会发出多个http请求
                             */
                            pipeline.addLast(new HttpObjectAggregator(8192));

                            /*
                                对应webSocket，数据是以帧（frame）形式传输
                                WebSocketFrame下面有六个子类
                                请求链接为 ws://localhost:7001/hello
                                WebSocketServerProtocolHandler 核心功能为将http协议升级为webSocket协议
                                浏览器控制台中 成功的请求中 Status Code:101 Switching Protocols
                             */
                            pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));
                            pipeline.addLast(new WebSocketServerHandler());
                        }
                    });

            ChannelFuture future = serverBootstrap.bind(7001).sync();
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        try {
            new WebSocketServer().run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
