package socket.nginx;

import java.io.UnsupportedEncodingException;

public class HttpParser {
    // 换行符
    private static final String separator = "\r\n";
    // http结束符
    private static final byte[] End_Tag = new byte[] { 0x0d, 0x0a, 0x30, 0x0d, 0x0a, 0x0d, 0x0a };

    private final byte[] bytes;
    private String dataStr;
    private final int c;

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
        httpHeader.type = type;
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
                        break;
                    }
                    System.out.println(header);
                    httpHeader.method = header.substring(0, header.indexOf(" ")).trim();
                    httpHeader.url = header.substring(httpHeader.method.length() + 1, header.lastIndexOf(" H"));
                }
                else if (header.indexOf("Connection") == 0) {
                    // Connection: Keep-Alive
                    httpHeader.connection = header.substring(12);
                }
                else if (header.indexOf("Host") == 0) {
                    // Host: m.cs.netstudy5.tst
                    System.out.println(header);
                    httpHeader.host = header.substring(6).trim();
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

    /**
     * http文件加载完成
     *
     * @return
     */
    public boolean isHttpEnd() {
        if (bytes.length > c) {
            return true;
        }

        for (int i = 0; i < End_Tag.length; i++) {
            if (bytes[c - i - 1] == End_Tag[End_Tag.length - i - 1]) {
                // 已结束
                if ((i + 1) == End_Tag.length) {
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
    private String getData() throws UnsupportedEncodingException {
        if (dataStr == null) {
            dataStr = new String(bytes, 0, c, "utf-8");
        }

        return dataStr;
    }

    /**
     * 身份校验
     *
     * @return
     */
    public boolean isServerVerify() {
        try {
            // return getData().indexOf("netstudy: 1") > 0;
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public static class HttpHeader {
        public static final int CLIENT = 0;
        public static final int SERVER = 1;

        public int type;// 0 是请求头，1 是返回头
        // 请求头
        public String method;// GET
        public String url; // http://m.cs.netstudy5.tst/course/courseCenter.htm
        public String host; // m.cs.netstudy5.tst

        // 返回头
        public int code;// 200

        // 连接方式
        public String connection; // Keep-Alive or close

    }

    // 重定向
    // HTTP/1.1 302 Found
    // Server: nginx
    // Date: Wed, 06 Jul 2016 10:35:08 GMT
    // Connection: keep-alive
    // Cache-Control: no-cache, must-revalidate
    // Expires: Thu, 01 Jan 1970 00:00:00 GMT
    // Pragma: no-cache
    // Set-Cookie: 915cde8154bedcc959214bdd5ea97d61=9; Domain=yflb.kehou.com; Expires=Sat, 02-Jun-2074 18:11:40 GMT;
    // Path=/
    // Location: http://m.yflb.kehou.com/mobileConfig.htm?aid=9
    // Content-Length: 0
}
