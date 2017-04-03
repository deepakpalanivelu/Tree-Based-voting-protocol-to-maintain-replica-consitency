package client;

/**
 * Created by deepakrtp on 01/04/17.
 */


public class BinaryTree {

    private static final int NO_OF_SERVERS = 7;
    private Node node[] = new Node[NO_OF_SERVERS];
    private int length;
    private int root;

    BinaryTree() {

        for(int i = 1 ; i < node.length;i++) {
            node[i] = new Node(i);
        }
        root = 0;
        length = node.length - 1;
    }

    public void setPermissionGranted(int serverID) {

        node[serverID].setPermissionGranted(true);
    }

//    public boolean getState(int serverID) {
//
//        return node[serverID].;
//    }

    public boolean checkifGranted(int serverID)  {
        return node[serverID].isPermissionGranted();

    }


    public boolean checkLogicalTree() {
        return checkLogicalTree(root);
    }

    public boolean checkLogicalTree(int root) {

        int lefttreeIndex = 2 * root + 1;
        int righttreeIndex = 2 * root + 2;

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
        for(int i = 0 ; i < NO_OF_SERVERS;i++) {
            node[i].setPermissionGranted(false);
        }
    }
}
