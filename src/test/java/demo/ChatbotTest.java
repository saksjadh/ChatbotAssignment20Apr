package demo;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class ChatbotTest {

		static WebDriver driver = null;
		public static void main(String[] args) throws InterruptedException {
			// TODO Auto-generated method stub
		launchBrowser();
		openChatbot();
		closeChatbot();
		closeBrowser();
		}
		
		private static void launchBrowser() {
			String rootFolder = System.getProperty("user.dir");
			System.setProperty("webdriver.chrome.driver",rootFolder+"\\src\\test\\resources\\chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--remote-allow-origins=*");

			driver = new ChromeDriver(options);
		    driver.manage().window().maximize();
		    
		    String Url = "http://cgsfdemobucket.s3-website-us-west-2.amazonaws.com/";
		    driver.get(Url);
		    
		    System.out.println("Browser is launched and it redirects to url");
		}
		
		
		private static void openChatbot() throws InterruptedException {
			
			//opening the chatbot by clicking button using xpath
			driver.findElement(By.xpath("//*[@id=\"amazon-connect-chat-widget\"]/div/div[2]/button")).click();
		    
		    WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(40));
		    
		    //Switching to chatbot's frame
		    wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath("//iframe[@name=\"amazon-connect-chat-widget\"]")));
		    System.out.println("Frame found and switched");
		    
		    //waiting till the first incoming message is present in the frame
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class=\"sc-gipzik dRSldq\"]")));    
			Thread.sleep(5000);
//		    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(text(),'Please enter the policy number')])")));

		    //waiting for the chatbox to get visible and sending the policy number
		    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class=\"public-DraftStyleDefault-block public-DraftStyleDefault-ltr\" ]")));
		    
		    Actions actions = new Actions(driver); 
		    actions.moveToElement(driver.findElement(By.xpath("//div[@class=\"public-DraftStyleDefault-block public-DraftStyleDefault-ltr\" ]")));
		    driver.findElement(By.xpath("//div[@class=\"public-DraftStyleDefault-block public-DraftStyleDefault-ltr\" ]")).sendKeys("1111-RAA-14");
		    System.out.println("Policy number sent");
		    driver.findElement(By.xpath("//div[@data-testid=\"chat-send-message-button\"]")).click();
		    
		    
		    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@value='Check policy information']")));
		    WebElement check_policy= driver.findElement(By.xpath("//button[@value='Check policy information']"));
		    check_policy.click();
		    
		    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@value='Address']")));
		    WebElement address= driver.findElement(By.xpath("//button[@value='Address']"));
		    address.click();
		      
		    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@value='Make a claim']")));
		    WebElement make_claim= driver.findElement(By.xpath("//button[@value='Make a claim']"));
		    make_claim.click();   
		    
		}
		
		private static void closeChatbot() {
			WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(40));
			try {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Chat has ended!']")));
			}
			catch (TimeoutException e){
				System.out.println("The Chat Has not Ended");
			}
			finally {
				WebElement lastElement= driver.findElement(By.xpath("//div[@class=\"sc-gipzik gYiKKX\"][last()]"));
				String actual = lastElement.getText();
				String expected = "Chat has ended!";
				Assert.assertEquals(actual, expected,"The chatbot is not working as expected. ");
				
			}
			System.out.println("The chat has ended succesfully");  	
			driver.findElement(By.xpath("//button[@data-testid=\"close-chat-button\"]")).click();
		    
		}
		
		private static void closeBrowser() {
			driver.quit();
		}

	}


