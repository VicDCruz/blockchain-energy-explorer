package mx.unam.cruz.victor;

import rice.environment.Environment;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Optional;

public class Main {
    // Command: /Users/victor.cruz/Library/Java/JavaVirtualMachines/corretto-18.0.2/Contents/Home/bin/java -jar target/proof-of-work-p2p-1.0-SNAPSHOT-jar-with-dependencies.jar 9002
    public static void main(String[] args) throws Exception {
        Environment environment = new Environment();
        environment.getParameters().setString("nat_search_policy", "never");
        int bindPort = Integer.parseInt(Optional.of(args[0]).orElse("9001")); // DHT port
        int bootPort = 9001; // hardcoded port to connect to other instances

        String dhtAddress = Optional.of(args[1]).orElse(InetAddress.getLocalHost().getHostAddress());
        InetAddress bootInetAddress = InetAddress.getByName(dhtAddress); // DHT address
        InetSocketAddress bootAddress = new InetSocketAddress(bootInetAddress, bootPort);
        System.out.println("InetAddress: " + bootInetAddress + ", Bind port: " + bindPort);

        new FreePastryExample(bindPort, bootAddress, environment);
    }
}
