package mx.unam.cruz.victor.utils.files.readers;

import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.util.List;

public interface FileReadable<T> {
    List<T> extract(String filename) throws CsvValidationException, IOException;
}
