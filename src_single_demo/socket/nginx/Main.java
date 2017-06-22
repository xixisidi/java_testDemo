package socket.nginx;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Map;
import java.util.Set;

public class Main {
    private static int PORT = 80;// 监听的端口号
    private ServerSocket serverSocket;
    private Map<String, Integer> portMap;

    public static void main(String[] args) throws Exception {
        new Main();
    }

    public Main() throws Exception {
    	//启动tomcat
    	startTomcat();
    	
        // 创建服务
        createServer();

        // 监听连接
        listenConnection();
    }
    
    private void startTomcat() throws Exception{
    	//获取路径
    	String rootPath = System.getProperty("user.dir") + "\\";
    	String tomcatHome = "\"" + PropertiesUtil.getParam("tomcat_home") + "\"";
    	
    	//加载并启动tomcat
    	portMap = TomcatUtil.loadServerInfos(rootPath, tomcatHome);
    	
    	String serverXml = PropertiesUtil.getParam("server_xml");
    	if(serverXml != null){
    		TomcatUtil.loadServerInfo(new File(serverXml), portMap);
    	}
    	
    	//输出地址
    	Set<String> keySet = portMap.keySet();
    	System.out.println("");
    	for (String key : keySet) {
			System.out.println(key + ":" + portMap.get(key));
		}
    }

    /**
     * 创建服务
     */
    private void createServer() {
        try {
            serverSocket = new ServerSocket(PORT);
        }
        // 端口号被占用
        catch (Exception e) {
        	e.printStackTrace();
        }
    }

    /**
     * 监听连接
     *
     * @throws IOException
     */
    private void listenConnection() {
        try {
            while (true) {
                new Thread(new HttpHandler(serverSocket.accept(),portMap)).start();
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
