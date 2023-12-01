package netty.nettyChatRoom;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerHandler extends SimpleChannelInboundHandler<String> {

    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //handlerAdded 当连接建立后，第一个执行的方法
    //将channel加入Group
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.add(channel);
        //将客户加入聊天的消息推送给其他客户端
        channelGroup.writeAndFlush("【客户端】 " + channel.remoteAddress() + " 加入聊天 "+ sdf.format(new Date())+"\n");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

        Channel channel = ctx.channel();
        //将客户加入聊天的消息推送给其他客户端
        channelGroup.writeAndFlush("【客户端】 " + channel.remoteAddress() + " 离开\n");
    }

    //表示channel 处于活跃状态
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "上线");
    }

    //表示channel 处于非活跃状态
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "离线");
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();

        channelGroup.forEach(ch->{
            if(ch!= channel){
                ch.writeAndFlush("【客户】 " + channel.remoteAddress() + ":    " + msg);
            }else{

                ch.writeAndFlush("【我】 " + channel.remoteAddress() + ":    " + msg);
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //关闭通道
        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {


        if(evt instanceof IdleStateEvent){
            IdleStateEvent evt1 = (IdleStateEvent) evt;
            String eventStr = null;
            switch (evt1.state()){
                case READER_IDLE:
                    eventStr = "读空闲";
                    break;
                case WRITER_IDLE:
                    eventStr = "写空闲";
                    break;
                case ALL_IDLE:
                    eventStr = "全部空闲";
                    break;
                default:
                    break;
            }
            System.out.println(ctx.channel().remoteAddress() + " " + eventStr);

            //服务端可以做相应处理，如关闭channel
            ctx.channel().close();
        }

    }
}
