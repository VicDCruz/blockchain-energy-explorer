package org.example;

import org.apache.commons.codec.digest.DigestUtils;

import java.security.NoSuchAlgorithmException;

public class Main {
    public static int nonce = 0;
    public static String data = "Hello, world!";

    public static void main(String[] args) throws NoSuchAlgorithmException {
        mine(6, "e7wM2iSaaNssTJcHZRGaZYTEoSibEm6sqDdNm");
    }

    public static void mine(int prefix, String genesisHash) throws NoSuchAlgorithmException {
        long millis = System.currentTimeMillis();
        String prefixString = "0".repeat(prefix);
        String hashString;

        do {
            hashString = calculateHash(genesisHash, millis);
            nonce++;
        } while (!hashString.substring(0, prefix).equals(prefixString));

        System.out.println("Mined Block Hash: " + hashString + " with (nonce: " + nonce + ")");
    }

    private static String calculateHash(String genesisHash, long millis) {
        String dataToHash = genesisHash + millis + nonce + data;
        return DigestUtils.sha256Hex(dataToHash);
    }
}