package netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class HttpChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        //HttpServerCodec 是 netty 提供的 http编-解码器
        pipeline.addLast("MyHttpServerCodec",new HttpServerCodec());
        pipeline.addLast("MyHttpHandle",new HttpServerChannelAdapter());
    }
}
