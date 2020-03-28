package googleCrawler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.ProfilesIni;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.touch.ScrollAction;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.gson.JsonParser;


public class googleCrawler {

	//user settings
	public static String searchWord = "cao dang cao thang"; 
	public static int numOfScroll = 50; //
	public static String posOutput = "pos.txt" ;
	public static String negOutput = "neg.txt";
	public static String neuOutput = "neu.txt";
	//static variables
	public static WebDriver driver;
	public static JsonParser parser = new JsonParser();
	public static int count = 0;
	public static ArrayList<String> postIds = new ArrayList<String>();
	public static ArrayList<String> authorIds = new ArrayList<String>();
	public static void main(String[] args) throws InterruptedException, IOException {
		
		FileWriter posFileWwriter = null;
		FileWriter negFileWwriter = null;
		FileWriter neuFileWwriter = null;
		try {
			posFileWwriter = new FileWriter(posOutput,true);
			negFileWwriter = new FileWriter(negOutput,true);
			neuFileWwriter = new FileWriter(neuOutput,true);

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    BufferedWriter posWriter = new BufferedWriter(posFileWwriter);
	    BufferedWriter negWriter = new BufferedWriter(negFileWwriter);
	    BufferedWriter neuWriter = new BufferedWriter(neuFileWwriter);

		
	System.setProperty("webdriver.gecko.driver", "D:/project-sa-uit/temp/geckodriver-v0.25.0-win64/geckodriver.exe");

	//driver initiate
	//driver = new FirefoxDriver();
	
	ProfilesIni profile = new ProfilesIni();
	FirefoxProfile testprofile = profile.getProfile("newuser");
	testprofile.setPreference("dom.webnotifications.enabled", false);
	testprofile.setPreference("dom.push.enabled", false);
	DesiredCapabilities dc = DesiredCapabilities.firefox();
	dc.setCapability(FirefoxDriver.PROFILE, testprofile);
	FirefoxOptions opt = new FirefoxOptions();
	opt.merge(dc);
	driver =  new FirefoxDriver(opt);
	
	//go to web page
	driver.get("https://www.google.com/");
	 
	//login process
	WebElement searchText = driver.findElement(By.name("q"));
	searchText.sendKeys(searchWord);

	searchText.submit();
	System.out.println(driver.getTitle());
	
	 
	TimeUnit.SECONDS.sleep(2);
	
	WebElement showReview = driver.findElement(By.cssSelector("[data-reply_source='merchant-replies-lu']"));
	showReview.click();
	TimeUnit.SECONDS.sleep(1);
	
	
	JavascriptExecutor js = (JavascriptExecutor)driver;
	WebElement div = driver.findElement(By.className("review-dialog-list"));
	div.click();
	div.sendKeys(Keys.PAGE_DOWN);
	for (int i = 1; i <= numOfScroll + 10; i++) {
		//js.executeScript("$(\"#reviewSort\").animate({ scrollTop: \"450px\" })");
		//((JavascriptExecutor)driver).executeScript("$(\"#reviewSort\").animate({ scrollTop: \"-1000px\" })");
		//js.executeScript("$(\"#reviewSort\").animate({ scrollTop: \"1000px\" })");
		//js.executeScript("scroll(0,500)", div);
		div.sendKeys(Keys.PAGE_DOWN);
		div.sendKeys(Keys.PAGE_DOWN);
		div.sendKeys(Keys.PAGE_DOWN);
		div.sendKeys(Keys.PAGE_DOWN);
		div.sendKeys(Keys.PAGE_DOWN);
		TimeUnit.SECONDS.sleep(1);
	}
	
	List<WebElement> listMoreReviewLinks = driver.findElements(By.className("review-more-link"));
	if (!listMoreReviewLinks.isEmpty()) {
		for (WebElement links : listMoreReviewLinks) {
			TimeUnit.SECONDS.sleep(1);
			if (links.isDisplayed())
				links.click();
		}

		
	}

	
	//List<WebElement> listPosts = driver.findElements(By.className("Jtu6Td"));
	List<WebElement> listPosts = driver.findElements(By.className("gws-localreviews__google-review"));
	ArrayList<String> postList = new ArrayList<String>();
	ArrayList<String> negList = new ArrayList<String>();
	ArrayList<String> neuList = new ArrayList<String>();

	for (WebElement posts : listPosts ) {
		
		if (posts.findElements(By.cssSelector("[aria-label='Được đánh giá 5,0 trên 5,']")).size() != 0) {
			addToList(posts.findElement(By.className("Jtu6Td")).getText(),postList);
		}else if (posts.findElements(By.cssSelector("[aria-label='Được đánh giá 4,0 trên 5,']")).size() != 0) {
			addToList(posts.findElement(By.className("Jtu6Td")).getText(),postList);
		}else if (posts.findElements(By.cssSelector("[aria-label='Được đánh giá 3,0 trên 5,']")).size() != 0) {
			addToList(posts.findElement(By.className("Jtu6Td")).getText(),neuList);
		}else if (posts.findElements(By.cssSelector("[aria-label='Được đánh giá 2,0 trên 5,']")).size() != 0) {
			addToList(posts.findElement(By.className("Jtu6Td")).getText(),negList);
		}else if (posts.findElements(By.cssSelector("[aria-label='Được đánh giá 1,0 trên 5,']")).size() != 0) {
			addToList(posts.findElement(By.className("Jtu6Td")).getText(),negList);
		}
	}
	
	for (String _str : postList) {
		if (_str.isEmpty()) continue;
		posWriter.append(_str); 
		posWriter.append(System.lineSeparator());
	}
	for (String _str : negList) {
		if (_str.isEmpty()) continue;
		negWriter.append(_str); 
		negWriter.append(System.lineSeparator());
	}
	for (String _str : neuList) {
		if (_str.isEmpty()) continue;
		neuWriter.append(_str); 
		neuWriter.append(System.lineSeparator());
	}
	
	System.out.println("Done");
	System.out.println(searchWord);
		posWriter.close();
		negWriter.close();
		neuWriter.close();
		driver.close();

	}
	
	public static ArrayList<String> addToList(String text, ArrayList<String> listString) {
		String temp = "";
		String temp2 = "";
		if (text != null || text.isEmpty() == true) {
			temp = text.replaceAll("\n", " ");
			if (temp.contains("(Dịch bởi Google) ")) {
				temp2 = temp.replaceAll("\\(Dịch bởi Google\\) ", "");	
			}
				
			if (temp2.contains("  (Gốc) "))
				temp = temp2.substring(0, temp2.lastIndexOf("  (Gốc) "));																		
			listString.add(temp);
		}															
		return listString;
	}

}

	
