package com.makemytrip.utility;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class Browsers extends PageElements{

	
	public WebDriver getBrowser(String browserName, String url){
		
		/*System.out.println("Browser Name : "+browserName);
		System.out.println("Url : "+url);*/

		try{
			if(browserName.equalsIgnoreCase("firefox")){
				System.setProperty("webdriver.gecko.driver", PageElements.mozillaDriverPath);
				PageElements.driver = new FirefoxDriver();
			}else if (browserName.equalsIgnoreCase("chrome")) {
				/*System.out.println("Entered into chrome");
				System.out.println("Chrome Driver Path : "+PageElements.chromeDriverPath);*/
				System.setProperty("webdriver.chrome.driver", PageElements.chromeDriverPath);
				Map<String, Object> prefs = new HashMap<String, Object>();
				prefs.put("profile.default_content_setting_values.notifications", 2);
				ChromeOptions options = new ChromeOptions();
				options.setExperimentalOption("prefs", prefs);
				PageElements.driver = new ChromeDriver(options);				
			}else if (browserName.equalsIgnoreCase("IE")) {
				System.setProperty("webdriver.ie.driver", PageElements.ieDriverPath);
				PageElements.driver = new InternetExplorerDriver();
			}
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.get(url);
		}catch(Exception e){
			System.out.println("[Exception]...Exception Occured While Opening the Browser");
		}

		return driver;
	}
}