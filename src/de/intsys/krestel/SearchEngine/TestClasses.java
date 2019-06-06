package de.intsys.krestel.SearchEngine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.stream.Collectors;

public class TestClasses {

    public static void main(String args[]) throws Exception {


        // Tokenize each row and each column

        TokenizerNew tkn = new TokenizerNew();
        CSVWriter csvWriter = new CSVWriter();
        Scanner scanner = new Scanner(new File("/Users/amitmanbansh/Documents/MES/search engine/Assignment_3/Corpus_1_without_url.csv"));
        List<String> tokenList = new ArrayList<String>();
        while (scanner.hasNextLine())
            tokenList.addAll(tkn.tokenize(scanner.nextLine()));

        ListIterator<String> ltr = tokenList.listIterator();
        int i = 0;
        String temp_toke = new String();
        while (ltr.hasNext()) {
            i++;
            temp_toke = ltr.next();
            if (i<500) {
                System.out.println(temp_toke);
            }

        }
        System.out.println("Number of tokens: "+i);



        // Find Unique word among tokens

        List<String> vocabList = new ArrayList<String>();
        vocabList = tokenList.stream().distinct().collect(Collectors.toList());





        ltr = vocabList.listIterator();
        i = 0;
        String temp_voc = new String();
        while (ltr.hasNext()) {
            i++;
            temp_voc = ltr.next();
            csvWriter.writeVocabCSV(temp_voc);

            if(i<500) {
                System.out.println(temp_voc);
            }


        }
        System.out.println("Number of unique words: "+i);

    }

}

