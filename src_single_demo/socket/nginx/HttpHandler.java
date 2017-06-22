package socket.nginx;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;

import socket.nginx.HttpParser.HttpHeader;

public class HttpHandler implements Runnable {
    // 客户端
    private Socket clientSocket;
    private InputStream clientInputStream;
    private OutputStream clientOutputStream;
    private final byte[] clientBytes;
    // 服务器端
    private Socket serverSocket;
    private InputStream serverInputStream;
    private OutputStream serverOutputStream;
    private final byte[] serverBytes;

    private boolean isNextRequest = true;// 正在加载httpHeader
    private final Map<String, Integer> portMap;

    public HttpHandler(Socket client, Map<String, Integer> portMap) {
        this.clientSocket = client;
        this.portMap = portMap;
        clientBytes = new byte[1024];
        serverBytes = new byte[1024];
    }

    @Override
    public void run() {
        // 转发给服务端
        sendToServer();

        // 转发给客户端
        sendToClient();

        // 关闭连接
        closeConnection();
    }

    /**
     * 创建连接
     *
     * @param c
     */
    private void createConnection(int c) {
        try {
            HttpHeader header = new HttpParser(clientBytes, c).parseHttpHeader(HttpHeader.CLIENT);
            Integer port = portMap.get(header.host);
            if (header.host != null && port != null) {
                serverSocket = new Socket(header.host, port);
                serverOutputStream = serverSocket.getOutputStream();
                serverInputStream = serverSocket.getInputStream();
            }
            else {
                System.out.println("应用" + header.host + "不存在！！！");
            }
        }
        catch (IOException e) {
            // e.printStackTrace();
            closeConnection();
        }
        catch (Exception e) {
            e.printStackTrace();
            closeConnection();
        }
    }

    /**
     * 转发给服务端
     */
    private void sendToServer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    clientInputStream = clientSocket.getInputStream();
                    clientOutputStream = clientSocket.getOutputStream();

                    int c = 0;
                    while ((c = clientInputStream.read(clientBytes, 0, clientBytes.length)) > 0) {
                        // 创建连接
                        if (serverSocket == null) {
                            createConnection(c);
                        }

                        // 发送信息
                        if (serverOutputStream != null) {
                            serverOutputStream.write(clientBytes, 0, c);
                        }
                    }
                }
                catch (IOException e) {
                    // e.printStackTrace();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

                closeConnection();
            }
        }).start();
    }

    /**
     * 转发给客户端
     */
    private void sendToClient() {
        try {
            // 等待基本信息初始化
            while (serverInputStream == null) {
                Thread.sleep(5000);
            }

            // 接收并转发数据
            int c = 0;
            HttpParser httpParser = null;
            while ((c = serverInputStream.read(serverBytes, 0, serverBytes.length)) > 0) {
                httpParser = new HttpParser(serverBytes, c);

                // 服务器校验
                if (isNextRequest && !httpParser.isServerVerify()) {
                    closeConnection();
                    return;
                }

                // 本次http访问结束
                if (httpParser.isHttpEnd()) {
                    isNextRequest = true;
                }

                // 发送信息
                clientOutputStream.write(serverBytes, 0, c);

                if (serverSocket == null) {
                    return;
                }
            }
        }
        catch (IOException e) {
            // e.printStackTrace();
        }
        catch (InterruptedException e) {
            // e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        closeConnection();
    }

    /**
     * 关闭连接
     */
    private void closeConnection() {
        if (clientInputStream != null) {
            try {
                clientInputStream.close();
                clientInputStream = null;
            }
            catch (IOException e) {
                e.printStackTrace();
                clientInputStream = null;
            }
        }
        if (clientOutputStream != null) {
            try {
                clientOutputStream.close();
                clientOutputStream = null;
            }
            catch (IOException e) {
                e.printStackTrace();
                clientOutputStream = null;
            }
        }
        if (clientSocket != null) {
            try {
                clientSocket.close();
                clientSocket = null;
            }
            catch (IOException e) {
                e.printStackTrace();
                clientSocket = null;
            }
        }

        if (serverInputStream != null) {
            try {
                serverInputStream.close();
                serverInputStream = null;
            }
            catch (IOException e) {
                e.printStackTrace();
                serverInputStream = null;
            }
        }
        if (serverOutputStream != null) {
            try {
                serverOutputStream.close();
                serverOutputStream = null;
            }
            catch (IOException e) {
                e.printStackTrace();
                serverOutputStream = null;
            }
        }
        if (serverSocket != null) {
            try {
                serverSocket.close();
                serverSocket = null;
            }
            catch (IOException e) {
                e.printStackTrace();
                serverSocket = null;
            }
        }
    }
}
