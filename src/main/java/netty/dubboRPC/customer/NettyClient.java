package netty.dubboRPC.customer;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.springframework.cglib.proxy.Proxy;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NettyClient {


    private ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private static ClientHandler handler;

    public Object getBean(final Class<?> serverClass,final String providerName){
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),new Class[]{serverClass},(prox, method, args)->{
            if(handler == null){
                initClient();
            }
            handler.setParams(providerName + args[0]);

            return executorService.submit(handler).get();
        });

    }

    private void initClient(){

        handler = new ClientHandler();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("myDecoder", new StringDecoder());
                            pipeline.addLast("myEncoder", new StringEncoder());
                            pipeline.addLast(handler);
                        }
                    });
            ChannelFuture future = bootstrap.connect("127.0.0.1", 7000).sync();
            System.out.println("clients is ready");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}
