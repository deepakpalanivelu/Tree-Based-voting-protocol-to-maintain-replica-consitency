package consistent;

/**
 * Created by deepakrtp on 01/04/17.
 */
public class Message {

    enum Event {
        READ,WRITE,ABORT,COMMIT,ACK;
    }
    Event event;
    int requestServer;
    int valuetoUpdate;
    int valueRead;
    int objectID;
    int value;
    enum Activity {
        COMMIT,ABORT
    }
    Activity activity;

    public void setValueRead(int value) {
        this.valueRead = valueRead;
    }
    public int getValueRead() {
        return this.valueRead;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setrequestingServerId(int requestServer) {
        this.requestServer = requestServer;
    }

    public int getrequestingServerId() {
        return this.requestServer;
    }

    public void setobjectID(int objectID) {
        this.objectID = objectID;
    }
    public int getobjectID() {
        return this.objectID;
    }

    public void setValuetoUpdate(int valuetoUpdate) {
        this.valuetoUpdate = valuetoUpdate;
    }

    public void setEvent(Event e) {
        this.event = e;
    }

    public Event getEvent() {
        return this.event;
    }


}
