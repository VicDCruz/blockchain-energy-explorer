package mx.unam.cruz.victor;

import rice.environment.Environment;
import rice.pastry.NodeHandle;
import rice.pastry.NodeIdFactory;
import rice.pastry.PastryNode;
import rice.pastry.PastryNodeFactory;
import rice.pastry.leafset.LeafSet;
import rice.pastry.socket.SocketPastryNodeFactory;
import rice.pastry.standard.RandomNodeIdFactory;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Collection;


public class FreePastryExample {
    public FreePastryExample(int bindPort, InetSocketAddress bootAddress, Environment environment) throws Exception {
        NodeIdFactory nidFactory = new RandomNodeIdFactory(environment);
        PastryNodeFactory factory = new SocketPastryNodeFactory(nidFactory, bindPort, environment);
        PastryNode node = factory.newNode();
        FreePastryApplication application = new FreePastryApplication(node);
        node.boot(bootAddress);

        System.out.println("Node " + node.getId().toString() + " created");
//        environment.getTimeSource().sleep(10_000);
//        Id randomId = nidFactory.generateNodeId();
//        application.routeMessage(randomId);

        environment.getTimeSource().sleep(10_000);
        LeafSet leafSet = node.getLeafSet();
        Collection<NodeHandle> collection = leafSet.getUniqueSet();
        for (NodeHandle nodeHandle : collection) {
            application.routeMessageDirect(nodeHandle);
            environment.getTimeSource().sleep(1000);
        }
    }

    public static void main(String[] args) throws Exception {
        Environment environment = new Environment();
        environment.getParameters().setString("nat_search_policy", "never");
        int bindPort = 9002;
        int bootPort = 9001;
        InetAddress bootInetAddress = InetAddress.getByName(InetAddress.getLocalHost().getHostAddress());
        InetSocketAddress bootAddress = new InetSocketAddress(bootInetAddress, bootPort);
        System.out.println("InetAddress: " + bootInetAddress);

        FreePastryExample freePastryExample = new FreePastryExample(bindPort, bootAddress, environment);
    }
}
