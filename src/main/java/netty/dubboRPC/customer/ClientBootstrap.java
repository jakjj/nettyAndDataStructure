package netty.dubboRPC.customer;

import netty.dubboRPC.publicinterface.HelloService;

public class ClientBootstrap {

    public static final String providerName = "hello#netty#";

    public static void main(String[] args) {
        NettyClient nettyClient = new NettyClient();
        HelloService bean = (HelloService)nettyClient.getBean(HelloService.class, providerName);
        String hello = bean.hello("client send msg 4 test");
        System.out.println(hello);
    }

}
