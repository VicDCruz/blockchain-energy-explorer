package mx.unam.cruz.victor.algorand;

import com.algorand.algosdk.account.Account;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class AccountGenerator {
    protected List<List<String>> toCSVRows(List<Account> accounts) {
        return IntStream.range(0, accounts.size())
                .mapToObj(index -> {
                    Account account = accounts.get(index);
                    try {
                        return Arrays.asList(Integer.toString(index + 1), account.getAddress().encodeAsString(), account.toMnemonic());
                    } catch (NoSuchAlgorithmException e) {
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toList());
    }

    protected abstract List<Account> getAccounts() throws Exception;
}
