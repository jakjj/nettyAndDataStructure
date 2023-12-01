package netty.zbcb;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

public class ClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    private int count = 0;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client-channelActive");
        for(int i = 0;i<10;i++){
            String message = "hello server, " + i;
            byte[] bytes = message.getBytes(Charset.forName("utf-8"));
            MessageProtocol messageProtocol = new MessageProtocol(bytes.length, bytes);

            ctx.writeAndFlush(messageProtocol);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        Channel channel = ctx.channel();


        byte[] content = msg.getContent();

        String msgStr = new String(content, CharsetUtil.UTF_8);

        System.out.println("客户端接受消息： " + msgStr);
        System.out.println("客户端接受消息次数： " + (++count));

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("client-error: " +cause.getMessage());
        ctx.close();
    }
}
