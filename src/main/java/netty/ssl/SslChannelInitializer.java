package netty.ssl;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

public class SslChannelInitializer extends ChannelInitializer<Channel> {

    private final SslContext context;
    private final boolean startTls;

    public SslChannelInitializer(SslContext context, boolean startTls) {
        this.context = context;
        this.startTls = startTls;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {

        //对于每一个SslHandler实例，都使用Channel的ByteBuf-Allocator从SslContext获取一个新的SSLEngine
        SSLEngine sslEngine = context.newEngine(channel.alloc());
        //将SslHandler作为第一个ChannelHandler添加到ChannelPipeline
        channel.pipeline().addLast("ssl",new SslHandler(sslEngine,startTls));
    }
}
