package netty.ssl;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpServerCodec;

public class HttpZipChannelInitializer extends ChannelInitializer<Channel> {

    private final boolean isClient;

    public HttpZipChannelInitializer(boolean isClient) {
        this.isClient = isClient;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {

        ChannelPipeline pipeline = channel.pipeline();

        if(isClient){
            pipeline.addLast("httpClientCodec",new HttpClientCodec());
            //客户端处理来自服务端的压缩内容
            pipeline.addLast("decomprocess", new HttpContentDecompressor());
        }else{
            pipeline.addLast("httpServerCodec",new HttpServerCodec());
            //服务端压缩内容
            pipeline.addLast("comprocess",new HttpContentCompressor());
        }

    }
}
