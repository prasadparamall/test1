package base;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import io.github.bonigarcia.wdm.WebDriverManager;
import utilities.ExcelReader;
import utilities.ExtentManager;
import utilities.Paths;
import utilities.Utility;

public class BaseClass {

	public static WebDriver driver;
	public static FileInputStream fi;
	public static Properties pro;
	
	public static ExtentReports rep = ExtentManager.getInstance();
	public static ExtentTest test;
	public static Logger log = Logger.getLogger("devpinoyLogger");
	public ExcelReader DemoQA_excel1 = new ExcelReader(Paths.excel);
	public static SoftAssert softassert = new SoftAssert();
	

	public static void setUp() throws Exception {
		fi = new FileInputStream(Paths.config);
		pro = new Properties();
		pro.load(fi);

		if (pro.getProperty("browser").equals("chrome")) {

			WebDriverManager.chromedriver().setup();

			Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("profile.default_content_setting_values.notifications", 2);
			prefs.put("credentials_enable_service", false);
			prefs.put("profile.password_manager_enabled", false);
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("prefs", prefs);
			options.addArguments("--disable-extensions");
			options.addArguments("--disable-infobars");
			options.addArguments("--remote-allow-origins=*");
			

			driver = new ChromeDriver(options);
			log.info("launching chrome broswer");
		
		} else if (pro.getProperty("browser").equals("firefox")) {

			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			log.info("launching firefox broswer");

		} else if (pro.getProperty("browser").equals("ie")) {

			WebDriverManager.iedriver().setup();
			driver = new InternetExplorerDriver();
			log.info("launching internetexplore broswer");
		}

		driver.manage().window().maximize();
		driver.get(pro.getProperty("url"));
		log.info("Open URL from config file");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}
	
	public WebElement locator1(String locatorkey) {
	
		try {
			if (!isElementPresent(locatorkey)) {
				System.out.println("Element not present " + locatorkey);
			}else if (!isElementVisible(locatorkey)) {
				System.out.println("Element not visible " + locatorkey);
			}
			
		}catch(Exception e) {
			System.out.println("");
		}
		
		return driver.findElement(getLocator(locatorkey));
	}
	
	public void locator(String locatorkey) {
		
		try {
			if (!isElementPresent(locatorkey)) {
				System.out.println("Element not present " + locatorkey);
			}
			if (!isElementVisible(locatorkey)) {
				System.out.println("Element not visible " + locatorkey);
			}
			
		}catch(Exception e) {
			System.out.println("");
		}
		
		driver.findElement(getLocator(locatorkey));

		//return driver.findElement(By.xpath(pro.getProperty(locatorkey)));
	}


	public WebElement getElement(String locatorkey) {

		WebElement e = driver.findElement(getLocator(locatorkey));
		return e;
	}

	public List<WebElement> getElements(String locatorkey) {
		
		return driver.findElements(getLocator(locatorkey));

	}
	
	public void clickJS(String locatorkey) {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", getElement(locatorkey));

	}
	
	public void clickJSValue(WebElement value) {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", value);

	}

	public void clear(String locatorkey) {
		getElement(locatorkey).clear();
	}



	static List<WebElement> ite;
	public List<WebElement> iteratorClick(String commanlocatorkey) {
		ite = getElements(commanlocatorkey);

		try {
			Iterator<WebElement> cli = ite.iterator();
			while (cli.hasNext()) {
				cli.next().click();
			}
		} catch (Exception e) {

		}

		return ite;
	}

	static List<WebElement> locats;
	public List<WebElement> iteratorgetText(String commanlocatorkey) {
		locats = getElements(commanlocatorkey);
		ArrayList<String> ls = new ArrayList<String>();
        try {
			Iterator<WebElement> cli2 = locats.iterator();
			
			while (cli2.hasNext()) {
				String ssm = cli2.next().getText();
				ls.add(ssm);
			}
			System.out.println(ls);
			
		} catch (Exception e) {

		}
		return locats;

	}
	
	static List<WebElement> locats1;
	public List<WebElement> iteratorgetText1(String commanlocatorkey) {
		
	locats1 = getElements(commanlocatorkey);
	Iterator<WebElement> dd = locats1.iterator();
	
	while(dd.hasNext()) {
		String cc = dd.next().getText();
		System.out.println(cc);
	}
	return locats1;
}
	
	public void printTable(String locators, String c1, String c2){
		
		List<WebElement> tablerows = getElements(locators);
		String columf = c1;
		String colums = c2;
		String column;
		List<WebElement> tablecolumns;

		for (int i = 1; i < tablerows.size(); i++) {
			column = columf + i + colums;

			tablecolumns = driver.findElements(By.xpath(column));

			for (int j = 0; j < tablecolumns.size(); j++) {
				System.out.print(tablecolumns.get(j).getText() + "  ,  ");
			}
			System.out.println();
		}
	}
	
	public String singlegetText(String locatorkey) {
		String tx = getElement(locatorkey).getText();
		System.out.println(tx);
		return tx;
	}
	
	public Select select(String locatorkey) {
		Select select1 = new Select(getElement(locatorkey));

		return select1;
	}
	
	static WebElement dropdown;
	public void selectVT(String xpath, String value) {
			dropdown = getElement(xpath);
		
		Select select1 = new Select(dropdown);
		select1.selectByVisibleText(value);

	}
	
	public static Robot robot() throws Exception {
		Robot robot1 = new Robot();
		return robot1;
	}

	static WebElement doubluclick;
	public void doubleClick(String locatorkey) {

		doubluclick = getElement(locatorkey);

		Actions actions = new Actions(driver);
		Action action = actions.doubleClick(doubluclick).build();
		action.perform();

	}

	static WebElement rightclick;
	public void rightClick(String locatorkey) {

		rightclick = getElement(locatorkey);

		Actions actions = new Actions(driver);
		Action action = actions.contextClick(rightclick).build();
		action.perform();

	}
	
	static WebElement moveto;
	public void moveToElement(String locatorkey) {

		moveto = driver.findElement(By.xpath(locatorkey));
	
		Actions actions = new Actions(driver);
		Action action = actions.moveToElement(moveto).build();
		action.perform();

	}
	
	static WebElement draganddrop;
	static WebElement elem;
	public void dragAndDrop(String locatorkey, String element) {

		draganddrop = getElement(locatorkey);
		elem = getElement(element);
		
		Actions actions = new Actions(driver);
		Action action = actions.dragAndDrop(draganddrop, elem).build();
		action.perform();

	}

	public void clickAndHold(String locatorkey) {

		draganddrop = getElement(locatorkey);
		
		Actions actions = new Actions(driver);
		Action action = actions.moveToElement(draganddrop).build();
		actions.clickAndHold().perform(); 
		
	}

	public void waitVisibility(String locatorkey) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.visibilityOfElementLocated(getLocator(locatorkey)));
	}
	
	public WebElement waitElementToBeClickable(String locatorkey) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		return wait.until(ExpectedConditions.elementToBeClickable(getLocator(locatorkey)));
	}



	public static void closeAllWindows(List<String> hlist, String pare) {
		for (String e : hlist) {
			if(!e.equals(pare)) {
				driver.switchTo().window(e).close();
			}
		}
	}
	
	public static void switchtoparawin(String pare) {
		driver.switchTo().window(pare);
		
	}
	
	public static boolean switchToWindows(String windowtitle, List<String> hlist) {
		for (String e : hlist) {
			String title = driver.switchTo().window(e).getTitle();
			if (title.contains(windowtitle)) {
				System.out.println("found the right window...");
				return true;
			}
		}

		return false;

	}
	
	
	public static void windowshandle(String main, String child) {
		main = driver.getWindowHandle();
		Set<String> dd = driver.getWindowHandles();
		
		Iterator<String> kk = dd.iterator();
		while(kk.hasNext()) {
			child = kk.next();
			
			if(! main.equalsIgnoreCase(child)) {
				driver.switchTo().window(child);
			}
		}
	}

	
	public static void verifyLinks(String linkUrl) {
		
		//final String redd ="\u001b[31m";
		
		try {
			URL url = new URL(linkUrl);

			// Now we will be creating url connection and getting the response code
			HttpURLConnection httpURLConnect = (HttpURLConnection) url.openConnection();
			httpURLConnect.setConnectTimeout(5000);
			httpURLConnect.connect();
			if (httpURLConnect.getResponseCode() >= 400) {
				System.out.println(linkUrl + " - " + httpURLConnect.getResponseMessage() +" is a broken link" +"\n");
			}

			// Fetching and Printing the response code obtained
			else {
				System.out.println(linkUrl + " - " + httpURLConnect.getResponseMessage());
			}
		} catch (Exception e) {
		}
	}

	public static String[] getMonthYear(String monthYearVal) {
		return monthYearVal.split(" ");
	}

	public static void quitBrowser() {

		driver.quit();

	}

	public static void tear() {
		driver.quit();
	}

	public boolean isElementPresent(String locatorkey) {
		// System.out.println("Checking presence of " + locatorkey);
	
		try {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));	
			wait.until(ExpectedConditions.presenceOfElementLocated(getLocator(locatorkey)));
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean isElementVisible(String locatorkey) {
		// System.out.println("Checking visible of " + locatorkey);
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOfElementLocated(getLocator(locatorkey)));
		} catch (Exception e) {
			return false;
		}
		return true;

	}

	public By getLocator(String locatorkey) {
		By by = null;

		if (locatorkey.endsWith("_id"))
			by = By.id(pro.getProperty(locatorkey));
		else if (locatorkey.endsWith("_xpath"))
			by = By.xpath(pro.getProperty(locatorkey));
		else if (locatorkey.endsWith("_css"))
			by = By.cssSelector(pro.getProperty(locatorkey));
		else if (locatorkey.endsWith("_name"))
			by = By.name(pro.getProperty(locatorkey));
		else if (locatorkey.endsWith("_tagname"))
			by = By.tagName(pro.getProperty(locatorkey));

		return by;
	}

}


