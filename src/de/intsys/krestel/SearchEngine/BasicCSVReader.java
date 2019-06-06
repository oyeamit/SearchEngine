package de.intsys.krestel.SearchEngine;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class BasicCSVReader {
    private static final String SAMPLE_CSV_FILE_PATH = "/Users/amitmanbansh/Documents/MES/search engine/Assignment_3/Corpus_1_without_url.csv";

    public static void main(String[] args) throws IOException {
        try (
                Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
        ) {
            CSVWriter csvWriter = new CSVWriter();

            TokenizerNew tkn = new TokenizerNew();

            for (CSVRecord csvRecord : csvParser) {
                // Accessing Values by Column Index
                String Article_author = csvRecord.get(0);
                String Article_heading = csvRecord.get(1);
                String Article_description = csvRecord.get(2);
                String Article_date = csvRecord.get(3);
                String Image_caption = csvRecord.get(4);



                List<String> tokenList_author = new ArrayList<String>();
                tokenList_author.addAll(tkn.tokenize(Article_author));
                //System.out.println(tokenList_author);

                List<String> tokenList_heading = new ArrayList<String>();
                tokenList_heading.addAll(tkn.tokenize(Article_heading));
                //System.out.println(tokenList_heading);

                List<String> tokenList_description = new ArrayList<String>();
                tokenList_description.addAll(tkn.tokenize(Article_description));
                //System.out.println(tokenList_description);

                List<String> tokenList_date = new ArrayList<String>();
                tokenList_date.addAll(tkn.tokenize(Article_date));
                //System.out.println(tokenList_date);

                List<String> tokenList_caption = new ArrayList<String>();
                tokenList_caption.addAll(tkn.tokenize(Image_caption));
                //System.out.println(tokenList_caption);

                csvWriter.writeCSV(tokenList_author, tokenList_heading,
                        tokenList_description, tokenList_date,
                        tokenList_caption);




//                System.out.println("Record No - " + csvRecord.getRecordNumber());
//                System.out.println("---------------");
//                System.out.println("Name : " + name);
//                System.out.println("Email : " + email);
//                System.out.println("Phone : " + phone);
//                System.out.println("Country : " + country);
//                System.out.println("---------------\n\n");
            }


        }
    }
}