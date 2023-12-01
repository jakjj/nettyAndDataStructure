package netty.zbcb;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.concurrent.DefaultEventExecutorGroup;


public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("messageDecoder",new MyMessageDecoder());
        DefaultEventExecutorGroup eventExecutors = new DefaultEventExecutorGroup(5);
        pipeline.addLast(eventExecutors,"messageEncoder",new MyMessageEncoder());
        pipeline.addLast(new ClientHandler());
    }
}
