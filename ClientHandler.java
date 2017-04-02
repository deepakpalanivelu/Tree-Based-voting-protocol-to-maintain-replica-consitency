package consistent;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by deepakrtp on 01/04/17.
 */
public class ClientHandler extends SimpleChannelInboundHandler<Message> {
    BinaryTree tree;
    ClientManager clientManager;

    ClientHandler(BinaryTree tree,ClientManager clientManager) {
        this.tree = tree;
        this.clientManager = clientManager;
    }
    @Override
    public void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {

        if(msg.getEvent() == Message.Event.READ) {
            // check timer
            //if time less
            int value = msg.getValueRead();
            System.out.println("Object " + msg.getobjectID() + "  current value = " +value);
            clientManager.setEndTime();
            if(clientManager.getStartTime() - clientManager.getEndTime() < 20) {
                msg.setActivity(Message.Activity.COMMIT);
            }
            else {
                msg.setActivity(Message.Activity.ABORT);
            }
            ctx.writeAndFlush(msg);
        }

        /*
         checks logical tree also cuz if the operation is already committed and then a server responds for a already

         committed operation it should be ignored
          */
        else if(msg.getEvent() == Message.Event.WRITE && !tree.checkLogicalTree()){
            // write event
            tree.setPermissionGranted(msg.getrequestingServerId());
            if(tree.checkLogicalTree()) {

                clientManager.setEndTime();
                if(clientManager.getStartTime() - clientManager.getEndTime() < 20) {
                    msg.setActivity(Message.Activity.COMMIT);
                }
                else {
                    msg.setActivity(Message.Activity.ABORT);
                }
            }
            // sends to all servers either to commit to abort
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
