package netty.zbcb;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class ServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    private int count = 0;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        Channel channel = ctx.channel();

        byte[] buffer = msg.getContent();

        String msgStr = new String(buffer, CharsetUtil.UTF_8);
        System.out.println("服务器端接受数据: " + msgStr);
        System.out.println("服务器端接受数据次数: " + (++count));

        //服务端返回随机Id
        byte[] bytes = UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8);

        ctx.writeAndFlush(new MessageProtocol(bytes.length,bytes));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
