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

public class thanhnien_crawler_backup {
    private static final int MAX_DEPTH = 2;
    private static HashSet<String> links;
    private List<List<String>> articles;
    
    private static String category;



    public thanhnien_crawler_backup() {
        links = new HashSet<>();
    }

    public void getPageLinks(String URL, int depth) {
//    	if (depth < MAX_DEPTH) {
//    		if (URL.contains("cuoituan") || URL.contains("tv") || URL.contains("tuoitrenews") || URL.isEmpty()) {
//            	//ignore
//            	System.out.println("Ignore links: " + URL);
//                }
//                else if (URL.contains("https://tuoitre.vn/")) {
//                	if ((!links.contains(URL) )) {
//                        System.out.println(">> Depth: 0"  + " [" + URL + "]");
//                        	try {
//
//                                Document document = Jsoup.connect(URL).get();
//                                Elements linksOnPage = document.select("a[href]");
//
//                                depth++;
//
//                                for (Element page : linksOnPage) {
//                                	if (links.add(URL)) {
//                                		System.out.println(URL);
//                                	}
//                                    getPageLinks(page.attr("abs:href"),depth);
//                                }
//                            } catch (IOException e) {
//                                System.err.println("For '" + URL + "': " + e.getMessage());
//                            }
//                		}
//                }    
//    		}
    	
    	if (depth < MAX_DEPTH) {
    		if ((URL.isEmpty() || !URL.contains("thanhnien.vn")) && URL.contains("video.thanhnien.vn")) {
    		}
    		else {
    			try {
    				
    				String[] splitedLinks = URL.split("/");
    				
    				try {
        				if (splitedLinks[3].equals(category)) {
                				Document document = Jsoup.connect(URL).get();
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
    		            System.err.println(e.getMessage());
    				}
    			}catch (IOException e) {
    	            System.err.println(e.getMessage());
    			}
    		}
    	}
        
        }
    
    public void getArticles() {
//        links.forEach(x -> {
//            Document document;
//            try {
//                document = Jsoup.connect(x).get();
//                Elements articleLinks = document.select("h2 a[href^=\"https://thanhnien.vn/thoi-su/\"]");
//                for (Element article : articleLinks) {
//                    //Only retrieve the titles of the articles that contain Java 8
////                    if (article.text().matches("^.*?(Java 8|java 8|JAVA 8).*$")) {
////                        //Remove the comment from the line below if you want to see it running on your editor, 
////                        //or wait for the File at the end of the execution
////                        //System.out.println(article.attr("abs:href"));
////
////                        ArrayList<String> temporary = new ArrayList<>();
////                        temporary.add(article.text()); //The title of the article
////                        temporary.add(article.attr("abs:href")); //The URL of the article
////                        articles.add(temporary);
////                        
//
////                    }
//                    
//                }
//            } catch (IOException e) {
//                System.err.println(e.getMessage());
//            }
//        });
        
        Document document = null;
        for (String documentLink : links) {
        	try {
				document = Jsoup.connect(documentLink).get();
				
				System.out.println(documentLink);
				
                
                Element title = document.getElementsByClass("details__headline").first();
                if (title == null) {
                	System.out.println("Đường dẫn sai! ");
                	System.out.println("====================================================================================================");
                	continue;
                }else 
                {
                	//lưu dữ liệu titile xuống ở đây
                    System.out.println("Title: " + title.text());
                    
                    // Lưu dữ liệu description text xuống ở đây
                    Element description = document.getElementById("chapeau");
                    System.out.println("Description: " + description.text());
                    

                    
                    Element content = document.getElementById("abody");
                    content.select("div.details__morenews").remove();
                    // luu du lieu content xuong o day
                    System.out.println("Content: " + content);
                    
                    Element thumpnail1 = document.getElementById("contentAvatar");
                    String thumpnailLink = "na";
                    if (thumpnail1 != null)
                    	thumpnailLink = thumpnail1.select("img").attr("src");
                    System.out.println("HÌnh ảnh thumpnail: " + thumpnailLink);
                    
                    System.out.println("====================================================================================================");
                }
                
                

//                Elements b = thumpnail1.siblingElements();
//                
//                Element c = b.select("div").first();
//                System.out.println(c);
//                System.out.println(b.attr("src"));
//                System.out.println();
//                Element thumpnail = document.getElementsByTag(".img").first();
//                System.out.println(thumpnail.attr("src"));
//                String thumpnailLink = thumpnail.attr("src");

				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        	}
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
    	System.out.println("Lấy những site thời sự từ thanhnien.vn");
    	String URL = "https://thanhnien.vn/thoi-su/";
    	System.out.println(URL);

    	String[] splitedURL = URL.split("/");

    	category = splitedURL[3];
    	thanhnien_crawler_backup test = new thanhnien_crawler_backup();
        test.getPageLinks(URL, 0);
        
        int i = 1;
        System.out.println("ALL LINK ===========================================================================================");
        for (String hello : links) {
        	System.out.println(i + "./ " + hello + "\n");
        	i++;
        }
        test.getArticles();
//        test.writeToFile("Hello");
        }
}