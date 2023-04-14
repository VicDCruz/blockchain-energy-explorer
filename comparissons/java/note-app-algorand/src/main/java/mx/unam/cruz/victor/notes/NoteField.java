package mx.unam.cruz.victor.notes;

import com.algorand.algosdk.account.Account;
import com.algorand.algosdk.transaction.Transaction;
import com.algorand.algosdk.v2.client.common.AlgodClient;
import com.algorand.algosdk.v2.client.common.Response;
import com.algorand.algosdk.v2.client.model.NodeStatusResponse;
import com.algorand.algosdk.v2.client.model.PendingTransactionResponse;
import mx.unam.cruz.victor.algorand.Broadcaster;
import mx.unam.cruz.victor.algorand.NetworkClient;
import mx.unam.cruz.victor.utils.files.readers.AlgorandAccountCsvFileReader;

import java.util.List;

import static mx.unam.cruz.victor.utils.algorand.AccountUtils.printBalance;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

public class NoteField {
    private AlgodClient algodClient = null;

    public static void main(String[] args) throws Exception {
        NoteField noteField = new NoteField();
        noteField.example();
    }

    public void example() throws Exception {
        this.algodClient = defaultIfNull(this.algodClient, NetworkClient.builder().build().connect());

        String fileName = "keys.csv";
        List<RawAccount> rawAccounts = new AlgorandAccountCsvFileReader().extract(fileName);
        System.out.println(rawAccounts);
        Account sender = new Account(rawAccounts.stream().findFirst().orElseThrow().mnemonic());

        printBalance(sender, this.algodClient);

        String note = "showing prefix and more";

        Transaction transaction = Transaction.PaymentTransactionBuilder()
                .sender(sender.getAddress())
                .noteUTF8(note)
                .amount(1)
                .receiver(rawAccounts.get(1).address())
                .lookupParams(this.algodClient)
                .build();

        Broadcaster.builder().algodClient(this.algodClient).build().broadcast(sender, transaction);
    }

    public PendingTransactionResponse waitForConfirmation(AlgodClient myclient, String txID, Integer timeout)
            throws Exception {
        if (myclient == null || txID == null || timeout < 0) {
            throw new IllegalArgumentException("Bad arguments for waitForConfirmation.");
        }
        Response<NodeStatusResponse> resp = myclient.GetStatus().execute();
        if (!resp.isSuccessful()) throw new Exception(resp.message());

        NodeStatusResponse nodeStatusResponse = resp.body();
        Long startRound = nodeStatusResponse.lastRound + 1;
        Long currentRound = startRound;
        while (currentRound < (startRound + timeout)) {
            // Check the pending transactions
            Response<PendingTransactionResponse> resp2 = myclient.PendingTransactionInformation(txID).execute();
            if (resp2.isSuccessful()) {
                PendingTransactionResponse pendingInfo = resp2.body();
                if (pendingInfo != null) {
                    if (pendingInfo.confirmedRound != null && pendingInfo.confirmedRound > 0) {
                        // Got the completed Transaction
                        return pendingInfo;
                    }
                    if (pendingInfo.poolError != null && pendingInfo.poolError.length() > 0) {
                        // If there was a pool error, then the transaction has been rejected!
                        throw new Exception("The transaction has been rejected with a pool error: " + pendingInfo.poolError);
                    }
                }
            }

            Response<NodeStatusResponse> resp3 = myclient.WaitForBlock(currentRound).execute();
            if (!resp3.isSuccessful()) {
                throw new Exception(resp3.message());
            }
            currentRound++;
        }
        throw new Exception("Transaction not confirmed after " + timeout + " rounds!");
    }
}
