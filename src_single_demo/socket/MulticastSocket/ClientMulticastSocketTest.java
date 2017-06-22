package socket.MulticastSocket;

import java.io.IOException;  
import java.net.DatagramPacket;  
import java.net.InetAddress;  
import java.net.MulticastSocket;  
  
/** 
 * TODO  
 * @author cuiran 
 * @version 1.0.0 
 */  
public class ClientMulticastSocketTest {  
  
    /** 
     * 2014-1-9 下午3:24:25 
     * @param args 
     *  
     */  
    public static void main(String[] args) {  
        // TODO Auto-generated method stub  
        MulticastSocket multicastSocket;  
        try {  
            multicastSocket = new MulticastSocket(10000);  
            InetAddress address = InetAddress.getByName("239.0.0.1");  
            multicastSocket.joinGroup(address);  
            byte[] buf = new byte[1024];  
              
            while (true) {  
                DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length);  
                multicastSocket.receive(datagramPacket); // 接收数据，同样会进入阻塞状态  
                  
                byte[] message = new byte[datagramPacket.getLength()]; // 从buffer中截取收到的数据  
                System.arraycopy(buf, 0, message, 0, datagramPacket.getLength());  
                System.out.println(datagramPacket.getAddress());  
                System.out.println(new String(message));  
            }  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } // 接收数据时需要指定监听的端口号  
         
    }  
  
}  
