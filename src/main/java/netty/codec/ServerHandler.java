package netty.codec;

import netty.codec.proto.MyDataInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {




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
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //关闭通道
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {

        MyDataInfo.MyMessage.DataType dataType = msg.getDataType();
        if(dataType == MyDataInfo.MyMessage.DataType.Student){
            MyDataInfo.Student student = msg.getStudent();
            System.out.println(student.getName() + "   " + student.getId());
        }
        if(dataType == MyDataInfo.MyMessage.DataType.Worker){
            MyDataInfo.Worker worker = msg.getWorker();
            System.out.println(worker.getName() + "   " + worker.getAge());
        }

    }
}
