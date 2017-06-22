package socket.nginx;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class TomcatUtil {
    // public static void main(String[] args) throws Exception {
    // //startTomcat("F:\\startup.bat","\"F:\\server.xml\"","\"D:\\Program Files\\Java\\apache-tomcat-7.0.59\"");
    // loadServerInfos("F:\\nginx\\", "\"D:\\Program Files\\Java\\apache-tomcat-7.0.59\"");
    // }

    public static Map<String, Integer> loadServerInfos(String serverPath, String tomcatHome) throws Exception {
        Map<String, Integer> map = new HashMap<String, Integer>();
        File[] files = new File(serverPath).listFiles();
        for (File file : files) {
            if (file.getName().endsWith("server.xml")) {
                // 加载端口
                if (!TomcatUtil.loadServerInfo(file, map)) {
                    startTomcat(serverPath + "tomcat.bat", serverPath + file.getName(), tomcatHome);// 启动tomcat
                }
            }
        }
        return map;
    }

    private static void startTomcat(String startupBatPath, String serverXmlPath, String tomcatHomePath)
            throws Exception {
        String disc = tomcatHomePath.substring(1, tomcatHomePath.indexOf(":") + 1);
        String[] cmd = new String[] { "cmd.exe", "/c", startupBatPath, disc, tomcatHomePath, "-config", serverXmlPath };
        CommandUtil.execCommand(cmd);
    }

    /**
     *
     * @param file
     *            serverxml文件
     * @param domainPort
     *            端口
     * @return 是否端口被占用
     * @throws DocumentException
     */
    @SuppressWarnings("unchecked")
    public static boolean loadServerInfo(File file, Map<String, Integer> domainPort) throws DocumentException {
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(file);
        Element root = document.getRootElement();
        Element service = root.element("Service");
        Element connector = service.element("Connector");
        Integer port = Integer.parseInt(connector.attributeValue("port"));
        String domain = null;

        // 获取域名Engine
        Element engine = service.element("Engine");
        List<Element> hosts = engine.elements("Host");
        for (Element host : hosts) {
            List<Element> aliass = host.elements("Alias");
            if (aliass != null && !aliass.isEmpty()) {
                for (Element alias : aliass) {
                    domainPort.put(alias.getTextTrim(), port);
                    domain = alias.getTextTrim();
                }
            }
            else {
                domainPort.put(host.attributeValue("name"), port);
                domain = host.attributeValue("name");
            }

        }
        return isPortUsing(domain, port);
    }

    /***
     * 端口是否被使用
     *
     * @param host
     * @param port
     * @throws UnknownHostException
     */
    public static boolean isPortUsing(String host, int port) {
        boolean flag = false;
        InetAddress theAddress = null;
        Socket socket = null;
        try {
            theAddress = InetAddress.getByName(host);
            socket = new Socket(theAddress, port);
            flag = true;
        }
        catch (Exception e) {
        }
        finally {
            if (socket != null) {
                try {
                    socket.close();
                }
                catch (IOException e) {
                }
            }
        }
        return flag;
    }

}
