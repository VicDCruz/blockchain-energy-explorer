package mx.unam.cruz.victor;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;

@Getter
@RequiredArgsConstructor
public class ProofOfWork {
    private int nonce = 0;
    private final String data;

    public String mine(int prefix, final long millis) {
        System.out.println("Start mining...");
        String prefixString = "0".repeat(prefix);
        String hashString;

        do {
            hashString = calculateHash(millis);
            nonce++;
        } while (!hashString.substring(0, prefix).equals(prefixString));
        nonce--;

        System.out.println("Mined Block Hash: " + hashString + " with (nonce: " + nonce + ")");
        return hashString;
    }

    private String calculateHash(long millis) {
        return sha256Hex(millis + nonce + data);
    }

    public static boolean verifyHash(String hash, long millis, int nonce, String id) {
        return sha256Hex(millis + nonce + id).equals(hash);
    }
}
