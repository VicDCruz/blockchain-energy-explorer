package mx.unam.cruz.victor.algorand;

import com.algorand.algosdk.account.Account;
import com.algorand.algosdk.transaction.SignedTransaction;
import com.algorand.algosdk.transaction.Transaction;
import com.algorand.algosdk.util.Encoder;
import com.algorand.algosdk.v2.client.common.AlgodClient;
import com.algorand.algosdk.v2.client.common.Response;
import com.algorand.algosdk.v2.client.model.PendingTransactionResponse;
import com.algorand.algosdk.v2.client.model.PostTransactionsResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.json.JSONObject;

import static com.algorand.algosdk.v2.client.Utils.waitForConfirmation;
import static mx.unam.cruz.victor.utils.algorand.AccountUtils.printBalance;

@AllArgsConstructor
@Builder
public class Broadcaster {
    private final AlgodClient algodClient;

    public void broadcast(Account account, Transaction txn) throws Exception {
        // Sign the transaction
        SignedTransaction signedTxn = account.signTransaction(txn);
        System.out.println("Signed transaction with txid: " + signedTxn.transactionID);

        // Submit the transaction to the network
        byte[] encodedTxBytes = Encoder.encodeToMsgPack(signedTxn);
        Response<PostTransactionsResponse> resp = algodClient.RawTransaction()
                .rawtxn(encodedTxBytes)
                .execute();
        if (!resp.isSuccessful()) {
            throw new Exception(resp.message());
        }
        String id = resp.body().txId;

        // Wait for transaction confirmation
        PendingTransactionResponse pTrx = waitForConfirmation(algodClient, id, 4);

        System.out.println("Transaction " + id + " confirmed in round " + pTrx.confirmedRound);

        System.out.println("Confirmed transaction");
        this.read(account, pTrx);
    }

    private void read(Account account, PendingTransactionResponse pTrx) throws Exception {
        JSONObject jsonObj = new JSONObject(pTrx.toString());
        System.out.println("Transaction information (with notes): " + jsonObj.toString(2));
        System.out.println("Decoded note: " + new String(pTrx.txn.tx.note));
        printBalance(account, this.algodClient);
    }
}
