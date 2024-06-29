package netty.webSocket.chatRoom;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static final Logger logger = LoggerFactory.getLogger(TextWebSocketFrameHandler.class);
    private final ChannelGroup group;

    public TextWebSocketFrameHandler(ChannelGroup group) {
        this.group = group;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        logger.info("TextWebSocketFrameHandler-userEventTriggered start-------");
        //第一种判断被弃用
//        if(evt == WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE){
        if(evt instanceof WebSocketServerProtocolHandler.HandshakeComplete){
            logger.info("get HandshakeComplete");
            ctx.pipeline().remove(HttpRequestHandler.class);
            group.add(ctx.channel());
            group.writeAndFlush(new TextWebSocketFrame("client: " + ctx.channel() + " 已加入"));
        }else{
            super.userEventTriggered(ctx, evt);
        }

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        //因为操作都是异步的，writeAndFlush可能在channelRead0结束后执行，所以retain()是必要的

        group.writeAndFlush(new TextWebSocketFrame(ctx.channel().remoteAddress() + ": "+ msg.text()).retain());
    }
}
