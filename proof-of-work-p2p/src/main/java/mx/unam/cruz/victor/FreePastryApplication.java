package mx.unam.cruz.victor;

import rice.p2p.commonapi.*;

public class FreePastryApplication implements Application {
    protected Endpoint endpoint;
    private final String message;
    private final String instance = " Instance ID";

    public FreePastryApplication(Node node) {
        this.endpoint = node.buildEndpoint(this, instance);
        this.message = "Hello there! from Instance: " + instance + " Sent at: [" + this.getCurrentTime() + "]";
        this.endpoint.register();
    }

    private long getCurrentTime() {
        return this.endpoint.getEnvironment().getTimeSource().currentTimeMillis();
    }

    public void routeMessage(Id randomId) {
        System.out.println(
                "Message Sent\n\tCurrent Node: " +
                this.endpoint.getId() + "\n\tDestination: " + randomId + "\n\tTime: " +
                getCurrentTime()
        );
        Message msg = new PastryMessage(this.endpoint.getId(), randomId, this.message);
        endpoint.route(randomId, msg, null);
    }

    public void routeMessageDirect(NodeHandle nh) {
        System.out.println("Message Sent Direct\n\tCurrent Node: " +
                           this.endpoint.getId() + " Destination: " +
                           nh + "\n\tTime: " + getCurrentTime());
        Message msg = new PastryMessage(endpoint.getId(), nh.getId(), "DIRECT-" + message);
        endpoint.route(null, msg, nh);
    }

    @Override
    public boolean forward(RouteMessage routeMessage) {
        return true;
    }

    @Override
    public void deliver(Id id, Message message) {
        System.out.println("Message Received\n\tCurrent Node: " + this.endpoint.getId() +
                           "\n\tMessage: " + message +
                           "\n\tTime: " + getCurrentTime());
    }

    @Override
    public void update(NodeHandle nodeHandle, boolean b) {

    }
}
