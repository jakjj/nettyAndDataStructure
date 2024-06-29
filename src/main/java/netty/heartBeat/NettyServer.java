package netty.heartBeat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class NettyServer {

    private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);

    private final String host;
    private final int port;

    public NettyServer(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run(){
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        try {
            serverBootstrap.group(bossGroup,workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
//                            pipeline.addLast("CombinedByteCharCodec",new CombinedByteCharCodec());
                            pipeline.addLast("encode", new StringEncoder());
                            pipeline.addLast("decode",new StringDecoder());
                            pipeline.addLast(new IdleStateHandler(0,0,6, TimeUnit.SECONDS));
                            pipeline.addLast("heartBeat",new HeartBeatInboundHandler());
                            pipeline.addLast("socketHandler",new NettyServerHandler());
                        }
                    });

            logger.info("server is started------------");
            ChannelFuture sync = serverBootstrap.bind(host, port).sync();

            ChannelFuture sync1 = sync.channel().closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) {
        NettyServer nettyServer = new NettyServer("127.0.0.1", 6655);
        nettyServer.run();
    }

}
