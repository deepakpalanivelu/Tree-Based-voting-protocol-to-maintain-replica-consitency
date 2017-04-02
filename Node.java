package consistent;

/**
 * Created by deepakrtp on 02/04/17.
 */
public class Node {
    int serverID;
    enum State {
        NOTGRANTED,
        GRANTED
    };
    State state;

    Node(int serverID) {
        this.serverID = serverID;
        state = State.NOTGRANTED;

    }
}
