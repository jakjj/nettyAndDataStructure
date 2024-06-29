package netty.udp.monitor;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.CharsetUtil;
import netty.udp.broadcast.LogEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class LogEventDecoder extends MessageToMessageDecoder<DatagramPacket> {

    private static final Logger logger = LoggerFactory.getLogger(LogEventDecoder.class);
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket, List<Object> list) throws Exception {
        ByteBuf content = datagramPacket.content();

        int index = content.indexOf(0, content.readableBytes(), LogEvent.SEPARATOR);
        String filePath = content.slice(0, index).toString(CharsetUtil.UTF_8);
        //原文中使用 content.readableBytes()，但测试中发现 content.readableBytes()-index-1未改变，
        // slic(offset,length) 导致indexOutOfBound
        // 改为content.readableBytes()-index-1
        String msg = content.slice(index+1,content.readableBytes()-index-1).toString(CharsetUtil.UTF_8);
        LogEvent logEvent = new LogEvent(datagramPacket.sender(), filePath, msg, System.currentTimeMillis());

        list.add(logEvent);
    }
}
