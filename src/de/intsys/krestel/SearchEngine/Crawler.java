package de.intsys.krestel.SearchEngine;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Crawler{

    public static void main(String[] args) throws Exception
    {


        // Crawl NewsWebsite Homepage

        WebFile webFile= new WebFile("https://www.thetimes.co.uk/");
        Object webContent = webFile.getContent();
        Document doc = Jsoup.parse(webContent.toString());
        Elements links = doc.select("a[href*=https://www.thetimes.co]");
        Elements DateOnPage = doc.select("time[class^=\"Dateline\"]");
        String DateOfArticle = DateOnPage.text();

        List listA = new ArrayList();   //links to be crawled
        List listB = new ArrayList();   //links crawled
        List listC = new ArrayList();   //links that are of our interest

        int count = 0;                  //Interested Article count


        // Add all the "www.thetimes.co.uk/...." links of Homepage to List "A"

        for (Element link : links){
            listA.add(link.attr("href"));
        }

        while(!listA.isEmpty()){

            // Crawl each link of List "A" if URL not crawled previously.
            // Once crawled add the link to List "B" containing crawled URL lists.
            // Once crawled, delete the URL from Crawl queue list "A".
            // If date matched of Article with Interested Date, write the data into csv file. And add URL to list "C".
            // Add All the "www.thetimes.co.uk/...." links of crawling page to queue list "A".
            // Sleeping for 60 second to avoid getting blocked by website.


            if(!listB.contains(listA.get(0))){

                WebFile webFile_sub = new WebFile(listA.get(0).toString());
                System.out.println("Crawling: "+listA.get(0).toString());
                Object webContent_sub = webFile_sub.getContent();
                Document doc_sub = Jsoup.parse(webContent_sub.toString());
                Elements DateOnPage_sub = doc_sub.select("time[class^=\"Dateline\"]");
                String DateOfArticle_sub = DateOnPage_sub.text();
                Elements links_sub = doc_sub.select("a[href*=https://www.thetimes.co]");

                listB.add(listA.get(0));


                if(DateOfArticle_sub.contains("June 2 2019")){

                    String authors = doc_sub.select("meta[name^=\"author\"]").first().attr("content");
                    String description = doc_sub.select("meta[name*=\"description\"]").first().attr("content");
                    String title = doc_sub.select("meta[name*=\"article:title\"]").first().attr("content");
                    Elements cat = doc_sub.select("li[class^=\"Topics-item\"]");
                    String content_art = doc_sub.select("div[class*=\"Article-content \"]").text();
                    String cat_text = "";
                    count = count+1;
                    for(int l=0;l<cat.size()/2;l++)
                        cat_text = cat_text + cat.get(l).text()+ ", ";

                    writeDataLineByLine("/Users/amitmanbansh/Documents/MES/search engine/Assignment_1/table_30.txt",count,listA.get(0).toString(),authors,title,content_art,DateOfArticle_sub,cat_text);

                    listC.add(listA.get(0));

                    for (Element link_sub : links_sub) {
                        listA.add(0,link_sub.attr("href"));
                    }

                }
                else {
                    for (Element link_sub : links_sub) {
                        listA.add(link_sub.attr("href"));
                    }
                }

                Thread.sleep(60000);
            }

            listA.remove(0);
            Thread.sleep(60000);
        }




    }



    // Function to write info of interested article into CSV file
    //@param: filePath, ArticleId, ArticleUrl, ArticleAuthors, ArticleHeadline, ArticleContent, ArticleTimeStamp


    public static void writeDataLineByLine(String filePath, int ArticleId, String ArticleUrl, String ArticleAuthors, String ArticleHeadline, String ArticleContent, String ArticleTimeStamp, String Category) {
        // first create file object for file placed at location
        // specified by filepath
        File file = new File(filePath);
        try {
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file,true);

            // create CSVWriter object filewriter object as parameter
            BufferedWriter bw = new BufferedWriter(outputfile);
            PrintWriter pw = new PrintWriter(bw);
            pw.println(ArticleId+'~'+ArticleUrl+'~'+ArticleAuthors+'~'+ArticleHeadline+'~'+ArticleContent+'~'+ArticleTimeStamp+'~'+Category);
            pw.flush();
            pw.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

