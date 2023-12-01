package netty.dubboRPC.provider;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyServerHandle extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        if(msg == null){
            return ;
        }
        String stuff = "hello#netty#";

        System.out.println("服务端接收消息：" + msg);
        String msgStr = msg.toString();
        if(msgStr.startsWith(stuff)){
            HelloServerImpl helloServer = new HelloServerImpl();
            String hello = helloServer.hello(msgStr.substring(msgStr.lastIndexOf("#") + 1));
            ctx.writeAndFlush(hello);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
