package netty;

import java.net.InetSocketAddress;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

public class HelloClient {
    public static void main(String args[]) {  
        // Client服务启动器  
        ClientBootstrap bootstrap = new ClientBootstrap(  
                new NioClientSocketChannelFactory(  
                        Executors.newCachedThreadPool(),  
                        Executors.newCachedThreadPool()
                    )
        		);  
  
        // 设置一个处理服务端消息和各种消息事件的类(Handler)  
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {  
            @Override  
            public ChannelPipeline getPipeline() throws Exception {  
                return Channels.pipeline(new HelloClientHandler());  
            }  
        });
  
        // 连接到本地的8000端口的服务端  
        final ChannelFuture future = bootstrap.connect(new InetSocketAddress("127.0.0.1", 8000));
        
        future.getChannel().write("我们都是中国人 啊啊！");  
        
//        new Timer().schedule(new TimerTask() {
//			@Override
//			public void run() {
//				//future.getChannel().write("123");
//			}
//		}, 10000, 1000);
    }  
}
