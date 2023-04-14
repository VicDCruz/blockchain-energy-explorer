package mx.unam.cruz.victor.algorand;

import com.algorand.algosdk.account.Account;
import lombok.Builder;
import mx.unam.cruz.victor.utils.files.generators.CsvFileGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Builder
public class TestAccountGenerator extends AccountGenerator {
    public static final List<String> CSV_HEADER = Arrays.asList("Index", "Address", "Mnemonic");
    public static final String CSV_FILENAME = "keys.csv";
    public static final int TOTAL_ACCOUNTS = 2;

    public static void main(String[] args) throws Exception {
        TestAccountGenerator testAccountGenerator = TestAccountGenerator.builder().build();
        new CsvFileGenerator().export(CSV_FILENAME, CSV_HEADER, testAccountGenerator.toCSVRows(testAccountGenerator.getAccounts()));
        System.out.println("Visit link https://testnet.algoexplorer.io/dispenser to dispense the accounts");
    }

    @Override
    protected List<Account> getAccounts() throws Exception {
        List<Account> accounts = new ArrayList<>();
        for (int i = 0; i < TOTAL_ACCOUNTS; i++) {
            accounts.add(new Account());
        }
        return accounts;
    }
}
