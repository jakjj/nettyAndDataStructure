package netty.heartBeat;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketAddress;

public class HeartBeatInboundHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(HeartBeatInboundHandler.class);

    private static final String triggerMsg = "HEAT_BEAT";



    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if(evt instanceof IdleStateEvent){
            SocketAddress socketAddress = ctx.channel().remoteAddress();

            logger.info("触发空闲检测器-----{}",socketAddress);


            ctx.writeAndFlush(triggerMsg)
                    .addListener(ChannelFutureListener.CLOSE_ON_FAILURE);


        }else{
            super.userEventTriggered(ctx, evt);
        }


    }
}
