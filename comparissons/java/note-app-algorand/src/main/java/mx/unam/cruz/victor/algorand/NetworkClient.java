package mx.unam.cruz.victor.algorand;

import com.algorand.algosdk.v2.client.common.AlgodClient;
import lombok.Builder;

@Builder
public class NetworkClient {

//    private final String host = "localhost";
//    private final int port = 4001;
//    private final String token = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
    private final int port = 443;
    private final String host = "https://testnet-api.algonode.cloud";
    private final String token = "";

    public AlgodClient connect() {
        return new AlgodClient(host, port, token);
    }
}
