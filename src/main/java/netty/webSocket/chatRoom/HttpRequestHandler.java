package netty.webSocket.chatRoom;

import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedNioFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.net.URL;

/***
 * 聊天室第一部分，用于处理Http请求
 */
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequestHandler.class);

    private final String wsUri;
    private static final File INDEX;

    static{
        logger.info("HttpRequestHandler static code area start-------");
        URL location = HttpRequestHandler.class.getProtectionDomain()
                .getCodeSource().getLocation();
        logger.info("location:{}",location);
        try {
            String path = location.toURI() + "index.html";
            path = path.contains("file:")?path.substring(5):path;
            INDEX = new File(path);
        } catch (URISyntaxException e) {
            throw new IllegalStateException("not found index.html",e);
        }
    }

    public HttpRequestHandler(String wsUri) {
        this.wsUri = wsUri;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {

        logger.info("request.uri():{}",request.uri());
        if(wsUri.equalsIgnoreCase(request.uri())){
            //请求协议升级为WebSocket，增加引用计数，交给下一个Handler
            ctx.fireChannelRead(request.retain());

        }else{
            //处理100Continue请求以符合http1.1规范
            if(HttpUtil.is100ContinueExpected(request)){
                send100Continue(ctx);
            }

            //获取文件
            RandomAccessFile file = new RandomAccessFile(INDEX,"r");
            HttpResponse response = new DefaultHttpResponse(request.protocolVersion(), HttpResponseStatus.OK);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/html;charset='utf-8'");
            boolean keepAlive = HttpUtil.isKeepAlive(request);
            //判断是否keep-alive
            if(keepAlive){
                response.headers().set(HttpHeaderNames.CONTENT_LENGTH,file.length());
                response.headers().set(HttpHeaderNames.CONNECTION,HttpHeaderValues.KEEP_ALIVE);
            }

            ctx.write(response);
            //判断是否使用ssl，没有则可以放到DefaultFileRegion中，使用零拷贝增加效率
            if(ctx.pipeline().get(SslHandler.class) == null){
                logger.info("no ssl-----");
                ctx.write(new DefaultFileRegion(file.getChannel(),0,file.length()));

            }else{
                logger.info("ssl-----");
                ctx.write(new ChunkedNioFile(file.getChannel()));
            }

            //之前都是write，最后通过writeAndFlush发送LastHttpContent来标记响应结束
            ChannelFuture future = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
            if(!keepAlive){
                future.addListener(ChannelFutureListener.CLOSE);
            }

        }


    }


    public static void send100Continue(ChannelHandlerContext ctx){
        ctx.writeAndFlush(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.CONTINUE));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error(cause.getMessage());
        ctx.close();
    }
}
