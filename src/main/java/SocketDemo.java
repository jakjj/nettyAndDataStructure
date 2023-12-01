
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.*;

public class SocketDemo {

    private static final RejectedExecutionHandler defaultHandler =
            new ThreadPoolExecutor.AbortPolicy();
    public static void main(String[] args) {
        ExecutorService newCachedThreadPool = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>(),Executors.defaultThreadFactory(),defaultHandler);

        try {
            ServerSocket serverSocket = new ServerSocket(6666);

            while(true){
                final Socket socket = serverSocket.accept();
                System.out.println("连接到一个客户端");

                newCachedThreadPool.execute(new Runnable(){
                    @Override
                    public void run() {
                        handle(socket);
                    }
                });
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void handle(Socket socket){

        try {
            System.out.println("线程信息：id"+ Thread.currentThread().getId() +" name:" + Thread.currentThread().getName());
            byte[] bytes = new byte[1024];
            InputStream in = socket.getInputStream();
            while(true){
                int read = in.read(bytes);
                if(read != -1){

                    System.out.println(new String(bytes,0,read));
                }else{
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
