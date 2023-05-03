package mx.unam.cruz.victor;

import lombok.Setter;
import rice.p2p.commonapi.*;

import java.text.MessageFormat;
import java.util.*;

public class FreePastryApplication implements Application {
    protected Endpoint endpoint;
    private Body bodyMessage;
    @Setter
    private int totalPeers = 0;
    private int messagesSent = 0;
    private final List<Body> bodiesReceived;

    public FreePastryApplication(Node node) {
        String instance = " Instance ID";
        this.endpoint = node.buildEndpoint(this, instance);
        this.endpoint.register();
        this.bodiesReceived = new ArrayList<>();
    }

    private long getCurrentTime() {
        return this.endpoint.getEnvironment().getTimeSource().currentTimeMillis();
    }

    private String getFullId() {
        return this.endpoint.getId().toStringFull();
    }

    private void verifyFinish() {
        if (this.messagesSent == this.totalPeers && this.bodiesReceived.size() == this.totalPeers) {
            this.printWinner();
            System.out.println(MessageFormat.format(
                    "[Instance: " + getFullId() + "] finish correctly [send: ({1} / {0}), receive: ({2} / {0})]",
                    this.totalPeers, this.messagesSent, this.bodiesReceived.size() - 1));
            System.exit(0);
        }
    }

    private void printWinner() {
        this.bodiesReceived.add(this.bodyMessage);
        System.out.println(this.bodiesReceived);
        Body winner = this.bodiesReceived.stream()
                .filter((b) -> ProofOfWork.verifyHash(b.getHash(), b.getMillis(), b.getNonce(), b.getId()))
                .min(Comparator.comparingLong(Body::getSentAt))
                .orElseThrow(NoSuchElementException::new);
        System.out.println("The winner is " + (winner.equals(this.bodyMessage) ? "ME 🤩" : (winner.getId() + " 😞")));
    }

    public void increaseMessagesSent() {
        this.messagesSent++;
        this.verifyFinish();
    }

    public void routeMessageDirect(NodeHandle receiver) {
        Random random = new Random();
        int lambda = 100;
        int sleep = random.nextInt(lambda);
        System.out.println("Sleeping for " + sleep + " ms");
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Message Sent Direct" +
                           "\n\tCurrent Node: " + getFullId() + " Destination: " + receiver.getId().toStringFull() +
                           "\n\tTime: " + new Date(this.bodyMessage.getSentAt() - 3600 * 1000));
        Message msg = new PastryMessage(endpoint.getId(), receiver.getId(), JsonParseUtil.stringify(bodyMessage));
        endpoint.route(null, msg, receiver);
    }

    public void mine() {
        ProofOfWork proofOfWork = new ProofOfWork(this.getFullId());
        long millis = this.getCurrentTime();
        String hash = proofOfWork.mine(5, millis);
        this.bodyMessage = Body.builder()
                .hash(hash).millis(millis).nonce(proofOfWork.getNonce())
                .id(this.getFullId()).sentAt(this.getCurrentTime())
                .build();
    }

    @Override
    public void deliver(Id id, Message message) {
        System.out.println("Message Received\n\tCurrent Node: " + getFullId() +
                           "\n\tMessage: " + message +
                           "\n\tTime: " + new Date(this.getCurrentTime() - 3600 * 1000));
        this.bodiesReceived.add(JsonParseUtil.parse(((PastryMessage) message).getMessageBody(), Body.class));
        this.verifyFinish();
    }

    @Override
    public boolean forward(RouteMessage routeMessage) {
        return true;
    }

    @Override
    public void update(NodeHandle nodeHandle, boolean b) {

    }
}
