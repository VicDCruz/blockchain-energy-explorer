package mx.unam.cruz.victor;

import rice.environment.Environment;
import rice.pastry.NodeHandle;
import rice.pastry.NodeIdFactory;
import rice.pastry.PastryNode;
import rice.pastry.PastryNodeFactory;
import rice.pastry.leafset.LeafSet;
import rice.pastry.socket.SocketPastryNodeFactory;
import rice.pastry.standard.RandomNodeIdFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.text.MessageFormat;
import java.util.Collection;


public class FreePastryExample {

    public static final int WAIT_MILLIS_TO_CONNECT = 5_000;

    public FreePastryExample(int bindPort, InetSocketAddress bootAddress, Environment environment) throws Exception {
        PastryNode node = createPastryNode(bindPort, environment);

        FreePastryApplication application = new FreePastryApplication(node);
        node.boot(bootAddress);

        System.out.println("Node " + node.getId().toStringFull() + " created");

        this.waitOthers(environment);

        LeafSet leafSet = node.getLeafSet();
        Collection<NodeHandle> collection = leafSet.getUniqueSet();
        application.setTotalPeers(collection.size());
        application.mine();
        for (NodeHandle nodeHandle : collection) {
            application.routeMessageDirect(nodeHandle);
            environment.getTimeSource().sleep(1000);
            application.increaseMessagesSent();
        }
    }

    private void waitOthers(Environment environment) throws InterruptedException {
        System.out.println(MessageFormat.format("Wait {0} secs for others to connect", WAIT_MILLIS_TO_CONNECT));
        environment.getTimeSource().sleep(WAIT_MILLIS_TO_CONNECT); // wait others to connect
    }

    private PastryNode createPastryNode(int bindPort, Environment environment) throws IOException {
        NodeIdFactory nidFactory = new RandomNodeIdFactory(environment);
        PastryNodeFactory factory = new SocketPastryNodeFactory(nidFactory, bindPort, environment);
        return factory.newNode();
    }
}
