package netty.dubboRPC.provider;

import netty.dubboRPC.publicinterface.HelloService;

public class HelloServerImpl implements HelloService {
    @Override
    public String hello(String msg) {
        String result = "Hello " + msg;
        System.out.println("HelloServerImpl被调用： " + msg);
        return result;
    }
}
