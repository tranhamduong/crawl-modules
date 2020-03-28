package facebook_crawler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By.ById;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.ProfilesIni;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class Selenium {

	//user settings
	public static String userId = "..."; //user login ID
	public static String userPw = "..."; //user login password
	public static int limitPostNum = 100; //get posts & comments : less than "limitPostNum"
	public static int startIdx = 0; //set where to start : if 5 then skip 5 post
	//static variables
	public static WebDriver driver;
	public static JsonParser parser = new JsonParser();
	public static int count = 0;
	public static ArrayList<String> postIds = new ArrayList<String>();
	public static ArrayList<String> authorIds = new ArrayList<String>();
	
	public static final String link = "https://www.facebook.com/groups/248571605975931/permalink/493678304798592/";
	
	public static void main(String[] args) throws InterruptedException {
		
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
	driver.get(link);
	 
	//login process
	if (driver.findElements(By.id("email")).size() != 0) {
		WebElement loginID = driver.findElement(By.id("email"));
		loginID.sendKeys(userId);
		WebElement loginPassword = driver.findElement(By.id("pass"));
		loginPassword.sendKeys(userPw);
		loginPassword.submit();
		System.out.println(driver.getTitle());
		
		TimeUnit.SECONDS.sleep(5);
		
		driver.get(link);
		TimeUnit.SECONDS.sleep(1);
	}


	WebElement showComment = driver.findElement(By.cssSelector("[data-testid='UFI2CommentsCount/root']"));
	if (showComment.isDisplayed()) {
		System.out.println("isDisplayed");

		//showComment.click();
	}
	else System.out.println("isNotDisplayed");
	List<WebElement> elements = new ArrayList<WebElement>();
	List<WebElement> elements2 = new ArrayList<WebElement>();
	List<WebElement> showMore = new ArrayList<WebElement>();
	
	do {
		showMore = driver.findElements(By.xpath("//*[contains(text(),'Xem các bình luận trước')]"));
		try {
			for (WebElement ele : showMore) {
				ele.click();
				TimeUnit.SECONDS.sleep(1);
			}
		}catch (Exception e) {
			
		}
	}while (showMore.size() != 0);
	
	//List<WebElement> elements3 = driver.findElements(By.cssSelector("[data-testid='UFI2CommentsList/root_depth_1']"));
	List<WebElement> elements3 = new ArrayList<WebElement>();
	do {
		elements3 = driver.findElements(By.cssSelector("[data-testid='UFI2CommentsPagerRenderer/pager_depth_1']"));
		try {
			for (WebElement ele : elements3) {
				ele.click();
				TimeUnit.SECONDS.sleep(1);
			}
		}catch (Exception e) {
			
		}
	}while (elements3.size() != 0);
	
	for (WebElement ele : elements3) {
		try {
			ele.click();
			TimeUnit.SECONDS.sleep(1);
		}catch (Exception e) {
			
		}
	}
	
	//elements2 = driver.findElements(By.xpath("//div[@data-testid='UFI2Comment/body']/div/span/span/a"));
	elements2 = driver.findElements(By.xpath("//*[contains(text(),'Xem thêm')]"));

	for (WebElement ele : elements2) {
		try {
			ele.click();
			TimeUnit.SECONDS.sleep(1);
		}catch (Exception e) {
			
		}
	}
	
		
	////div[@data-testid='UFI2Comment/body']/div/span/span
	//elements2 = driver.findElements(By.cssSelector("[data-testid='UFI2Comment/body']"));
	
	elements = driver.findElements(By.xpath("//div[@data-testid='UFI2Comment/body']/div/span/span/span"));
	
	System.out.println(elements.size());
	
	for (WebElement ele : elements){
		System.out.println(ele.getText());
	}
	
	//System.out.println(elements);
	System.out.println(elements2);
	if (elements == null)
		System.out.println("null");
	if (elements.isEmpty())
		System.out.println("empty");

	//driver.close();
		/*
		 * try { //List<WebElement> comments = driver.findElements(By.xpath(
		 * "//div[@data-testid='UFI2CommentsList/root_depth_0']")); //List<WebElement>
		 * comments = driver.findElements(By.xpath(xpathExpression)("_19sz"));
		 * 
		 * } catch(Exception e) {e.printStackTrace();} //close browser
		 * 
		 * WebElement post = driver.findElement(By.className("userContentWrapper"));
		 * WebElement postWrap = post.findElement(By.xpath("..")); //xpath parent
		 * WebElement postMeta = post.findElement(By.xpath("..//..")); //xpath
		 * grandparent
		 * 
		 * List<WebElement> seeMores = post.findElements(By.className("see_more_link"));
		 * for(WebElement seeMore : seeMores){ (new
		 * WebDriverWait(driver,30)).until(ExpectedConditions.elementToBeClickable(
		 * seeMore)); seeMore.click(); //append post text }
		 * 
		 * //2. comment List<WebElement> comments =
		 * postWrap.findElements(By.className("UFIPagerRow")); for(WebElement comment :
		 * comments){ //2-1. show entire comments List<WebElement> moreComments =
		 * comment.findElements(By.className("UFIPagerLink")); for(WebElement
		 * moreComment : moreComments){ try{ while(moreComment.isDisplayed()){ (new
		 * WebDriverWait(driver,30)).until(ExpectedConditions.elementToBeClickable(
		 * moreComment)); moreComment.click(); //show entire comments }
		 * }catch(StaleElementReferenceException e){} } } comments =
		 * postWrap.findElements(By.className("UFIPagerRow")); for(WebElement comment :
		 * comments){ //2-2. append comment text List<WebElement> commSeeMores =
		 * comment.findElements(By.linkText("더 보기")); //Caution : login user's
		 * locale(korean) sensitive for(WebElement commSeeMore : commSeeMores){ try{
		 * (new WebDriverWait(driver,30)).until(ExpectedConditions.elementToBeClickable(
		 * commSeeMore)); commSeeMore.click(); //append comment text
		 * }catch(StaleElementReferenceException e){} } }
		 * 
		 * System.out.println(comments);
		 * 
		 * 
		 * driver.close();
		 */
	
		//driver.close();
	}
	//search for start index value
	public static int searchStartIdx(String lastPostId){
	List<WebElement> posts = driver.findElements(By.className("userContentWrapper"));
	//post id
	String postId = "";
	//crawling post start index -> return value
	int startIdx = 0;
	//search for start index value
	for(int i=0; i<posts.size(); i++){
	WebElement post = posts.get(i);
	WebElement postMeta = post.findElement(By.xpath("..//..")); //xpath grandparent
	JsonObject json = (JsonObject)parser.parse(postMeta.getAttribute("data-ft"));
	if(json != null && json.get("id") != null){
	postId = json.get("id").toString();
	}else{
	postId = "";
	}
	//search equal postId to lastPostId
	if(lastPostId == null){ //init case
	}else if(!lastPostId.equals(postId)){ //input not equals to postId
	continue;
	}else{ //input equals to postId : last point index -> +1 is start point index
	startIdx = i+1;
	break;
	}
	}
	return startIdx;
	}
	//get posts & comments
	public static int getContents(int startIdx) throws InterruptedException{
	//1. post
	List<WebElement> posts = driver.findElements(By.className("userContent"));
	//post id
	String postId = "";
	//author id
	String authorId = "";
	//author name
	String authorName = "";
	//crawling post restart index
	//1-1. get posts
	for(int i=startIdx; i<posts.size(); i++){ //start on startIdx post
	WebElement post = posts.get(i);
	WebElement postWrap = post.findElement(By.xpath("..")); //xpath parent
	WebElement postMeta = post.findElement(By.xpath("..//..")); //xpath grandparent
	//1-2. get post data json & extract info
	JsonObject json = (JsonObject)parser.parse(postMeta.getAttribute("data-ft"));
	System.out.println(json);
	//1-6. append post text
	List<WebElement> seeMores = post.findElements(By.className("see_more_link"));
	for(WebElement seeMore : seeMores){
	(new WebDriverWait(driver,30)).until(ExpectedConditions.elementToBeClickable(seeMore));
	seeMore.click(); //append post text
	}
	//2. comment
	List<WebElement> comments = postWrap.findElements(By.className("UFIPagerRow"));
	for(WebElement comment : comments){
	//2-1. show entire comments
	List<WebElement> moreComments = comment.findElements(By.className("UFIPagerLink"));
	for(WebElement moreComment : moreComments){
	try{
	while(moreComment.isDisplayed()){
	(new WebDriverWait(driver,30)).until(ExpectedConditions.elementToBeClickable(moreComment));
	moreComment.click(); //show entire comments
	}
	}catch(StaleElementReferenceException e){}
	}
	}
	comments = postWrap.findElements(By.className("UFIPagerRow"));
	for(WebElement comment : comments){
	//2-2. append comment text
	List<WebElement> commSeeMores = comment.findElements(By.linkText("더 보기")); //Caution : login user's locale(korean) sensitive
	for(WebElement commSeeMore : commSeeMores){
	try{
	(new WebDriverWait(driver,30)).until(ExpectedConditions.elementToBeClickable(commSeeMore));
	commSeeMore.click(); //append comment text
	}catch(StaleElementReferenceException e){}
	}
	}
	//1-7. get posts
	count++;
	System.out.println("<post "+count+", postId : "+postId+", authorId : "+authorId+", authorName : "+authorName+">");
	System.out.println(post.getText());
	//2-3. get comments
	comments = postWrap.findElements(By.className("UFICommentContentBlock"));
	int commIdx = 0;
	for(WebElement comment : comments){
	commIdx++;
	System.out.println("<comments"+commIdx+">");
	System.out.println(comment.getText());
	}
	//3. move scroll down
	Actions actions = new Actions(driver);
	actions.moveToElement(post);
	actions.perform();
	System.out.println("=========================post "+count+" end=======================");
	}
	return posts.size();
	}
}
