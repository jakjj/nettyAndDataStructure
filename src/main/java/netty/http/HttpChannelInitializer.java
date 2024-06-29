package netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.*;

public class HttpChannelInitializer extends ChannelInitializer<SocketChannel> {


    private final boolean isClient;

    public HttpChannelInitializer(boolean isClient) {
        this.isClient = isClient;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        if(isClient){
//            pipeline.addLast("httpDecoder",new HttpResponseDecoder());
//            pipeline.addLast("httpEncoder",new HttpRequestEncoder());
            pipeline.addLast("httpClientCodecer",new HttpClientCodec());
        }else{

//            pipeline.addLast("httpDecoder",new HttpRequestDecoder());
//            pipeline.addLast("httpEncoder",new HttpResponseEncoder());
            //HttpServerCodec 是 netty 提供的 http编-解码器
            pipeline.addLast("MyHttpServerCodec",new HttpServerCodec());
        }
//        pipeline.addLast("MyHttpHandle",new HttpServerChannelAdapter());
        //将最大的消息大小为512KB的HttpObjectAggregator加入pipeline
        pipeline.addLast("aggregator",new HttpObjectAggregator(512*1024));
    }
}
