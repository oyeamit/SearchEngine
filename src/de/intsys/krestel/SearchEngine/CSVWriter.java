package de.intsys.krestel.SearchEngine;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class CSVWriter {
    private static final String SAMPLE_CSV_FILE = "/Users/amitmanbansh/Documents/MES/search engine/Assignment_3/test_csv_corpus.csv";

    public void writeCSV(List<String> tokenList_author, List<String> tokenList_heading,
                                List<String> tokenList_description, List<String> tokenList_date,
                                List<String> tokenList_caption) throws IOException {
        try (
                BufferedWriter writer = Files.newBufferedWriter(Paths.get(SAMPLE_CSV_FILE),StandardOpenOption.APPEND);

                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                );
        ) {


                csvPrinter.printRecord(tokenList_author,tokenList_heading,tokenList_description,tokenList_date,
                        tokenList_caption);

                csvPrinter.flush();

        }
    }

    private static final String VOCAB_CSV_FILE = "/Users/amitmanbansh/Documents/MES/search engine/Assignment_3/test_csv_corpus_vocab.csv";

    public void writeVocabCSV(String vocab) throws IOException {
        try (
                BufferedWriter writer = Files.newBufferedWriter(Paths.get(VOCAB_CSV_FILE),StandardOpenOption.APPEND);

                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                );
        ) {


            csvPrinter.printRecord(vocab);

            csvPrinter.flush();

        }
    }
}