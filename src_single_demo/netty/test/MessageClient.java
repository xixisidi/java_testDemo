package netty.test;

import java.net.InetSocketAddress;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

public class MessageClient {
    public static void main(String args[]) {
        String host = "127.0.0.1";
        // String host = "192.168.60.113";
        // int port = 9550;
        int port = 20889;
        // Configure the client.
        ClientBootstrap bootstrap = new ClientBootstrap(
                new NioClientSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
        // Set up the event pipeline factory.
        // bootstrap.setPipelineFactory(new MessageServerPipelineFactory());
        bootstrap.getPipeline().addLast("decoder", new MessageDecoder());
        bootstrap.getPipeline().addLast("encoder", new MessageEncoder());
        bootstrap.getPipeline().addLast("handler", new MessageClientHandler());
        // Start the connection attempt.
        final ChannelFuture future = bootstrap.connect(new InetSocketAddress(host, port));

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                future.getChannel().write("123");
            }
        }, 10000, 10000);

        // Wait until the connection is closed or thX`e connection attempt
        // fails.
        future.getChannel().getCloseFuture().awaitUninterruptibly();

        // Shut down thread pools to exit.
        // future.getChannel().write("我们都是中国人 啊啊！");
        bootstrap.releaseExternalResources();
    }
}
