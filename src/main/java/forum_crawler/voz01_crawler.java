package forum_crawler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

public class voz01_crawler {
	private static final String url = "https://forums.voz.vn/showthread.php?t=5771925";
	
		//https://forums.voz.vn/showthread.php?t=7136041
	private static final int maxPage = 3;
	private static final int start = 2;
	private HashSet<String> links;
	private static final String continueString = "&page=";
	
	public static void main(String[] args) throws IOException {
		getArticles();
	}

	public static void getArticles() throws IOException {
		String output = System.getProperty("user.dir") + "/out.txt";

		Document document = null;
		FileWriter fileWwriter = null;
		try {
			fileWwriter = new FileWriter(output,true);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    BufferedWriter writer = new BufferedWriter(fileWwriter);

		
		try {
			document = Jsoup.connect(url).get();	
			
			Elements elements = document.getElementsByAttributeValueContaining("class", "voz-post-message");
			//		System.out.println(elements);
			
			ArrayList<String> listString = new ArrayList<>();

			for (Element post : elements) {

				post.removeAttr("pre");
				post.removeAttr("img");
				post.removeAttr("br");
				post.select("table").remove();

				listString.add(post.text());

			}
			
			ArrayList<String> posts = new ArrayList<String>();
			for (String _string : listString) {
				try {
					String tempS = _string.replaceAll("Quote: ", "");
					 tempS = _string.replaceAll("Quote:", "");
					 if (tempS.contains("Sent from")) {
						 int tempNum = tempS.lastIndexOf("Sent From");
						 tempS = tempS.substring(0, tempNum);
					 }
					 else if (tempS.contains("Gửi từ ")){
						 int tempNum = tempS.lastIndexOf("Gửi từ ");
						 tempS = tempS.substring(0, tempNum);
					 }
					 else if (tempS.contains("Gửi bằng ")){
						 int tempNum = tempS.lastIndexOf("Gửi bằng ");
						 tempS = tempS.substring(0, tempNum);
					 }
					posts.add(removeUrl(tempS));
					
				}catch(Exception e) {}
			}
			
			for (String _string : posts) {
			    writer.append(_string);
			    writer.append(System.lineSeparator()); 
			}
			
			
			
		} catch(Exception e) {e.printStackTrace();}
		
		  for (int i = start; i <=maxPage; i++) { try { TimeUnit.SECONDS.sleep(2);
		  document = Jsoup.connect(url + continueString + i).get();
		  
		  Elements elements
		  = document.getElementsByAttributeValueContaining("class",
		  "voz-post-message"); // System.out.println(elements);
		  
		  ArrayList<String> listString = new ArrayList<>();
		  
		  for (Element post : elements) { 
		  post.removeAttr("pre");
		  post.removeAttr("img"); post.removeAttr("br"); 
		  post.select("table").remove();

		  listString.add(post.text()); }
		  
		  ArrayList<String> posts = new ArrayList<String>(); 
		  for (String _string : listString) { 
			  try { 
					String tempS = _string.replaceAll("Quote: ", "");
					 tempS = _string.replaceAll("Quote:", "");
					 if (tempS.contains("Sent from")) {
						 int tempNum = tempS.lastIndexOf("Gửi từ ");
						 tempS = tempS.substring(0, tempNum);
					 }
					 else if (tempS.contains("Gửi từ ")){
						 int tempNum = tempS.lastIndexOf("Gửi từ ");
						 tempS = tempS.substring(0, tempNum);
					 }
						posts.add(removeUrl(tempS));
			  }catch(Exception e) {} }
		  
		  for (String str : posts) { writer.append(str);
		  writer.append(System.lineSeparator()); }
		  
		  System.out.println(i);
		  
		  
		  } catch(Exception e) {e.printStackTrace();}}
		 
		
	    writer.close();

		
	}
	
	private static String removeUrl(String commentstr)
    {
        String urlPattern = "((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern p = Pattern.compile(urlPattern,Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(commentstr);
        int i = 0;
        while (m.find()) {
            commentstr = commentstr.replaceAll(m.group(i),"").trim();
            i++;
        }
        return commentstr;
    }
	
	public static void whenAppendStringUsingBufferedWritter_thenOldContentShouldExistToo(String output, ArrayList<String> input) 
			  throws IOException {


			}
}
