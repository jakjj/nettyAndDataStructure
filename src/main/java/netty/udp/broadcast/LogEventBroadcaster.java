package netty.udp.broadcast;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;
import jdk.jfr.events.SocketReadEvent;
import netty.udp.monitor.LogEventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;

public class LogEventBroadcaster {

    private static final Logger logger = LoggerFactory.getLogger(LogEventBroadcaster.class);
    private final NioEventLoopGroup eventLoopGroup;

    private final Bootstrap bootstrap;

    private final File file;


    public LogEventBroadcaster(InetSocketAddress address, File file) {
        this.eventLoopGroup = new NioEventLoopGroup();
        this.bootstrap = new Bootstrap();
        this.file = file;


        //channel类型是NioDatagramChannel
        //这里只添加了一个handler
        // 结合channelInitialzer的描述--当其将handlers关联到pipeline后将自身从pipeline中去除。
        // 可知，handler()本就是接收单个handler的
        bootstrap.group(eventLoopGroup)
                .channel(NioDatagramChannel.class)
                //设置SO_BROADCAST（广播 选项
                .option(ChannelOption.SO_BROADCAST,true)
                .handler(new LogEventEncoder(address));

    }

    public void run() throws Exception {
        Channel channel = bootstrap.bind(0).sync().channel();

        long pointer = 0;
        for(;;){
            long length = file.length();
            if(length < pointer){
                pointer = length;
            }else {
                RandomAccessFile randomAccessFile = new RandomAccessFile(file,"r");
                randomAccessFile.seek(pointer);

                String line;
                while((line = randomAccessFile.readLine()) != null){
                    logger.info("send: {}" , line);
                    channel.writeAndFlush(new LogEvent(file.getAbsolutePath(),line));
                }

                pointer = randomAccessFile.getFilePointer();
                randomAccessFile.close();
            }

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
                Thread.interrupted();
                break;
            }
            pointer = 0;
        }
    }

    public void stop(){
        eventLoopGroup.shutdownGracefully();
    }

    public static void main(String[] args) {
        if(args.length != 2){
            throw new IllegalArgumentException();
        }

        //255.255.255.255 代表当前局域网所有
        LogEventBroadcaster logEventBroadcaster = new LogEventBroadcaster(
                new InetSocketAddress("255.255.255.255",Integer.parseInt(args[0])),new File(args[1]));

        try {
            logEventBroadcaster.run();
        } catch (Exception e) {
//            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            logEventBroadcaster.stop();
        }

    }


}
