package mx.unam.cruz.victor.utils.files.readers;

import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public abstract class CsvFileReader<T> implements FileReadable<T> {
    protected List<T> rawExtract(String filePath, Class<T> type) throws IOException {
        return new CsvToBeanBuilder<T>(new FileReader(filePath))
                .withType(type)
                .withSkipLines(1)
                .build()
                .parse();
    }
}
