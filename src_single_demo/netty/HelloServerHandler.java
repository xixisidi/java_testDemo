package netty;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

public class HelloServerHandler extends SimpleChannelHandler {
    
	/** 
     * 当有客户端绑定到服务端的时候触发，打印"Hello world, I'm server."
     */  
    @Override
    public void channelConnected(ChannelHandlerContext ctx,ChannelStateEvent e) {  
        System.out.println("Hello world, I'm server.");  
    }
}
