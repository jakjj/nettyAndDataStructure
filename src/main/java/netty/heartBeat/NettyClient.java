package netty.heartBeat;

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
import netty.codec.codecer.CombinedByteCharCodec;
import netty.nettyChatRoom.ClientHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class NettyClient {

    private static final Logger logger = LoggerFactory.getLogger(NettyClient.class);

    public void run(String host,int port){

        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();

        try {
            bootstrap.group(eventExecutors)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
//                            pipeline.addLast("CombinedByteCharCodec",new CombinedByteCharCodec());
                            pipeline.addLast("encode", new StringEncoder());
                            pipeline.addLast("decode",new StringDecoder());
                            pipeline.addLast("socketHandler",new ClientHandler());
                        }
                    });
            ChannelFuture future = bootstrap.connect(host, port).sync();

            logger.info("client is connected: {}",future);

            Channel channel = future.channel();
            System.out.println("client is start " + channel.localAddress());
            Scanner scanner = new Scanner(System.in);
            while(scanner.hasNextLine()){
                String msg = scanner.nextLine();
                channel.writeAndFlush(msg + "\r\n");
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            eventExecutors.shutdownGracefully();

        }
    }

    public static void main(String[] args) {
        String host = args[0];
        int port = Integer.parseInt(args[1]);

        logger.info("host:{},port:{}",host,port);

        NettyClient nettyClient = new NettyClient();
        nettyClient.run(host,port);

    }
}
