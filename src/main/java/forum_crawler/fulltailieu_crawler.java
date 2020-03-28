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

public class fulltailieu_crawler {
	private static final String url = "https://www.fulltailieu.com/2019/09/11-su-that-o-ai-hoc-ngoai-thuong.html";
	
		//https://forums.voz.vn/showthread.php?t=7136041
	private static final int maxPage = 3;
	private static final int start = 2;
	private HashSet<String> links;
	private static final String continueString = "&page=";
	private static String[] okList = {
			"https://www.fulltailieu.com/2019/09/11-su-that-o-ai-hoc-ngoai-thuong.html",
			"https://www.fulltailieu.com/2019/09/review-mo-ia-chat-humg.html",
			"https://www.fulltailieu.com/2019/09/review-luat-ha-noi-hlu.html",
			"https://www.fulltailieu.com/2019/09/ai-hoc-ngoai-ngu-h-quoc-gia-hn-ulis.html",
			"https://www.fulltailieu.com/2019/08/review-hoc-vien-tai-chinh.html",
			"https://www.fulltailieu.com/2019/08/kinh-te-quoc-dan-neu-suu-tam-nhom-tai.html",
			"https://www.fulltailieu.com/2019/08/bach-khoa-ha-noi-hust-nguon-suu-tam.html",
			"https://www.fulltailieu.com/2019/08/review-ai-hoc-y-khoa-ai-hoc-quoc-gia-hn_10.html",
			"https://www.fulltailieu.com/2019/08/gioi-thieu-ve-ai-hoc-su-pham-ha-noi.html",
			"https://www.fulltailieu.com/2019/08/ai-hoc-ngoai-thuong-ftu-phan-3.html",
			"https://www.fulltailieu.com/2019/08/ai-hoc-ngoai-thuong-ftu-phan-1.html",
			"https://www.fulltailieu.com/2019/08/ai-hoc-ngoai-thuong-ftu-phan-2.html",
			"https://www.fulltailieu.com/2019/08/review-ai-hoc-ha-noi-hanu-phan-2.html",
			"https://www.fulltailieu.com/2019/08/review-ai-hoc-ha-noi-hanu-phan-1.html",
			"https://www.fulltailieu.com/2019/08/review-bach-khoa-nang-dut_10.html",
			"https://www.fulltailieu.com/2019/08/review-ai-hoc-khoa-hoc-xa-hoi-va-nhan.html",
			"https://www.fulltailieu.com/2019/08/review-ai-hoc-y-khoa-ai-hoc-quoc-gia-hn.html",
			"https://www.fulltailieu.com/2019/08/review-hoc-vien-khoa-hoc-quan-su.html",
			"https://www.fulltailieu.com/2019/08/review-hoc-vien-hai-quan.html",
			"https://www.fulltailieu.com/2019/08/review-truong-sy-quan-tang-thiet-giapl.html",
			"https://www.fulltailieu.com/2019/08/review-hoc-vien-bien-phong.html",
			"https://www.fulltailieu.com/2019/08/review-hoc-vien-pkkq.html",
			"https://www.fulltailieu.com/2019/08/review-hoc-vien-hau-can.html",
			"https://www.fulltailieu.com/2019/08/review-bach-khoa-nang-dut.html",
			"https://www.fulltailieu.com/2019/08/review-ai-hoc-mo-hn-hou.html",
			"https://www.fulltailieu.com/2019/08/ai-hoc-ngoai-ngu-h-quoc-gia-hn-ulis.html"
	
	};
	
	public static void main(String[] args) throws IOException {
		ArrayList<String> arrayList = new ArrayList<String>();
		
		  FileWriter fileWwriter = null; String output = System.getProperty("user.dir")
		  + "/out.txt";
		  
		  try { fileWwriter = new FileWriter(output,true); } catch (IOException e1) {
		  e1.printStackTrace(); } BufferedWriter
		  writer = new BufferedWriter(fileWwriter);
		  
		  for (int i = 0; i < okList.length; i++) {
			  getArticles(arrayList, 0, okList[i]);
		  
		  } 
		  for (String _string : arrayList) {
			  writer.append(_string);
			  writer.append(System.lineSeparator()); 
		  }
		  writer.close();
		 
		}
	
	public static void getLinks() throws IOException{
		Document document = null;
		document = Jsoup.connect(url).get();
		ArrayList<String> listURL = new ArrayList<>();		
		Elements elements = document.getElementsByAttributeValueContaining("class", "post hentry");
		for (Element ele : elements) {
			Element temp = ele.select("a").first();
			String tempString = temp.attr("href");
			listURL.add(tempString);
		}
		
		for(String a : listURL) System.out.println(a);
	}

	public static void getArticles(ArrayList<String> arrayList, int index, String url) throws IOException {
		Document document = null;

	    document = Jsoup.connect(url).get();
		Element doc = document.getElementsByAttributeValueContaining("class", "post-body entry-content").first();
		
		doc.removeAttr("img");
		doc.removeAttr("a");
		doc.removeAttr("br");
		
		arrayList.add(doc.text());
		
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
