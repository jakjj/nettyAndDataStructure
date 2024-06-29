package netty.udp.monitor;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.udp.broadcast.LogEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogEventHandler extends SimpleChannelInboundHandler<LogEvent> {


    private static final Logger logger = LoggerFactory.getLogger(LogEventHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogEvent msg) throws Exception {

        StringBuilder builder = new StringBuilder();
        builder.append("[");
        builder.append(msg.getReceived());
        builder.append("  ");
        builder.append(msg.getSource());
        builder.append(" : ");
        builder.append(msg.getLogfile());
        builder.append(" -- ");
        builder.append(msg.getMsg());
        builder.append("]");

        logger.info(builder.toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        cause.printStackTrace();
        logger.error(cause.getMessage());
        ctx.close();
    }
}
