package netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

//HttpObject 客户端和服务端通讯数据被包装成HttpObject
public class HttpServerChannelAdapter extends SimpleChannelInboundHandler<HttpObject> {

    //读取客户端数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        //判断msg是否为 netty.http request请求
        if(msg instanceof HttpRequest){
            System.out.println("msg类型；" + msg.getClass());
            System.out.println("客户端地址：" + ctx.channel().remoteAddress());

            //回复消息 HTTP
            ByteBuf buf = Unpooled.copiedBuffer("hello,this is server", CharsetUtil.UTF_8);

            HttpRequest request = (HttpRequest) msg;
            URI uri = new URI(request.uri());
            if("/favicon.ico".equals(uri.getPath())){
                System.out.println("favicon.ico 不予响应");
                return;
            }

            //构造一个Http响应 即 HttpResponse
            DefaultFullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);
            httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
            httpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH,buf.readableBytes());

            //返回响应
            ctx.writeAndFlush(httpResponse);
        }
    }
}
