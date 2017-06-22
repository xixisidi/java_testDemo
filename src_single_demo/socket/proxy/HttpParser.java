package socket.proxy;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ThreadLocalRandom;

public class HttpParser {
    // 换行符
    private static final String separator = "\r\n";
    // http结束符
    private static final byte[] HTTP_BODY_END = new byte[] { 0x0d, 0x0a, 0x30, 0x0d, 0x0a, 0x0d, 0x0a };

    private byte[] bytes;
    private String dataStr;
    private int c;

    public HttpParser() {
    }

    public HttpParser(byte[] bytes, int c) throws UnsupportedEncodingException {
        this.bytes = bytes;
        this.c = c;
    }

    /**
     * 解析文件头
     *
     * @param type
     *            0 是请求头，1 是返回头
     * @return
     * @throws UnsupportedEncodingException
     */
    public HttpHeader parseHttpHeader(int type) throws UnsupportedEncodingException {
        HttpHeader httpHeader = new HttpHeader();
        String[] headers = getData().split(separator);
        String header = null;
        for (int i = 0; i < headers.length; i++) {
            header = headers[i];
            // 客户端请求头
            if (type == HttpHeader.CLIENT) {
                if (i == 0) {
                    // GET http://m.cs.netstudy5.tst/course/courseCenter.htm HTTP/1.1
                    // CONNECT open.account.xiaomi.com:443 HTTP/1.1 --http代理
                    if (header.indexOf("CONNECT") >= 0) {
                    	httpHeader.method = "CONNECT";
                    	httpHeader.url = header.substring(8,header.indexOf(" H")).trim();
                    	int port = 80;
                        String[] array = httpHeader.url.split(":");
                        if(array.length > 1){
                        	port = Integer.parseInt(array[1]);
                        }
                        httpHeader.domain = array[0];
                        httpHeader.host = array[0];
                        httpHeader.port = port;
                        break;
                    }
                    try {
                    	httpHeader.method = header.substring(0, header.indexOf("http")).trim();
                    	httpHeader.url = header.substring(httpHeader.method.length() + 1, header.indexOf(" H"));
					} catch (Exception e) {
						System.out.println(getData());
						e.printStackTrace();
						throw new RuntimeException(e);
					}
                }
                else if (header.indexOf("Connection") == 0) {
                    // Connection: Keep-Alive
                    httpHeader.connection = header.substring(12);
                }
                else if (header.indexOf("Host") == 0) {
                    // Host: m.cs.netstudy5.tst
                    httpHeader.host = header.substring(6);
                    int port = 80;
                    String[] array = httpHeader.host.split(":");
                    if(array.length > 1){
                    	port = Integer.parseInt(array[1]);
                    }
                    httpHeader.domain = array[0];
                    httpHeader.port = port;
                }
                if (httpHeader.host != null && httpHeader.connection != null) {
                    break;
                }
            }
            // 服务器端返回头
            else if (type == HttpHeader.SERVER) {
                if (i == 0) {
                    // HTTP/1.1 200 OK
                    httpHeader.method = header.substring(0, header.indexOf(" "));
                    httpHeader.url = header.substring(httpHeader.method.length() + 1, header.indexOf(" H"));
                }
                else if (header.indexOf("Connection") == 0) {
                    // Connection: Keep-Alive
                    httpHeader.connection = header.substring(12);
                    break;
                }
            }
        }
        return httpHeader;
    }

    public int getHttpEnd(byte[] datas) {
        // 空
        if (bytes.length > c) {
            return -1;
        }

        // 获取结束符
        int index = -1;
        a: for (int i = 0; i < datas.length; i++) {
            b: for (int j = 0; j < HTTP_BODY_END.length; j++) {
                if (datas[i + j] == HTTP_BODY_END[j]) {
                    if (j == HTTP_BODY_END.length - 1) {
                        index = i - 1;
                        break a;
                    }
                    continue;
                }
                else {
                    break b;
                }
            }
        }

        // 去除正文尾部的换行
        if (index > 0) {
            if (datas[index - 1] == 0x0a && datas[index] == 0x0d) {
                index -= 2;
            }
            if (datas[index - 1] == 0x0a && datas[index] == 0x0d) {
                index -= 2;
            }
            if (datas[index - 1] == 0x0a && datas[index] == 0x0d) {
                index -= 2;
            }
        }

        return index;
    }

    /**
     * http文件加载完成
     *
     * @return
     */
    public boolean isHttpEnd() {
        if (bytes.length > c) {
            return true;
        }

        for (int i = 0; i < HTTP_BODY_END.length; i++) {
            if (bytes[c - i - 1] == HTTP_BODY_END[HTTP_BODY_END.length - i - 1]) {
                // 已结束
                if ((i + 1) == HTTP_BODY_END.length) {
                    return true;
                }
            }
            // 未结束
            else {
                break;
            }
        }
        return false;
    }

    /**
     * 获取字符串
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    public String getData() throws UnsupportedEncodingException {
        if (dataStr == null) {
            dataStr = new String(bytes, 0, c, "utf-8");
        }

        return dataStr;
    }

    public void initData(byte[] bytes, int c) {
        this.bytes = bytes;
        this.c = c;
    }

}
