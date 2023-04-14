package mx.unam.cruz.victor.utils.files.readers;

import mx.unam.cruz.victor.notes.RawAccount;

import java.io.IOException;
import java.util.List;

public class AlgorandAccountCsvFileReader extends CsvFileReader<RawAccount> {

    @Override
    public List<RawAccount> extract(String filename) throws IOException {
        return this.rawExtract(filename, RawAccount.class);
    }
}
