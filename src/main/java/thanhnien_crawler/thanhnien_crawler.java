package thanhnien_crawler;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class thanhnien_crawler {
    private static final int MAX_DEPTH = 2;
    private static HashSet<String> links;
    private List<List<String>> articles;
    
    private static String category;



    public thanhnien_crawler() {
        links = new HashSet<>();
    }

    public void getPageLinks(String URL, int depth) {
    	if (depth < MAX_DEPTH) {
    		if ((URL.isEmpty() || !URL.contains("thanhnien.vn")) && URL.contains("video.thanhnien.vn")) {
    		}
    		else {
    			try {
    				
    				String[] splitedLinks = URL.split("/");
    				
    				try {
        				if (splitedLinks[3].equals(category)) {
                				Document document = Jsoup.connect(URL).get();
                				//headless browser
                				Elements linksOnPage = document.select("a[href]");

                				depth++;
                				
            						for (Element page : linksOnPage) {
                    					if (links.add(URL)) {
                    					}                 					
                                        getPageLinks(page.attr("abs:href"),depth);
                					}
        						}
    					}
    				catch (IndexOutOfBoundsException e) {
    		            //System.err.println(e.getMessage());
    				}
    			}catch (IOException e) {
    	            //System.err.println(e.getMessage());
    			}
    		}
    	}
        
    }
    
    
    public void getArticles() {
        Document document = null;
        for (String documentLink : links) {
        	try {
				document = Jsoup.connect(documentLink).get();
				
				//System.out.println(documentLink);
				
                
                Element title = document.getElementsByClass("details__headline").first();
                if (title == null) {
                	//System.out.println("Đường dẫn sai! ");
                	//System.out.println("====================================================================================================");
                	continue;
                }else 
                {
                    System.out.println("Title: " + title.text());
                    
                    Element description = document.getElementById("chapeau");
                    System.out.println("Description: " + description.text());
                    
                    Element thumpnail1 = document.getElementById("contentAvatar");
                    String thumpnailLink = "na";
                    if (thumpnail1 != null)
                    	thumpnailLink = thumpnail1.select("img").attr("src");
                    System.out.println("Hình ảnh thumpnail: " + thumpnailLink);
                    
                    Element content = document.getElementById("abody");
                    if (content.select("div.details__morenews").first() != null)
                    	content.select("div.details__morenews").remove();
//                    if (content.select("article.story").first() != null)
//                    	content.select("article.story").remove();
                    
                    System.out.println("Content: " + content);
                    
                                      


                    
                    /*
                     * Somehow save the data here!
                     * */
                    System.out.println("====================================================================================================");
                }

				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        }
    }


    public static void main(String[] args) {
    	//System.out.println("Lấy những site thời sự từ thanhnien.vn");
    	String URL = "https://thanhnien.vn/cong-nghe/";
    	//System.out.println(URL);

    	String[] splitedURL = URL.split("/");

    	category = splitedURL[3];
    	thanhnien_crawler test = new thanhnien_crawler();
        test.getPageLinks(URL, 0);
        
        //int i = 1;
        //System.out.println("ALL LINK ===========================================================================================");
        //for (String hello : links) {
        //	System.out.println(i + "./ " + hello + "\n");
        //	i++;
        //}
        //System.out.println("ALL LINK ===========================================================================================");

        test.getArticles();
        }
}