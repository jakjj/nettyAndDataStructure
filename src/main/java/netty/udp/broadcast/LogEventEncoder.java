package netty.udp.broadcast;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.util.List;

public class LogEventEncoder extends MessageToMessageEncoder<LogEvent> {

    private final InetSocketAddress remoteAddress;


    public LogEventEncoder(InetSocketAddress remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, LogEvent logEvent, List<Object> list) throws Exception {
        byte[] file = logEvent.getLogfile().getBytes(CharsetUtil.UTF_8);
        byte[] msg = logEvent.getMsg().getBytes(CharsetUtil.UTF_8);

        ByteBuf buffer = channelHandlerContext.channel().alloc().buffer(file.length + msg.length + 1);
        buffer.writeBytes(file);
        buffer.writeByte(LogEvent.SEPARATOR);
        buffer.writeBytes(msg);

        //将拥有一个地址和数据的新DataGramPacket加入出战的消息列表中
        list.add(new DatagramPacket(buffer,remoteAddress));

    }
}
