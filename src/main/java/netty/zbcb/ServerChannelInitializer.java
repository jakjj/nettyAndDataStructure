package netty.zbcb;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("messageDecoder",new MyMessageDecoder());
        pipeline.addLast("messageEncoder",new MyMessageEncoder());
        pipeline.addLast(new ServerHandler());
    }
}
