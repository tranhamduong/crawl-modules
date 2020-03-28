package tuoitre_crawler;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class tuoitrevn_crawler {
    private static final int MAX_DEPTH = 2;
    private HashSet<String> links;
    private List<List<String>> articles;



    public tuoitrevn_crawler() {
        links = new HashSet<>();
    }

    public void getPageLinks(String URL, int depth) {
    	if (depth < MAX_DEPTH) {
    		if (URL.contains("cuoituan") || URL.contains("tv") || URL.contains("tuoitrenews") || URL.isEmpty()) {
            	//ignore
            	System.out.println("Ignore links: " + URL);
                }
                else if (URL.contains("https://tuoitre.vn/")) {
                	if ((!links.contains(URL) )) {
                        System.out.println(">> Depth: 0"  + " [" + URL + "]");
                        	try {

                                Document document = Jsoup.connect(URL).get();
                                Elements linksOnPage = document.select("a[href]");

                                depth++;

                                for (Element page : linksOnPage) {
                                	if (links.add(URL)) {
                                		System.out.println(URL);
                                	}
                                    getPageLinks(page.attr("abs:href"),depth);
                                }
                            } catch (IOException e) {
                                System.err.println("For '" + URL + "': " + e.getMessage());
                            }
                		}
                }    
    	}
        
        }
    
    public void getArticles() {
        links.forEach(x -> {
            Document document;
            try {
                document = Jsoup.connect(x).get();
                Elements articleLinks = document.select("h2 a[href^=\"http://www.mkyong.com/\"]");
                for (Element article : articleLinks) {
                    //Only retrieve the titles of the articles that contain Java 8
                    if (article.text().matches("^.*?(Java 8|java 8|JAVA 8).*$")) {
                        //Remove the comment from the line below if you want to see it running on your editor, 
                        //or wait for the File at the end of the execution
                        //System.out.println(article.attr("abs:href"));

                        ArrayList<String> temporary = new ArrayList<>();
                        temporary.add(article.text()); //The title of the article
                        temporary.add(article.attr("abs:href")); //The URL of the article
                        articles.add(temporary);
                    }
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        });
    }
    
    public void writeToFile(String filename) {
        FileWriter writer;
        try {
            writer = new FileWriter(filename);
            articles.forEach(a -> {
                try {
                    String temp = "- Title: " + a.get(0) + " (link: " + a.get(1) + ")\n";
                    //display to console
                    System.out.println(temp);
                    //save to file
                    writer.write(temp);
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            });
            writer.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


    public static void main(String[] args) {
    	System.out.println("Lấy những site thời sự từ tuoitre.vn");

    	tuoitrevn_crawler test = new tuoitrevn_crawler();
        test.getPageLinks("https://tuoitre.vn/thoi-su.htm", 0);
        test.getArticles();
//        test.writeToFile("Hello");
        }
}