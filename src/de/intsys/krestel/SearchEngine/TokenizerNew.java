package de.intsys.krestel.SearchEngine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;


public class TokenizerNew {

    List<String> stopwords = new ArrayList<String>();
    KrovStemmer krovStemmer = new KrovStemmer();

    public List<String> tokenize(String raw_data){
        List<String> tokens = new ArrayList<String>();

        StringTokenizer st = new StringTokenizer(raw_data," ,/?\"\\(\\)\\[\\]:;_!|“”-…");
        initStopWords();
        while(st.hasMoreTokens()){
            String val = st.nextToken();

            if(val.matches("[a-zA-Z]+")){
                if(!checkStopWords(val))
                    tokens.add(krovStemmer.stemToken(val.toLowerCase()));

            }
            else if(val.matches("[0-9]{2}/[0-9]{2}/[0-9]{4}")||val.matches("[0-9]{2}.[0-9]{2}.[0-9]{4}")||
                    val.matches("[0-9]{4}-[0-9]{2}")){//Handles dates and year.
                if(val.contains("."))
                    tokens.add(krovStemmer.stemToken(val.replaceAll(".","/")));
                else
                    tokens.add(krovStemmer.stemToken(val));
            }
            else if(val.contains(".")&&!val.contains("@")){//for abbreviations
                if(val.length()==1){

                }else {
                    String temp_val = val.replaceAll("(?<=(^|[.])[\\S&&\\D])[.](?=[\\S&&\\D]([.]|$))", "").replace('.', ' ').toLowerCase();
                    tokens.add(krovStemmer.stemToken(temp_val.substring(0, temp_val.length() - 1).toLowerCase()));
                }
            }
            else if(val.matches("[a-zA-Z]+['][s]")||val.matches("[a-zA-Z]+[’][s]")&&!val.contains("let")&&!val.contains("Let")){
                String next = st.nextToken().toLowerCase();
                tokens.add(krovStemmer.stemToken(val.toLowerCase()+next));//for possession
                String[] words = removePunctuations(val);
                if(words[0]!=null&&!checkStopWords(words[0].toLowerCase()))
                    tokens.add(krovStemmer.stemToken(words[0]));
                if(next!=null)
                    tokens.add(krovStemmer.stemToken(next.toLowerCase()));

            }
            else if(val.contains("'")){
                String[] words = removePunctuations(val);
                for(int i=0;i<words.length;i++){
                    if(words[i]!=null&&!checkStopWords(words[i]))
                        tokens.add(krovStemmer.stemToken(words[i].toLowerCase()));
                }
            }
            else if(val.contains("‘")){
                tokens.add(krovStemmer.stemToken(val.split("‘")[1].toLowerCase()));
                }
            else if(val.contains("’")){
                tokens.add(krovStemmer.stemToken(val.split("’")[0].toLowerCase()));
            }
            else{
                if(val!=null&&!checkStopWords(val))
                    tokens.add(krovStemmer.stemToken(val.toLowerCase()));
            }
        }
        //System.out.println(tokens);
        return tokens;
    }


    public void initStopWords(){
        stopwords.addAll(Arrays.asList("a","an","and","are","as","at","be","by","for","from","has","he","in","is","it","its","of","on",
                "or","that","the","this","to","vs","was","what","when","where","who","will","with","the","a", "as", "able", "about",
                "above", "according", "accordingly", "across", "actually",
                "after", "afterwards", "again", "against", "aint", "all",
                "allow", "allows", "almost", "alone", "along", "already",
                "also", "although", "always", "am", "among", "amongst", "an",
                "and", "another", "any", "anybody", "anyhow", "anyone", "anything",
                "anyway", "anyways", "anywhere", "apart", "appear", "appreciate",
                "appropriate", "are", "arent", "around", "as", "aside", "ask", "asking",
                "associated", "at", "available", "away", "awfully", "be", "became", "because",
                "become", "becomes", "becoming", "been", "before", "beforehand", "behind", "being",
                "believe", "below", "beside", "besides", "best", "better", "between", "beyond", "both",
                "brief", "but", "by", "cmon", "cs", "came", "can", "cant", "cannot", "cant", "cause", "causes",
                "certain", "certainly", "changes", "clearly", "co", "com", "come",
                "comes", "concerning", "consequently", "consider", "considering", "contain",
                "containing",    "contains","corresponding","could", "couldnt", "course", "currently",
                "definitely", "described", "despite", "did", "didnt", "different", "do", "does",
                "doesnt", "doing", "dont", "done", "down", "downwards", "during", "each", "edu",
                "eg", "eight", "either", "else", "elsewhere", "enough", "entirely", "especially",
                "et", "etc", "even", "ever", "every", "everybody", "everyone", "everything", "everywhere",
                "ex", "exactly", "example", "except", "far", "few", "ff", "fifth", "first", "five", "followed",
                "following", "follows", "for", "former", "formerly", "forth", "four", "from", "further",
                "furthermore", "get", "gets", "getting", "given", "gives", "go", "goes", "going", "gone"
                , "got", "gotten", "greetings", "had", "hadnt", "happens", "hardly", "has", "hasnt", "have",
                "havent", "having", "he", "hes", "hello", "help", "hence", "her", "here", "heres", "hereafter", "hereby", "herein", "hereupon", "hers", "herself", "hi", "him", "himself", "his", "hither", "hopefully", "how", "howbeit", "however", "i", "id", "ill", "im", "ive", "ie", "if", "ignored", "immediate", "in", "inasmuch", "inc", "indeed", "indicate", "indicated", "indicates", "inner", "insofar", "instead", "into", "inward", "is", "isnt", "it", "itd", "itll", "its", "its", "itself", "just", "keep", "keeps", "kept", "know", "knows", "known", "last", "lately", "later", "latter", "latterly", "least", "less", "lest", "let", "lets", "like", "liked", "likely", "little", "look", "looking", "looks", "ltd", "mainly", "many", "may", "maybe", "me", "mean", "meanwhile", "merely", "might", "more", "moreover", "most", "mostly", "much", "must", "my", "myself", "name", "namely", "nd", "near", "nearly", "necessary", "need", "needs", "neither", "never", "nevertheless", "new", "next", "nine", "no", "nobody", "non", "none", "noone", "nor", "normally", "not", "nothing", "novel", "now", "nowhere", "obviously", "of", "off", "often", "oh", "ok", "okay", "old", "on", "once", "one", "ones", "only", "onto", "or", "other", "others", "otherwise", "ought", "our", "ours", "ourselves", "out", "outside", "over", "overall", "own", "particular", "particularly", "per", "perhaps", "placed", "please", "plus", "possible", "presumably", "probably", "provides", "que", "quite", "qv", "rather", "rd", "re", "really", "reasonably", "regarding", "regardless", "regards", "relatively", "respectively", "right", "said", "same", "saw", "say", "saying", "says", "second", "secondly", "see", "seeing", "seem", "seemed", "seeming", "seems", "seen", "self", "selves", "sensible", "sent", "serious", "seriously", "seven", "several", "shall", "she", "should", "shouldnt", "since", "six", "so", "some", "somebody", "somehow", "someone", "something", "sometime", "sometimes", "somewhat", "somewhere", "soon", "sorry", "specified", "specify", "specifying", "still", "sub", "such", "sup", "sure", "ts", "take", "taken", "tell", "tends", "th", "than", "thank", "thanks", "thanx", "that", "thats", "thats", "the", "their", "theirs", "them", "themselves", "then", "thence", "there", "theres", "thereafter", "thereby", "therefore", "therein", "theres", "thereupon", "these", "they", "theyd", "theyll", "theyre", "theyve", "think", "third", "this", "thorough", "thoroughly", "those", "though", "three", "through", "throughout", "thru", "thus", "to", "together", "too", "took", "toward", "towards", "tried", "tries", "truly", "try", "trying", "twice", "two", "un", "under", "unfortunately", "unless", "unlikely", "until", "unto", "up", "upon", "us", "use", "used", "useful", "uses", "using", "usually", "value", "various", "very", "via", "viz", "vs", "want", "wants", "was", "wasnt", "way", "we", "wed", "well", "were", "weve", "welcome", "well", "went", "were", "werent", "what", "whats", "whatever", "when", "whence", "whenever", "where", "wheres", "whereafter", "whereas", "whereby", "wherein", "whereupon", "wherever", "whether", "which", "while", "whither", "who", "whos", "whoever", "whole", "whom", "whose", "why", "will", "willing", "wish", "with", "within", "without", "wont", "wonder", "would", "would", "wouldnt", "yes", "yet", "you", "youd", "youll", "youre", "youve", "your", "yours", "yourself", "yourselves", "zero"
                ,"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"));
    }


    public boolean checkStopWords(String word){
        if(stopwords.contains(word))
            return true;
        else
            return false;
    }


    public String[] removePunctuations(String word){
        String[] words = new String[2];
        if(word.contains("'d")){
            words = word.split("'");
            words[1] = "would";
        }
        else if(word.contains("'ll")){
            words = word.split("'");
            words[1] = "will";
        }
        else if(word.contains("'m")){
            words = word.split("'");
            words[1] = "am";
        }
        else if(word.contains("'nt")){
            words = word.split("'");
            words[1] = "not";
        }
        else if(word.contains("'re")){
            words = word.split("'");
            words[1] = "are";
        }
        else if(word.contains("'s")){//Handles possessives
            words = word.split("'");
            if(words[0].equals("let"))
                words[1] = "us";
        }
        else if(word.contains("'ve")){
            words = word.split("'");
            words[1] = "have";
        }
        return words;
    }

}