package socket.proxy;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;

public class HttpProxyServer {
    private static int PORT = 8082;// 监听的端口号
    private ServerSocket serverSocket;
    private int sleep = 1000;

    public static void main(String[] args) throws IOException {
        new HttpProxyServer();
    }

    public HttpProxyServer() {
        // 创建服务
        createServer();

        // 启动提示
        System.out.println("HttpProxyServer启动，TCP端口:" + PORT);

        // 监听连接
        listenConnection();
    }

    /**
     * 创建服务
     */
    public void createServer() {
        try {
            serverSocket = new ServerSocket(PORT);
        }
        // 端口号被占用
        catch (BindException e) {
            PORT += 2;

            // 重新尝试创建
            createServer();
        }
        // 其他原因
        catch (IOException e) {
            // 休眠30秒重新尝试连接
            try {
                Thread.sleep(sleep);
                sleep *= 2;
            }
            catch (InterruptedException e1) {
                e1.printStackTrace();
            }

            // 重新尝试创建
            createServer();
        }
    }

    /**
     * 监听连接
     *
     * @throws IOException
     */
    public void listenConnection() {
        try {
            while (true) {
                new Thread(new HttpHandler(serverSocket.accept())).start();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            // 关闭服务
            closeConnection();

            // 重新创建服务
            createServer();

            // 重新监听连接
            listenConnection();
        }
    }

    /**
     * 已绑定端口
     *
     * @return
     */
    public boolean hasBind() {
        return serverSocket != null && serverSocket.isBound();
    }

    public int getPort() {
        if (!hasBind()) {
            return -1;
        }

        return serverSocket.getLocalPort();
    }

    /**
     * 关闭连接
     */
    private void closeConnection() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
