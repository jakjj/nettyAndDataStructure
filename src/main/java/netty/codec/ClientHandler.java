package netty.codec;

import netty.codec.proto.MyDataInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;


public class ClientHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {
        System.out.println(msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        int random = new Random().nextInt(3);
        MyDataInfo.MyMessage data = null;

        if(random == 0){
            data = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.Student)
                    .setStudent(MyDataInfo.Student.newBuilder().setId(66).setName("网页").build())
                    .build();
        }else{

            data = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.Worker)
                    .setWorker(MyDataInfo.Worker.newBuilder().setAge(25).setName("无名").build())
                    .build();
        }
        ctx.writeAndFlush(data);
    }
}
