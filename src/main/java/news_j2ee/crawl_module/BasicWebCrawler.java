package news_j2ee.crawl_module;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class BasicWebCrawler {

    private HashSet<String> links;

    private static final String URL = "http://kenh14.vn/nsut-my-uyen-oc-thanh-van-va-nhieu-dong-nghiep-den-vieng-dam-tang-co-nghe-si-le-binh-20190501160532078.chn";
    public BasicWebCrawler() {
        links = new HashSet<String>();
    }

    public void getPageLinks(String URL) {
        //4. Check if you have already crawled the URLs 
        //(we are intentionally not checking for duplicate content in this example)
        if (!links.contains(URL)) {
            try {
                //4. (i) If not add it to the index
                if (links.add(URL)) {
                    System.out.println(URL);
                }

                //2. Fetch the HTML code
                Document document = Jsoup.connect(URL).get();
                //3. Parse the HTML to extract links to other URLs
//              Elements linksOnPage = document.select("a[href]");
                Elements form = document.getElementsByClass("knc-content");
                
//
//                //5. For each extracted URL... go back to Step 4.
//                for (Element page : linksOnPage) {
//                    getPageLinks(page.attr("abs:href"));
                
//                String title = document.title();      
                System.out.println("Content : " + form);

            
            } catch (IOException e) {
                System.err.println("For '" + URL + "': " + e.getMessage());
            }
        }
    }

//    public static void main(String[] args) {
//        //1. Pick a URL from the frontier
//        new BasicWebCrawler().getPageLinks(URL);
//    }
}
