package de.intsys.krestel.SearchEngine;

import org.lemurproject.kstem.KrovetzStemmer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class KrovStemmer{

        KrovetzStemmer stemmer = new KrovetzStemmer();
        List<String> stemList = new ArrayList<>();

        List<String> stemVocabList(Set<String> unStemedList){

            Iterator<String> itr = unStemedList.iterator();

            while (itr.hasNext()){
                String eachStem = stemmer.stem(itr.next());
                stemList.add(eachStem);
            }
            return stemList;
        }

        public String stemToken(String temp_toki){
            String stem = new String();
            stem = stemmer.stem(temp_toki);
            return stem;
        }

    public static void main(String[] args) throws Exception {

        String word = "document";
        KrovetzStemmer stemmer = new KrovetzStemmer();
        String res = stemmer.stem("vases");
        System.out.println(res);




//        PorterStemmer stemmer_port = new PorterStemmer();
//        String res_1 = stemmer_port.stem(word);
//
//        String html = "Bruno The document detailed the areas it said businesses in the capital were most concerned about and which councillors could have an impact on.";
//        System.out.println("Original String: " + html);
//        html = html.replaceAll("[^a-zA-Z0-9\\s+]", "");
//        //System.out.println(html);
//        html = html.toLowerCase();
//        System.out.println("Tokenizer Output:");
//        StringTokenizer st = new StringTokenizer(html, " ");
//        /*while (st.hasMoreElements()) {
//        System.out.println(st.nextElement());
//        }*/
//        System.out.println("Tokenizer + Stemming Output:");
//        while (st.hasMoreElements()) {
//            System.out.println(stemmer.stem(st.nextElement().toString()));
//        }
//        StringTokenizer st1 = new StringTokenizer(html, " ");
//        System.out.println("Tokenizer + Stemming Output:");
//        while (st1.hasMoreElements()) {
//            System.out.println(stemmer_port.stem(st1.nextElement().toString()));
//        }
    }
}




