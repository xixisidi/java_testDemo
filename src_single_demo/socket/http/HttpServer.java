package socket.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 通过Socket实现Http服务器
 * 
 * @author zhangxz
 * @version $Revision: 1.0 $, $Date: 2016年2月15日 上午10:37:47 $
 */
public class HttpServer {
    private final Thread serverThread;
    private ServerSocket serverSocket;

    public static void main(String[] args) {
        new HttpServer();
    }

    public HttpServer() {
        serverThread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    serverSocket = new ServerSocket(8082);
                    while (true) {
                        Socket client = serverSocket.accept();
                        handleSocket(client);
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        serverThread.start();
    }

    /**
     * 处理端口信息
     *
     * @param client
     */
    private void handleSocket(final Socket clientSocket) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream is = null;
                OutputStream os = null;
                try {
                    is = clientSocket.getInputStream();
                    os = clientSocket.getOutputStream();
                    // 获取信息
                    System.out.println(">>>>>>>>>获取客户端信息_开始");
                    System.out.println(handleInputStream(is));
                    System.out.println("<<<<<<<<<获取客户端信息_结束");
                    // 处理信息
                    System.out.println(">>>>>>>>>发送信息给客户端_开始");
                    handleOutputStream(os, "123");
                    System.out.println("<<<<<<<<<发送信息给客户端_结束");
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
                    if (is != null) {
                        try {
                            is.close();
                        }
                        catch (Exception e1) {

                        }
                    }

                    if (os != null) {
                        try {
                            os.close();
                        }
                        catch (Exception e2) {

                        }
                    }

                    if (clientSocket != null) {
                        try {
                            clientSocket.close();
                        }
                        catch (Exception e3) {

                        }
                    }
                }
            }
        }).start();
    }

    /**
     * 处理返回的信息
     *
     * @param is
     * @return
     * @throws IOException
     */
    private String handleInputStream(InputStream is) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        byte[] buffer = new byte[2048];
        int readBytes = 0;
        // while((readBytes = is.read(buffer)) > 0){
        // System.out.println("信息片段:" + new String(buffer, 0, readBytes));
        // stringBuilder.append(new String(buffer, 0, readBytes));
        // }
        readBytes = is.read(buffer);
        stringBuilder.append(new String(buffer, 0, readBytes));

        String msg = stringBuilder.toString();
        if (msg != null) {
            int start = msg.indexOf("GET") + 3;
            int end = msg.indexOf("HTTP");
            if (start < end) {
                msg = msg.substring(start, end).trim();
            }
            System.out.println("---------------" + msg);
        }

        return stringBuilder.toString();
    }

    /**
     * 处理返回信息
     *
     * @param is
     * @throws IOException
     */
    private void handleOutputStream(OutputStream os, String msg) throws IOException {
        String separator = "\r\n";
        PrintWriter printWriter = new PrintWriter(os);
        // http_header
        printWriter.write("HTTP/1.1 200 OK" + separator);
        printWriter.write("Content-Type: text/html;charset=UTF-8" + separator);
        printWriter.write("Content-Length: " + msg.length() + separator);
        // stringBuffer.append("Connection: close").append(separator);
        // stringBuffer.append("Cache-control: no-cache").append(separator);
        // http_content
        // stringBuffer.append(msg).append(separator);
        printWriter.write(separator);
        printWriter.write(msg);
        printWriter.flush();
        // printWriter.close();
    }
}
