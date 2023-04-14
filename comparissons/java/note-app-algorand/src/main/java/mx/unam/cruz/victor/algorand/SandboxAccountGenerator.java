package mx.unam.cruz.victor.algorand;

import com.algorand.algosdk.account.Account;
import com.algorand.algosdk.crypto.Address;
import com.algorand.algosdk.kmd.client.ApiException;
import com.algorand.algosdk.kmd.client.KmdClient;
import com.algorand.algosdk.kmd.client.api.KmdApi;
import com.algorand.algosdk.kmd.client.model.APIV1Wallet;
import com.algorand.algosdk.kmd.client.model.ExportKeyRequest;
import com.algorand.algosdk.kmd.client.model.InitWalletHandleTokenRequest;
import com.algorand.algosdk.kmd.client.model.ListKeysRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import mx.unam.cruz.victor.utils.files.generators.CsvFileGenerator;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Builder
public class SandboxAccountGenerator extends AccountGenerator {
    private static String KMD_HOST = "http://localhost:4002";
    private static String KMD_TOKEN = "a".repeat(64);
    private static KmdApi kmd = null;
    public static final List<String> CSV_HEADER = Arrays.asList("Index", "Address", "Mnemonic");
    public static final String CSV_FILENAME = "keys.csv";

    public static void main(String[] args) throws Exception {
        AccountGenerator sandboxAccountGenerator = SandboxAccountGenerator.builder().build();
        new CsvFileGenerator().export(CSV_FILENAME, CSV_HEADER, sandboxAccountGenerator.toCSVRows(sandboxAccountGenerator.getAccounts()));
    }

    @Override
    protected List<Account> getAccounts() throws Exception {
        // Initialize KMD v1 client
        KmdClient kmdClient = new KmdClient();
        kmdClient.setBasePath("http://localhost:4002");
        kmdClient.setApiKey("a".repeat(64));
        kmd = new KmdApi(kmdClient);

        // Get accounts from sandbox.
        String walletHandle = getDefaultWalletHandle();
        List<Address> addresses = getWalletAccounts(walletHandle);

        List<Account> accts = new ArrayList<>();
        for (Address addr : addresses) {
            byte[] pk = lookupPrivateKey(addr, walletHandle);
            accts.add(new Account(pk));
        }
        return accts;
    }

    private List<Address> getWalletAccounts(String walletHandle) throws ApiException, NoSuchAlgorithmException {
        List<Address> accounts = new ArrayList<>();
        ListKeysRequest keysRequest = new ListKeysRequest();
        keysRequest.setWalletHandleToken(walletHandle);
        for (String addr : kmd.listKeysInWallet(keysRequest).getAddresses()) accounts.add(new Address(addr));
        return accounts;
    }

    public byte[] lookupPrivateKey(Address addr, String walletHandle) throws ApiException {
        ExportKeyRequest req = new ExportKeyRequest();
        req.setAddress(addr.toString());
        req.setWalletHandleToken(walletHandle);
        req.setWalletPassword("");
        return kmd.exportKey(req).getPrivateKey();
    }

    public String getDefaultWalletHandle() throws ApiException {
        for (APIV1Wallet w : kmd.listWallets().getWallets()) {
            if (w.getName().equals("unencrypted-default-wallet")) {
                InitWalletHandleTokenRequest tokenreq = new InitWalletHandleTokenRequest();
                tokenreq.setWalletId(w.getId());
                tokenreq.setWalletPassword("");
                return kmd.initWalletHandleToken(tokenreq).getWalletHandleToken();
            }
        }
        throw new RuntimeException("Default wallet not found.");
    }
}
