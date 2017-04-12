package Message;

/**
 * Created by deepakrtp on 01/04/17.
 */
public class Message {

    public enum Event {
        READ,WRITE,ABORT,COMMIT,ACK;
    }
    Event event;
    int objectID;

    public void setobjectID(int objectID) {
        this.objectID = objectID;
    }
    public int getobjectID() {

        return this.objectID;
    }

    public void setEvent(Event e) {
        this.event = e;
    }

    public Event getEvent() {
        return this.event;
    }


}
