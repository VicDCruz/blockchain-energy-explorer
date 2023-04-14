package mx.unam.cruz.victor.utils.algorand;

import com.algorand.algosdk.account.Account;
import com.algorand.algosdk.v2.client.common.AlgodClient;
import com.algorand.algosdk.v2.client.common.Response;

public class AccountUtils {
    public static void printBalance(Account account, AlgodClient algodClient) throws Exception {
        Response<com.algorand.algosdk.v2.client.model.Account> accountResponse =
                algodClient.AccountInformation(account.getAddress()).execute();
        if (!accountResponse.isSuccessful()) throw new Exception(accountResponse.message());
        System.out.println("[" + account.getAddress() + "] Account Balance: " + accountResponse.body().amount + " microAlgos");
    }
}
