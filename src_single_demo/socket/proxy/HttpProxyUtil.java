package socket.proxy;

import java.util.Properties;

public class HttpProxyUtil {
    private static HttpProxyServer httpProxyServer;

    /**
     * 创建服务
     *
     * @return
     */
    public static HttpProxyServer createServer() {
        if (httpProxyServer == null) {
            httpProxyServer = new HttpProxyServer();
        }
        return httpProxyServer;
    }

    /**
     * 创建代理
     */
    public synchronized static void createProxy() {
        // 未初始化
        if (httpProxyServer != null && httpProxyServer.hasBind()) {
            return;
        }

        // 设置
        Properties prop = System.getProperties();
        prop.setProperty("http.proxyHost", "localhost");
        prop.setProperty("http.proxyPort", httpProxyServer.getPort() + "");
    }
}
