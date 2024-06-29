import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import org.junit.Test;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class NettyTest {



    @Test
    public void test1(){
        int capacity = 6;
        byte[] bb = new byte[6];
        ByteBuffer allocate = ByteBuffer.allocate(capacity);
        allocate.put((byte)20);
        allocate.put((byte)30);
        allocate.put((byte)40);
        allocate.put((byte)50);
        allocate.put((byte)60);

        allocate.rewind();

        ByteBuffer duplicate = allocate.duplicate();



        allocate.put((byte) 22);
        allocate.put((byte) 33);
        duplicate.put((byte) 44);
        duplicate.put((byte) 55);

        ByteBuf byteBuf = Unpooled.copiedBuffer(allocate);

        WebSocketFrame retain = new WebSocketFrame(byteBuf) {
            @Override
            public WebSocketFrame replace(ByteBuf content) {
                return new BinaryWebSocketFrame(this.isFinalFragment(), this.rsv(), content);
            }
        }.duplicate();

        WebSocketFrame retain1 = retain.retain();

        System.out.println(Arrays.toString(allocate.array()));
        System.out.println(Arrays.toString(duplicate.array()));
    }



}

