package Message;

/**
 * Created by deepakrtp on 01/04/17.
 */
public class MessageReadRequest extends Message {

    int serverID;
    int valueRead;

    public void setrequestingServerId(int serverID) {
        this.serverID = serverID;
    }

    public int getrequestingServerId() {
        return this.serverID;
    }

    public int getValueRead() {
        return this.valueRead;
    }

    public void setValueRead(int valueRead) {
        this.valueRead = valueRead;
    }

}
