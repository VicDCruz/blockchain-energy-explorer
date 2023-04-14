package mx.unam.cruz.victor.utils.files.generators;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class CsvFileGenerator implements FileGenerale {
    @Override
    public void export(String filename, List<String> headers, List<List<String>> data) throws FileNotFoundException {
        System.out.println("Export accounts to CSV...");
        File csv = new File(filename);
        try (PrintWriter printWriter = new PrintWriter(csv)) {
            printWriter.println(toCSVRow(headers));
            data.stream().map(this::toCSVRow).forEach(printWriter::println);
        }
        System.out.println(filename + " " + (csv.exists() ? "file created correctly" : "file couldn't be created"));
    }

    private String toCSVRow(List<String> stringList) {
        return String.join(",", stringList);
    }
}
