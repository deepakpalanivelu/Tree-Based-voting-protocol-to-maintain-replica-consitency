package client;

/**
 * Created by deepakrtp on 02/04/17.
 */
public class Node {
    private int serverID;
    boolean permissionGranted;

    Node(int serverID) {
        this.serverID = serverID;
        permissionGranted = false;

    }
     public boolean isPermissionGranted() {
        return permissionGranted;
     }

     public void setPermissionGranted(boolean permissionGranted) {
        permissionGranted = true;
     }
}
