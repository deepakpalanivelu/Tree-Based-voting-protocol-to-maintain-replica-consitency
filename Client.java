package client;

import Message.Message;
import io.netty.channel.ChannelFuture;

/**
 * Created by deepakrtp on 01/04/17.
 */

public class Client {
    // recieving here as Message object
    public void sendMessage(ChannelFuture future, Message msg) {

        future.channel().writeAndFlush(msg);

    }
}
