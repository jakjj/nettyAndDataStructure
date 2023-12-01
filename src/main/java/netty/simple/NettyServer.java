package netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {

    public static void main(String[] args) {

        //bossGroup 只处理accept，workGroup处理客户端业务
        //两个线程组 都是无限循环
        //bossGroup和workGroup 罕有的子线程（NioEventLoop）个数，默认为cpu核数*2
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        try {
            //绑定两个组
            //设置服务器channel实现
            //返回线程队列得到的连接数
            //保持活动的个数
            //创建一个通道测试对象
            serverBootstrap.group(bossGroup,workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,128)
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        //设置通道处理器
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyServerHandle());
                        }
                    });
            System.out.println("。。。。。服务器 is ready");

            //绑定一个通道并且同步，生成一个future对象
            //启动服务器(并绑定端口)
            ChannelFuture cf = serverBootstrap.bind(6666).sync();

            //对关闭通道进行监听
            cf.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //优雅的关闭
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }
}
