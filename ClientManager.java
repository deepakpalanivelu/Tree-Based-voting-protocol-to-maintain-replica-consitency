package client;

/**
 * Created by deepakrtp on 01/04/17.
 */

import Message.Message;
import Message.MessageReadRequest;
import Message.MessageWriteRequest;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ClientManager {

    private Client client;
    private Map<Integer, ChannelFuture> channelMap;
    private Properties prop;
    private InputStream input;
    private EventLoopGroup workerGroup;
    private long startTime;
    private long endTime;
    BinaryTree tree ;
    boolean requestDone;
    int ackCount;

    ClientManager() throws IOException {

        client = new Client();
        channelMap = new HashMap<Integer, ChannelFuture>();
        prop = new Properties();
        input = getClass().getClassLoader().getResourceAsStream("config.properties");
        prop.load(input);
        workerGroup = new NioEventLoopGroup();
        tree = new BinaryTree();
    }

    public void setup(ClientManager clientManager) throws IOException, InterruptedException {

        try {
            Bootstrap b = new Bootstrap(); // (1)
            b.group(workerGroup); // (2)
            b.channel(NioSocketChannel.class); // (3)
            b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
            final BinaryTree Tree = tree;
            final ClientManager  manager = clientManager;
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new ClientHandler(Tree,manager));
                }
            });

            // Start the client
            // Connecting to 7 servers
            for (int i = 0; i < 7; i++) {
                int port = Integer.parseInt(prop.getProperty(Integer.toString(i)));
                ChannelFuture future = b.connect("localhost",port).sync(); //5
                channelMap.put(i, future);
            }

            // Wait until the connection is closed.
            for (Map.Entry<Integer, ChannelFuture> entry : channelMap.entrySet()) {
                ChannelFuture f = entry.getValue();
                f.channel().closeFuture().sync();
            }
        }
        finally {
            workerGroup.shutdownGracefully();
        }
    }


    public void readMessage(MessageReadRequest msg) {
        // Check the message object and see the server id and and take the channel and pass it to client
        int serverID = msg.getrequestingServerId();
        ChannelFuture future =  channelMap.get(serverID);

        // sending message read request object
        client.sendMessage(future,msg);
        setStartTime();

    }

    public void writeMessage(Message msg) {
        // iterate each channel and pass the channel to the client
        for (Map.Entry<Integer, ChannelFuture> entry : channelMap.entrySet()) {
            ChannelFuture future = entry.getValue();
            client.sendMessage(future,msg);
        }
        setStartTime();

    }

    public void setStartTime() {
        this.startTime = System.nanoTime();
    }

    public long getStartTime() {
        return this.startTime;
    }

    public void setEndTime() {
        this.startTime = System.nanoTime();
    }

    public long getEndTime() {
        return this.startTime;
    }

    public void setAckCount() {
        this.ackCount++;
    }

    public int getAckCount() {
        return this.ackCount;
    }

    public boolean isRequestDone() {
        return this.requestDone;
    }
    public void setRequestDone ( boolean requestDone) {
        this.requestDone = requestDone;
    }

}






