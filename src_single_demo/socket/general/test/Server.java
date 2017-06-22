package socket.general.test;

import java.io.BufferedReader;  
import java.io.DataInputStream;  
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;  
import java.net.Socket;  
  
public class Server {  
    public static final int PORT = 12345;//监听的端口号     
      
    public static void main(String[] args) {    
        System.out.println("服务器启动...\n");    
        Server server = new Server();    
        server.init();    
    }    
    
    public void init() {    
        try {    
            ServerSocket serverSocket = new ServerSocket(PORT);    
            while (true) {    
                // 一旦有堵塞, 则表示服务器与客户端获得了连接    
                Socket client = serverSocket.accept();    
                // 处理这次连接    
                new HandlerThread(client);    
            }    
        } catch (Exception e) {    
            System.out.println("服务器异常: " + e.getMessage());    
        }    
    }    
    
    private class HandlerThread implements Runnable {    
        private Socket socket;    
        public HandlerThread(Socket client) {    
            socket = client;    
            new Thread(this).start();    
        }    
    
        public void run() {    
        	while(true){//保持长连接  
        		    try {  
        		            Thread.sleep(100);//等待时间  
        		    } catch (InterruptedException e1) {  
        		            e1.printStackTrace();  
        		    }  
        		    if (socket !=null){  
        		        try {  
        		                String ip = socket.getInetAddress().toString().replace("/", "");  
        		                System.out.println("====socket.getInetAddress()====="+ip);  
        		                socket.setKeepAlive(true);  
        		                InputStream is = socket.getInputStream();  
        		                DataOutputStream os = new DataOutputStream(socket.getOutputStream());    
        		                System.out.println("服务器端接受请求");  
        		                
        		                DataInputStream input = new DataInputStream(is);  
        		                String tempdata = input.readUTF(); 
	        		            System.out.println("接收到的数据为："+tempdata);  
	        		            if(tempdata.contains("stop")){  
	        		                is.close();  
	        		                os.close();  
	        		            }  
	        		            os.writeUTF("接收到");    
	        		            os.flush();  
        		        }catch(Exception e){  
        		            System.out.println("出现了错误");  
        		        }  
        		    }  
        		}  

        }    
    }    
}    

