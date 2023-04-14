package mx.unam.cruz.victor.utils.files.generators;

import java.io.FileNotFoundException;
import java.util.List;

public interface FileGenerale {
    void export(String filename, List<String> headers, List<List<String>> data) throws FileNotFoundException;
}
