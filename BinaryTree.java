package consistent;

/**
 * Created by deepakrtp on 01/04/17.
 */
import io.netty.bootstrap.Bootstrap;


public class BinaryTree {

    public final int NOOFSERVERS = 8;
    Node node[] = new Node[NOOFSERVERS];
    int length;
    int root;

    BinaryTree() {

        for(int i = 1 ; i < node.length;i++) {
            node[i] = new Node(i);
        }
        root = 1;
        length = node.length - 1;
    }

    public void setPermissionGranted(int serverID) {
        node[serverID].state = Node.State.GRANTED;
    }

    public Node.State getState(int serverID) {
        return node[serverID].state;
    }

    public boolean checkifGranted(int serverID)  {
        if(node[serverID].state == Node.State.GRANTED) {
            return true;
        } else {
            return false;
        }
    }


    public boolean checkLogicalTree() {
        return checkLogicalTree(root);
    }

    public boolean checkLogicalTree(int root) {

        int lefttreeIndex = 2 * root;
        int righttreeIndex = 2 * root + 1;

        if(lefttreeIndex > length && righttreeIndex > length) {
            return checkifGranted(root);
        }
        boolean leftsubTree = checkLogicalTree(lefttreeIndex);
        boolean rightsubTree = checkLogicalTree(righttreeIndex);
        if((checkifGranted(root) && (leftsubTree || rightsubTree)) || (leftsubTree && rightsubTree)) {
            return true;
        } else {
            return false;
        }
    }

    public void resetState() {
        for(int i = 0 ; i < 7;i++) {
            node[i].state = Node.State.NOTGRANTED ;
        }
    }
}
