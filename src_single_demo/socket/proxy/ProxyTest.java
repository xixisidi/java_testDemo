package socket.proxy;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class ProxyTest {
    public static void main(String[] args) throws IOException {
        System.setProperty("http.proxyHost", "127.0.0.1");
        // System.setProperty("http.proxyHost", "127.0.0.1");
        System.setProperty("http.proxyPort", "8082");
        System.setProperty("https.proxyHost", "127.0.0.1");
        System.setProperty("https.proxyPort", "8082");

        URL url = new URL("http://m.netstudy5.dev/mobileConfig.htm?aid=4");
        // URL url = new URL("http://m.yflb.kehou.com/login.htm");
        // URL url = new URL("http://www.baidu.com");
        URLConnection conn = url.openConnection();
        // Accept-Encoding: gzip, deflate
        // conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
        // conn.setRequestProperty("netstudy", "1");
        Scanner scan = new Scanner(conn.getInputStream());
        // 读取远程主机的内容
        while (scan.hasNextLine()) {
            System.out.println(scan.nextLine());
        }
        scan.close();
    }
}
