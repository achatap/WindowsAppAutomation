package winapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.appium.java_client.windows.WindowsDriver;

public class check {

	public static WindowsDriver driver= null;

	@BeforeTest
	public void setup()  {
		DesiredCapabilities cap= new DesiredCapabilities();
		cap.setCapability("app", "C:\\ProgramData\\Microsoft\\Windows\\Start Menu\\Programs\\Firefox.lnk");
		cap.setCapability("platformName", "Windows");
		cap.setCapability("deviceName", "WindowsPC");
		

		try {
			driver = new WindowsDriver(new URL("http://127.0.0.1:4723"), cap);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

	}

	@AfterTest
	public void clean() {
		driver.close();
		driver.quit();
		setup();
	}

	@Test
	public void tin() throws InterruptedException, IOException {
		
		File src =new File("C:\\Users\\achat\\Downloads\\citi.xls");
		
		FileInputStream fis =new FileInputStream(src);
		
		HSSFWorkbook wb = new HSSFWorkbook(fis);
		
		HSSFSheet sh1= wb.getSheetAt(0);
		
		int row= sh1.getLastRowNum()- sh1.getFirstRowNum();
		
		System.out.println(row);
		
		for(int i=1; i< row; i++)
		{
			System.out.println(sh1.getRow(i).getCell(0).getStringCellValue());
			String city= sh1.getRow(i).getCell(0).getStringCellValue();
		
		driver.findElementByName("Search with Google or enter address").clear();;
		driver.findElementByName("Search with Google or enter address").sendKeys("https://tinder.com/app/profile");

		driver.findElementByName("Search with Google or enter address").sendKeys(Keys.ENTER);

		driver.findElementByName("Tinder Plus®").click();

		driver.findElementByName("Current Location").click();

		driver.findElementByName("Search a Location").sendKeys(city);

		driver.findElementByName("Search a Location").sendKeys(Keys.ARROW_DOWN);

		Thread.sleep(2000);

		driver.findElementByName("Search a Location").sendKeys(Keys.ENTER);

		Thread.sleep(2000);

		driver.findElementByName("Add new location").click();

		String txt= "We are unable to find any potential matches right now. Try changing your preferences to see who is nearby";
		
		int count= 0;
		
		while(true) {
			count++;
			try {
				
				try {
					driver.findElementByName("Like").click();
					if(count%10==0) {
						if(driver.findElementByName(txt).isDisplayed()) {
							System.out.println("No result found");
							break;					
					}
				}
				}
				catch (Exception e) {
					continue;
				}

			}
			catch (Exception e) {
				System.out.println(e);
				System.out.println("Unable to like");
				break;
			}

		}

	}
	}

}
