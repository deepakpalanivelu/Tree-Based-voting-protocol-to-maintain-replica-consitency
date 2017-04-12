package client;

import Message.Message;
import Message.MessageWriteRequest;
import Message.MessageReadRequest;

import java.io.IOException;
import java.util.Random;


/**
 * Created by deepakrtp on 01/04/17.
 */
public class ClientSimulator {
    private ClientManager clientManager;

    ClientSimulator() throws IOException {

    }

    public void execute() throws IOException, InterruptedException {


        // set up client Manager

        clientManager = new ClientManager();
        clientManager.setup(clientManager);

        // Randomnly generating an event

        Random rand = new Random();

        // to geneate events
        for (int i = 0; i < 10; i++) { // 10 events to be simulated in each client

            // only after acknowledge recieved from server can proceed with the next
            // atmostt one pending request

            while(true) {
                if (clientManager.isRequestDone()) {
                    break;
                }
                else {
                    Thread.sleep(10);
                }

            }


            int randomObjectId = rand.nextInt(3) + 0;
            System.out.println("Random object selected is" + randomObjectId);
            // randomOperation = 1 write operation
            // Else read Operation
            int randomOperation = rand.nextInt(10) + 1;


            if (randomOperation == 1) {
                // write operations
                Message msg = new MessageWriteRequest();

                msg.setobjectID(randomObjectId);
                msg.setEvent(Message.Event.WRITE);

                int randomNumber = rand.nextInt(10) + 1; // generating a random value to update the object
                ((MessageWriteRequest)msg).setValuetoUpdate(randomNumber);
                // passes it to clientManager
                clientManager.writeMessage(msg);


            } else {
                // randomnly selects the server to read from
                Message msg = new MessageReadRequest();
                msg.setEvent(Message.Event.READ);
                int randomServer = rand.nextInt(6) + 0;
                ((MessageReadRequest)msg).setrequestingServerId(randomServer);
                clientManager.readMessage(msg);

            }
            clientManager.setRequestDone(false);
        }
    }
    public static void main(String [] args) throws IOException, InterruptedException {
        // start up client Simulator here
        ClientSimulator simulator = new ClientSimulator();
        simulator.execute();

    }
}
