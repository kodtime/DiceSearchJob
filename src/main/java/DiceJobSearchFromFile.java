import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DiceJobSearchFromFile {
	
	public static void main(String[] args) throws IOException  {
		
		
		
		
		//Set up chrome driver path
		WebDriverManager.chromedriver().setup();
		//invoke selenium webdriver
		WebDriver driver = new ChromeDriver();
		//fullcreen
		driver.manage().window().fullscreen();
		//set universal wait time in case web page is slow
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		/*Step 1. Launch browser and navigate to https://dice.com 
			  Expected: dice home page should be displayed
		*/
		String url = "https://dice.com";
		driver.get(url);
		
		String actualTitle = driver.getTitle();
		String expectedTitle = "Job Search for Technology Professionals | Dice.com";
		
		if(actualTitle.equals(expectedTitle)) {
			System.out.println("Step PASS. Dice homepage successfully loaded");
		}else {
			System.out.println("Step FAIL. Dice homepage did not load successfully");	
			throw new RuntimeException("Step FAIL. Dice homepage did not load successfully");
		}
		
		List<String> searchList=new ArrayList<String>();

		try (FileReader fr = new FileReader("search.txt"); BufferedReader br = new BufferedReader(fr);) {
			String value;
			while ((value = br.readLine()) != null) {
				searchList.add(value);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
		for (int i = 0; i < searchList.size(); i++) {
			
		
		
		String keyword =searchList.get(i);
		System.out.println(keyword);
		
		driver.findElement(By.id("search-field-keyword")).clear();
		driver.findElement(By.id("search-field-keyword")).sendKeys(keyword);
		
		String location = "22102";
		driver.findElement(By.id("search-field-location")).clear();
		driver.findElement(By.id("search-field-location")).sendKeys(location);
		
		driver.findElement(By.id("findTechJobs")).click();
		
		String count = driver.findElement(By.id("posiCountId")).getText();
		System.out.println(count);
		//ensure count is more than 0
		int countResult = Integer.parseInt(count.replace(",", ""));
		
		if(countResult > 0) {
			System.out.println( "Step PASS: Keyword : " + keyword +" search returned " +
			countResult +" results in " + location);
		}else {
			System.out.println( "Step FAIL: Keyword : " + keyword +" search returned " +
					countResult +" results in " + location);
		}
		
		
		driver.navigate().back();
		
		}
		
		driver.close();
		
		
			
		
		
		
		
		
	}
}