package Test;

import org.testng.annotations.Test;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import java.io.*;

public class TestCase1 {
	
	WebDriver driver = null;
	public static boolean keepAlive = true;
	public static long purgeInterval = 10; // in milliseconds
    public static long implicitlyWait = 20; // in seconds
	String driverPath = "C:\\Users\\Dell\\Desktop\\Drivers\\Gecko driver\\geckodriver-v0.24.0-win64\\";
	
	// Selenium-TestNG Suite Initialization
    @BeforeSuite
    public void suiteSetup() {
        System.out.println("suiteSetup");
        //System.setProperty("webdriver.gecko.driver", driverPath + "geckodriver.exe");
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Dell\\Desktop\\Drivers\\Chrome\\chromedriver_win32\\chromedriver.exe");
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("marionette", true);
       // driver = new FirefoxDriver(capabilities);
        //driver = new FirefoxDriver();
       driver = new ChromeDriver();
        startMonitor();
    
    }

    // Selenium-TestNG Suite cleanup
    @AfterSuite
    public void suiteTeardown() {
        System.out.println("suiteTeardown");
        driver.close();
        driver.quit();
    }
    
    @BeforeMethod
    public void beforeTest() throws InterruptedException {
        System.out.println("Open Browser");
        driver.get("http:www.linkedIn.com");
        Thread.sleep(1000);
        driver.manage().window().maximize();
        System.out.println("exit from openBrowser()");
    }
    
    @AfterMethod
    public void afterTest() {
        // Intentionally left blank.
    }
    
    @Test
    public void login() throws InterruptedException, IOException{
    	
    	//if(2>3)
    	//{
    		
    	//}
    	driver.manage().timeouts().implicitlyWait(implicitlyWait, TimeUnit.SECONDS);
    	WebElement signinbtn = driver.findElement(By.linkText("Sign in"));
    	signinbtn.click();
    	
    	
    	WebElement usernametxt = driver.findElement(By.id("username"));
    	usernametxt.clear();
    	usernametxt.sendKeys("amanshaw2492@gmail.com");
    	
    	WebElement passwordtxt = driver.findElement(By.id("password"));
    	passwordtxt.clear();
    	passwordtxt.sendKeys("@Man2492");
    	
    	WebElement signinbtn2 = driver.findElement(By.xpath("//div/button[text()='Sign in']"));
    	signinbtn2.click();
    	//Thread.sleep(10000);
    	
    }
    
    @Test
    public void jobsearch() throws InterruptedException, IOException{
    	    	
    	String actualTitle = driver.getTitle();
    	String expectedTitle = "business analyst Jobs in Singapore | LinkedIn";
    	if(actualTitle.equalsIgnoreCase(expectedTitle))
    	{
    	System.out.println("Title Matched");
    	   
    	WebElement jobslnk = driver.findElement(By.xpath("//li[@id='jobs-nav-item']/a"));
    	jobslnk.click();
    	driver.get("https://www.linkedin.com/jobs/");
    	WebElement seachtxt = driver.findElement(By.xpath("//input[@placeholder='Search jobs']"));
    	seachtxt.clear();
    	seachtxt.sendKeys("Software Testing");
    	
    	
    	WebElement locationtxt = driver.findElement(By.xpath("//input[@placeholder='Search location']"));
    	locationtxt.clear();
    	locationtxt.sendKeys("Singapore");
    	
    	WebElement searchbtn = driver.findElement(By.xpath("//button[text()='Search']"));
    	searchbtn.click();
    	}
    	else
    	{
       	 System.out.println("Title didn't match");
    }
	
    	
    }
    
    //fetching company names of top 5 jobs available for the condition given 
    @Test
    public void top5jobs() throws InterruptedException, IOException{
    	int i;
    	
    	File file = new File("C:\\Users\\Dell\\eclipse-workspace\\DataDrivenTestSuite\\Resource\\TestData.xlsx");
    	//creating a workbook
    	XSSFWorkbook wb = new XSSFWorkbook();
    	
    	//creating a sheet
    	XSSFSheet shh = wb.createSheet("Top_5_jobs");
    	
    	for(i=0;i<=5;i++)
    	{
    		WebElement companytxt = driver.findElement(By.xpath("//button[@id='ember983']"));
    		companytxt.click();
    		WebElement companynametxt = driver.findElement(By.xpath("//h4[@class='job-card-search__company-name t-14 t-black artdeco-entity-lockup__subtitle ember-view']["+i+"]"));
    	String company_name = companynametxt.getText();
    	shh.createRow(i).createCell(0).setCellValue(company_name);
    	
    	try {
    	FileOutputStream fos = new FileOutputStream(file);
    	 wb.write(fos);
    	}
    	 catch (Exception e) {
    		 e.printStackTrace();
    	 }
    	}
    	i=i+1;	
    }
    
    
    
    // Method to remove alerts from the web page
    public void purgeAllAlerts() {	
        try {
            Thread.sleep(purgeInterval);
            Alert alert = driver.switchTo().alert();
            if (alert != null)
                alert.dismiss();
        } catch (Exception ex) {
            // Intentionally left blank.
        }
    }

//Method to start background thread for removing alerts
public void startMonitor() {
    System.out.println("enter into AlertMonitor().");
    keepAlive = true;
    Thread t = new Thread(new Runnable() {
        public void run() {
            for (;;) {
                purgeAllAlerts();
                if (!keepAlive)
                    break;
            }
            System.out.println("exit from AlertMonitor() thread.");
        }
    });

    t.start();
    System.out.println("exit from AlertMonitor().");
}

// Method to stop alert monitor thread
public void stopMonitor() {
    keepAlive = false;
	}
}
