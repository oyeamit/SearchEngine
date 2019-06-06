package de.intsys.krestel.SearchEngine;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class testDictNew {

    public static void main(String[] args) throws Exception{


        GlobalVari globI = new GlobalVari();



        HashMap<String, HashMap<Integer, HashMap<Integer, List<Integer>>>> voc_post_dict = new HashMap<>();




        String TOKEN_CSV_FILE = "/Users/amitmanbansh/Documents/MES/search engine/Assignment_3/test_csv_corpus.csv";
        String VOCAB_CSV_FILE = "/Users/amitmanbansh/Documents/MES/search engine/Assignment_3/test_csv_corpus_vocab.csv";
        try (
                Reader reader = Files.newBufferedReader(Paths.get(VOCAB_CSV_FILE));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
        ){
            for (CSVRecord csvRecord : csvParser){
                String vocab_item = csvRecord.get(0);
                HashMap<Integer, HashMap<Integer, List<Integer>>> doc_dict = new HashMap<>();
                try (
                        Reader reader1 = Files.newBufferedReader(Paths.get(TOKEN_CSV_FILE));
                        CSVParser csvParser1 = new CSVParser(reader1, CSVFormat.DEFAULT);
                )
                {
                    int i = 0;  // track of article number
                    for (CSVRecord csvRecord1 : csvParser1) {
                        globI.setGlobalVari(i);
                        String Article_author = csvRecord1.get(0);
                        String Article_heading = csvRecord1.get(1);
                        String Article_description = csvRecord1.get(2);
                        String Article_date = csvRecord1.get(3);
                        String Image_caption = csvRecord1.get(4);

                        String Article_author_1 = Article_author.substring(1,Article_author.length()-1);
                        String Article_heading_1 = Article_heading.substring(1,Article_heading.length()-1);
                        String Article_description_1 = Article_description.substring(1,Article_description.length()-1);
                        String Article_date_1 = Article_date.substring(1,Article_date.length()-1);
                        String Image_caption_1 = Image_caption.substring(1,Image_caption.length()-1);

                        List<String> Article_author_1_list = new ArrayList<String>(Arrays.asList(Article_author_1.split(", ")));
                        List<String> Article_heading_1_list = new ArrayList<String>(Arrays.asList(Article_heading_1.split(", ")));
                        List<String> Article_description_list = new ArrayList<String>(Arrays.asList(Article_description_1.split(", ")));
                        List<String> Article_date_list = new ArrayList<String>(Arrays.asList(Article_date_1.split(", ")));
                        List<String> Image_caption_1_list = new ArrayList<String>(Arrays.asList(Image_caption_1.split(", ")));


                        HashMap<Integer, List<Integer>> posting = new HashMap<>();



                        List<Integer> ind_Article_author_1_list = new ArrayList<>();
                        for(int ind=0; ind < Article_author_1_list.size(); ind++){
                            if(Article_author_1_list.get(ind).equals(vocab_item)){
                                ind_Article_author_1_list.add(ind);
                            }
                        }
                        if(!ind_Article_author_1_list.isEmpty()){
                            posting.put(0, ind_Article_author_1_list);

                        }





                        List<Integer> ind_Article_heading_1_list = new ArrayList<>();
                        for(int ind=0; ind < Article_heading_1_list.size(); ind++){
                            if(Article_heading_1_list.get(ind).equals(vocab_item)){
                                ind_Article_heading_1_list.add(ind);
                            }
                        }
                        if(!ind_Article_heading_1_list.isEmpty()){
                            posting.put(1, ind_Article_heading_1_list);
                        }


                        List<Integer> ind_Article_description_list = new ArrayList<>();
                        for(int ind=0; ind < Article_description_list.size(); ind++){
                            if(Article_description_list.get(ind).equals(vocab_item)){
                                ind_Article_description_list.add(ind);
                            }
                        }
                        if(!ind_Article_description_list.isEmpty()){
                            posting.put(2, ind_Article_description_list);
                        }


                        List<Integer> ind_Article_date_list = new ArrayList<>();
                        for(int ind=0; ind < Article_date_list.size(); ind++){
                            if(Article_date_list.get(ind).equals(vocab_item)){
                                ind_Article_date_list.add(ind);
                            }
                        }
                        if(!ind_Article_date_list.isEmpty()){
                            posting.put(3, ind_Article_date_list);
                        }


                        List<Integer> ind_Image_caption_1_list = new ArrayList<>();
                        for(int ind=0; ind < Image_caption_1_list.size(); ind++){
                            if(Image_caption_1_list.get(ind).equals(vocab_item)){
                                ind_Image_caption_1_list.add(ind);
                            }
                        }
                        if(!ind_Image_caption_1_list.isEmpty()){
                            posting.put(4, ind_Image_caption_1_list);

                        }

                        if(!posting.isEmpty()){
                            doc_dict.put(globI.getGlobalVari(),posting);
                        }



                        i++;
                    }



                }
                if(!doc_dict.isEmpty()){
                    voc_post_dict.put(vocab_item,doc_dict);
                }



            }
        }




        for (HashMap.Entry<String, HashMap<Integer, HashMap<Integer, List<Integer>>>> voca_post : voc_post_dict.entrySet()){
            String temp_vocab = voca_post.getKey();
            System.out.print(temp_vocab+" : { ");

            for (HashMap.Entry<Integer, HashMap<Integer, List<Integer>>> doc_dict_itr : voca_post.getValue().entrySet() ){
                Integer doc_id = doc_dict_itr.getKey();
                System.out.print(doc_id+" : <");
                for (HashMap.Entry<Integer, List<Integer>> col_post_itr : doc_dict_itr.getValue().entrySet()){
                    Integer col_num = col_post_itr.getKey();
                    System.out.print(col_num+" :[");
                    List<Integer> post_list = col_post_itr.getValue();
                    for (int i=0; i < post_list.size(); i++){
                        System.out.print(" "+post_list.get(i)+" ");
                    }
                    System.out.print("]|,|");
                }
                System.out.print("} ");
            }
            System.out.println();

        }

    }
}

