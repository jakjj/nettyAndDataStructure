package netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

public class UnpooledDemo {
    public static void main(String[] args) {
        ByteBuf byteBuf = Unpooled.copiedBuffer("Hello,WOrld", CharsetUtil.UTF_8);
        CharSequence charSequence = byteBuf.getCharSequence(0, 5, Charset.forName("utf-8"));

    }
}
