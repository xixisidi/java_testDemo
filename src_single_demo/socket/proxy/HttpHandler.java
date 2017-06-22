package socket.proxy;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public HttpHandler(Socket client) {
        this.clientSocket = client;
        clientBytes = new byte[4096];
        serverBytes = new byte[4096];
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
            if (header.host != null) {
                serverSocket = new Socket(header.domain, header.port);
                serverOutputStream = serverSocket.getOutputStream();
                serverInputStream = serverSocket.getInputStream();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            // new String(clientBytes, 0, c, "utf-8")
            closeConnection();
        }
        catch (Exception e) {
            e.printStackTrace();
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
                        // 发送信息
                        System.out.println(new String(clientBytes, 0, c, "utf-8"));

                        // 创建连接
                        if (serverSocket == null) {
                            createConnection(c);
                        }

                        if (serverOutputStream != null) {
                            serverOutputStream.write(clientBytes, 0, c);
                        }
                    }
                }
                catch (IOException e) {
                    // e.printStackTrace();
                }

                closeConnection();
            }
        }).start();
    }

    /**
     * 转发给客户端
     */
    private void sendToClient() {
        HttpData httpData = null;
        HttpChunck httpChunck = null;
        boolean isGzip = false;
        boolean isChunck = false;
        int preEndTestIndex = -1;// chunk上一次验证地址（接着地址继续验证）
        long date = new Date().getTime();
        boolean isEnd = false;

        System.out.println("==============================================连接开始_" + date);
        try {
            // 等待基本信息初始化
            while (serverInputStream == null) {
                Thread.sleep(5000);
            }

            // 接收并转发数据
            int httpLength = 0;
            while ((httpLength = sendToClient(serverBytes, 0, serverBytes.length, clientOutputStream)) > 0) {
                // 打印HTTP
                try {
                    HttpHeader httpHeader = new HttpHeader();
                    int startIndex = 0;
                    isEnd = false;
                    isGzip = false;
                    isChunck = false;
                    String header = httpHeader.getServerHeader(serverBytes, httpLength);

                    if (httpHeader.isHeader()) {
                        httpData = new HttpData();
                        httpData.setHeader(header);
                        httpData.setImage(httpHeader.isImage());

                        if (httpHeader.contentEncoding != null) {
                            isGzip = httpHeader.contentEncoding.equals("gzip");
                        }
                        if (httpHeader.transferEncoding != null) {
                            isChunck = httpHeader.transferEncoding.equals("chunked");
                        }
                        startIndex = httpHeader.getHeaderEndIndex() + 1;
                    }

                    // http通过chunck传送
                    if (isChunck) {// 2是header的换行符
                        httpChunck = new HttpChunck(serverBytes, startIndex + 1, httpLength, preEndTestIndex);
                        // 只传过来文件头
                        if (httpChunck.getChunckEndIndex() == 0 && httpChunck.getChunckStartIndex() == 0) {
                            // 304说明后面没有数据了
                            if (httpHeader.code == 304) {
                                continue;
                            }
                            // 说明正文在下一个文件流中 200 404 500等
                            if (httpHeader.code == 200 || httpHeader.code != 304) {
                                httpLength = sendToClient(serverBytes, 0, serverBytes.length, clientOutputStream);
                                // -2 ：因为起始不是\r\n
                                httpChunck = new HttpChunck(serverBytes, -2, httpLength, preEndTestIndex);
                            }
                        }

                        int _startIndex = -1;
                        int _contentLength = -1;
                        long _remainContentLength = httpChunck.getChunckEndIndex() - httpChunck.getChunckStartIndex()
                                + 1;
                        boolean isHeader = httpHeader.isHeader();
                        List<HttpChunck> httpChuncks = new ArrayList<HttpChunck>();
                        while (_remainContentLength > 0) {
                            httpChuncks.add(httpChunck);
                            // 起点
                            if (isHeader) {
                                _startIndex = httpChunck.getChunckStartIndex();
                            }
                            else {
                                _startIndex = 0;
                            }

                            // 终点
                            if (_startIndex + _remainContentLength > httpLength) {
                                _contentLength = httpLength - _startIndex;
                            }
                            else {
                                _contentLength = (int) _remainContentLength;
                            }

                            // 保存文件
                            httpData.saveFile(serverBytes, _startIndex, _contentLength);
                            CourseCenterTest.saveFile(serverBytes, _startIndex, _contentLength);
                            // new String(serverBytes, 0, httpLength, "utf-8");

                            // 剩余长度
                            _remainContentLength -= _contentLength;
                            // 该chunck未加载完，读取流继续解析
                            if (_remainContentLength > 0) {
                                httpLength = sendToClient(serverBytes, 0, serverBytes.length, clientOutputStream);
                                httpChunck.remainContentLength(serverBytes, httpLength, _remainContentLength);
                                if (httpLength < 0) {
                                    break;
                                }
                                isHeader = false;
                            }
                            // 该chunck加载完，但是后面还有数据
                            else {
                                // 数据没有结束
                                if (!httpChunck.isEnd()) {
                                    // 验证下一个流是不是文件结束符
                                    long v = httpLength - (httpChunck.getChunckStartIndex() + _contentLength);
                                    if (v < HttpChunck.HTTP_BODY_END.length && httpChunck.getEndTestIndex() >= 0) {
                                        httpLength = sendToClient(serverBytes, 0, serverBytes.length,
                                                clientOutputStream);
                                        preEndTestIndex = httpChunck.getEndTestIndex();
                                        httpChunck = new HttpChunck(serverBytes, 0, httpLength, preEndTestIndex);
                                        isHeader = false;
                                        isEnd = httpChunck.isEnd();
                                        if (isEnd) {
                                            break;
                                        }
                                    }
                                    // 可能是下一个chunk
                                    else {
                                        // new String(serverBytes, 0, httpLength, "utf-8")
                                        httpChunck = new HttpChunck(serverBytes,
                                                httpChunck.getChunckStartIndex() + _contentLength, httpLength,
                                                preEndTestIndex);
                                        _remainContentLength = httpChunck.getChunckEndIndex()
                                                - httpChunck.getChunckStartIndex() + 1;
                                        isHeader = true;
                                    }
                                }
                                else {
                                    isHeader = false;
                                }
                            }
                        }

                        isEnd = httpChunck.isEnd();
                        if (!isEnd) {
                            preEndTestIndex = httpChunck.getEndTestIndex();
                        }
                    }
                    // http通过content-length传送
                    else {
                        int _startIndex = -1;
                        int _contentLength = -1;
                        long _remainContentLength = httpHeader.contentLength;
                        boolean isHeader = httpHeader.isHeader();
                        while (_remainContentLength > 0) {
                            // 起点
                            if (isHeader) {
                                _startIndex = httpHeader.getHeaderEndIndex() + 5;
                            }
                            else {
                                _startIndex = 0;
                            }

                            // 终点
                            if (_startIndex + _remainContentLength > httpLength) {
                                _contentLength = httpLength - _startIndex;
                            }
                            else {
                                _contentLength = (int) _remainContentLength;
                            }

                            // 保存文件
                            httpData.saveFile(serverBytes, _startIndex, _contentLength);

                            // new String(serverBytes, 0,httpLength,"utf-8")

                            // 剩余长度
                            _remainContentLength -= _contentLength;
                            // 加载数据
                            if (_remainContentLength > 0) {
                                httpLength = sendToClient(serverBytes, 0, serverBytes.length, clientOutputStream);
                                if (httpLength < 0) {
                                    break;
                                }
                            }
                            else {
                                isEnd = true;
                            }
                            isHeader = false;
                        }
                    }

                    if (isEnd) {
                        httpData.printHttp(isGzip);
                    }
                    isEnd = false;
                }
                catch (SocketException e) {
                    e.printStackTrace();
                    httpData.printHttp(isGzip);
                    System.out.println("==============================================主动关闭_" + date);
                    break;
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                catch (Exception e) {
                    e.printStackTrace();

                    // 报错后停止监听，但是数据继续传输
                    try {
                        while ((httpLength = sendToClient(serverBytes, 0, serverBytes.length,
                                clientOutputStream)) > 0) {

                        }
                    }
                    catch (SocketException e2) {
                        System.out.println("==============================================主动关闭_" + date);
                        break;
                    }
                    catch (Exception e2) {
                        e.printStackTrace();
                    }

                }
            }
        }
        catch (SocketException e) {
            System.out.println("==============================================主动关闭_" + date);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("==============================================连接结束_" + date);

        closeConnection();
    }

    private int sendToClient(byte[] serverBytes, int off, int len, OutputStream clientOutputStream) throws IOException {
        int httpLength = serverInputStream.read(serverBytes, off, len);
        if (httpLength > 0) {
            clientOutputStream.write(serverBytes, 0, httpLength);
        }
        if (serverSocket == null) {
            throw new SocketException("serverSocket为空关闭连接");
        }
        return httpLength;
    }

    /**
     * 关闭连接
     */
    private void closeConnection() {
        if (serverSocket == null) {
            return;
        }

        try {
            serverSocket.close();
            serverSocket = null;
        }
        catch (IOException e) {
            e.printStackTrace();
            serverSocket = null;
        }

        try {
            clientSocket.close();
            clientSocket = null;
        }
        catch (IOException e) {
            e.printStackTrace();
            clientSocket = null;
        }
    }
}
