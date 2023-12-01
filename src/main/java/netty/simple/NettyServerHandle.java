package netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;


/*
    自定义Handle 需要继承 Netty的一个HandleAdapter
 */
public class NettyServerHandle extends ChannelInboundHandlerAdapter {

    //读取数据实现
    //ChannelHandlerContext 上下文对象 含有管道pipeline，通道channel，地址
    //msg 客户端发来的数据
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server ctx:" + ctx);
        ByteBuf buf = (ByteBuf) msg;

        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000*10);
                    ctx.writeAndFlush(Unpooled.copiedBuffer("hello, client: this is channelRead 4 Server", CharsetUtil.UTF_8));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000*10);
                    ctx.writeAndFlush(Unpooled.copiedBuffer("hello, client: this is schedule channelRead 4 Server", CharsetUtil.UTF_8));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },5, TimeUnit.SECONDS);
        System.out.println("客户端发送消息：" +  buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址：" + ctx.channel().remoteAddress());
    }


    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        super.channelWritabilityChanged(ctx);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端",CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
