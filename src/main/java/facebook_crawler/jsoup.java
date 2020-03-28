package facebook_crawler;

import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class jsoup {

    public static void main(String[] args) {
        try {
            //In this url you must login
            String loginUrl = "https://www.facebook.com";

            //This is an example, it can be anything else
            String url = "https://www.facebook.com/groups/khoacongnghephanmemuit/permalink/3081600975198672/";

            //First login. Take the cookies
            Connection.Response res = Jsoup
                    .connect(loginUrl)
                    .data("email", "...")
                    .data("pass", "...")
                    .referrer("http://www.google.com")
                    .userAgent( "Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                    .method(Method.POST).timeout(0).execute();

            Map<String, String> loginCookies = res.cookies();

            //Now you can parse any page you want, as long as you pass the cookies
            Document doc = Jsoup
                    .connect(url)
                    .timeout(0)
                    .cookies(loginCookies)
                    .referrer("http://www.google.com")
                    .userAgent(
                            "Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                    .get();
            System.out.println("Title : " + doc.title());
            
            Elements elements = doc.getElementsByAttributeValueContaining("data-testid", "UFI2Comment/body");
            //System.out.println(elements.text());
            Elements elements2 = doc.getAllElements();
            System.out.println(doc);

            System.out.println("checkpoint");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}