package netty.dubboRPC.provider;

public class ServerBootstrap {
    public static void main(String[] args) {
        NettyServer.start("127.0.0.1", 7000);
    }
}
