package Message;

/**
 * Created by deepakrtp on 01/04/17.
 */
public class MessageWriteRequest extends Message {

    int valuetoUpdate;
    int serverId;
    public void setValuetoUpdate(int value) { this.valuetoUpdate = valuetoUpdate; }
    public int getValuetoUpdate() {
        return this.valuetoUpdate;
    }
    public void setServerID(int serverId) {
        this.serverId = serverId;
    }
    public int getServerId() {
        return this.serverId;
    }
}
