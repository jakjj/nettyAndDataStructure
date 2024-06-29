package netty.udp.monitor;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;
import netty.udp.broadcast.LogEventBroadcaster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

public class LogEventMonitor {

    private static final Logger logger = LoggerFactory.getLogger(LogEventMonitor.class);

    private final NioEventLoopGroup group;

    private final Bootstrap bootstrap;

    public LogEventMonitor(InetSocketAddress address) {
        this.group = new NioEventLoopGroup();
        this.bootstrap = new Bootstrap();

        bootstrap.group(group)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST,true)
                .handler(new ChannelInitializer<NioDatagramChannel>() {
                    @Override
                    protected void initChannel(NioDatagramChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast("LogEventDecoder",new LogEventDecoder());
                        pipeline.addLast("LogEventHandler",new LogEventHandler());
                    }
                })
                //
                .localAddress(address);
    }

    public Channel bind(){
        return bootstrap.bind().syncUninterruptibly().channel();
    }

    public void stop(){
        group.shutdownGracefully();
    }

    public static void main(String[] args) {
        if(args.length != 1){
            throw new IllegalArgumentException();
        }
        LogEventMonitor logEventMonitor = new LogEventMonitor(new InetSocketAddress(Integer.parseInt(args[0])));

        try {
            Channel channel = logEventMonitor.bind();
            logger.info("LogEventMonitor is running");
            channel.closeFuture().sync();
        } catch (Exception e) {
//            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            logEventMonitor.stop();
        }


    }

}
