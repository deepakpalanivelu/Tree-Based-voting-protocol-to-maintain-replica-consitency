package client;

import Message.Message;
import Message.MessageReadRequest;
import Message.MessageWriteRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by deepakrtp on 01/04/17.
 */
public class ClientHandler extends SimpleChannelInboundHandler<Message> {
    private BinaryTree tree;
    private ClientManager clientManager;

    ClientHandler(BinaryTree tree,ClientManager clientManager) {
        this.tree = tree;
        this.clientManager = clientManager;
    }
    @Override
    public void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {

        if(msg.getEvent() == Message.Event.READ) {
            // check timer
            //if time less
            MessageReadRequest readMsg = (MessageReadRequest) msg;
            int value = readMsg.getValueRead();
            System.out.println("Object " + readMsg.getobjectID() + "  current value = " +value);
            clientManager.setEndTime();
            if(clientManager.getStartTime() - clientManager.getEndTime() < 20) {
                msg.setEvent(Message.Event.COMMIT); // commit
            }
            else {
                msg.setEvent(Message.Event.ABORT); // Abort
            }
            // confused wether to send Message object or MessageReadRequst Object
            ctx.writeAndFlush(msg);
        }

        /*
         checks logical tree also cuz if the operation is already committed and then a server responds for a already
            committed operation it should be ignored
        */

        else if(msg.getEvent() == Message.Event.WRITE && !tree.checkLogicalTree()){
            // write event
            MessageWriteRequest writeMsg = (MessageWriteRequest) msg;
            tree.setPermissionGranted(writeMsg.getServerId());
            if(tree.checkLogicalTree()) {

                clientManager.setEndTime();
                if(clientManager.getStartTime() - clientManager.getEndTime() < 20) {
                    writeMsg.setEvent(Message.Event.COMMIT);
                } else {
                    writeMsg.setEvent(Message.Event.ABORT);
                }
            }

            // sends to all servers either to commit to abort

            // confused wether to send Message object or MessageWriteRequstObject
            clientManager.writeMessage(msg);
        }

        else if(msg.getEvent() == Message.Event.ACK) {
            clientManager.setAckCount();
            if(clientManager.getAckCount() == 7) {
                // resetting all state
                tree.resetState();
                // only after the request is perfomed next request is performed
                clientManager.setRequestDone(true);
            }
                // only after the request is perfomed next request is performed
        }
    }
}
