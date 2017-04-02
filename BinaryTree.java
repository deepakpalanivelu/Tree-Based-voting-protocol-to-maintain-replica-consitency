package consistent;

/**
 * Created by deepakrtp on 01/04/17.
 */
import io.netty.bootstrap.Bootstrap;

public class BinaryTree {

    int serverID;
    BinaryTree left;
    BinaryTree right;
    enum State {
        NOTGRANTED,
        GRANTED
    };
    State state;
    BinaryTree node[];

    BinaryTree() {
        BinaryTree node[] = new BinaryTree[7];
    }

    BinaryTree(int serverID) {
        this.serverID = serverID;
        state = State.NOTGRANTED;
    }

    public void createtree() {

        for(int i = 0 ; i < 7;i++) {
            node[i] = new BinaryTree(i);
        }
        node[0].left = node[1];
        node[0].right = node[2];
        node[1].left = node[3];
        node[1].right = node[4];
        node[2].left = node[5];
        node[2].right = node[6];

    }
    public void setPermissionGranted(int serverID) {
        node[serverID].state = State.GRANTED;

    }

    public State getState() {
        return this.state;
    }
    public boolean checkGranted(BinaryTree node)  {
        if(node.getState() == State.GRANTED) {
            return true;
        }
        else {
            return false;
        }
    }
    public boolean checkLogicalTree() {
        return checkLogicalTree(node[0]);
    }
    public boolean checkLogicalTree(BinaryTree root) {

        if(root.left == null && root.right == null) {
            return checkGranted(root);
        }
        boolean leftsubTree = checkLogicalTree(root.left);
        boolean rightsubTree = checkLogicalTree(root.right);
        if( (checkGranted(root) && leftsubTree || rightsubTree) || (leftsubTree && rightsubTree)) {
            return true;
        }
        else {
            return false;
        }

    }

    public void resetState() {
        for(int i = 0 ; i < 7;i++) {
            node[i].state = State.NOTGRANTED ;
        }
    }

}
