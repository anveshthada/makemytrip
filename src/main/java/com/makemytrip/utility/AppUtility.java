package com.makemytrip.utility;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;

public class AppUtility extends PageElements{

	Browsers browser = new Browsers();
	UtilityMethods utilMethods = new UtilityMethods();



	public void selectDate(String fullDate){
		String[] dep_date = fullDate.split("-");
		String date = dep_date[0];
		String month = dep_date[1];
		String year = dep_date[2];
		driver.findElement(By.xpath("//span[contains(text(),'"+month+"')]"));
		List<WebElement> date_Options = driver.findElements(By.xpath("//span[contains(text(),'"+month+"')]//following::table[contains(@class,'ui-datepicker-calendar')]//tr//td//a"));
		ListIterator<WebElement> itr = date_Options.listIterator();
		while(itr.hasNext()){
			String date1 = itr.next().getText();
			if(date1.contentEquals(date)){
				itr.previous().click();
				break;
			}
		}
	}

	
	public void openApplication() {
		try{
			utilMethods.loadConfiguration();
			browser.getBrowser(testBrowser, appUrl);
			UtilityMethods.capctureScreenShot(driver, "Url", "Browser And Application Opened Successfully");
		}catch (Exception e) {
			//e.printStackTrace();
			System.out.println("[Exception].... Exception Occurred while opening application");
			System.out.println(e);
		}
	}

	public void registerUser(String excelFile, String excelSheet){
		String email,mobile,password;
		openApplication();
		try{
			// wait and click on sign up link
			WebElement signupLink = driver.findElement(By.xpath(PageElements.signUpLink));
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.visibilityOf(signupLink)).click();
			// read and enter the details
			email = UtilityMethods.readExcelCellData(excelFile, excelSheet, "Registration-1", "Email");
			mobile = UtilityMethods.readExcelCellData(excelFile, excelSheet, "Registration-1", "Mobile");
			password = UtilityMethods.readExcelCellData(excelFile, excelSheet, "Registration-1", "Password");
			
			wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(PageElements.email)))).click();
			driver.findElement(By.xpath(PageElements.email)).sendKeys(email);
			driver.findElement(By.xpath(PageElements.mobile)).click();
			driver.findElement(By.xpath(PageElements.mobile)).clear();
			driver.findElement(By.xpath(PageElements.mobile)).sendKeys(mobile);
			driver.findElement(By.xpath(PageElements.password)).click();
			driver.findElement(By.xpath(PageElements.password)).sendKeys(password);

			// Capture screen
			UtilityMethods.capctureScreenShot(driver, "Registration Details", "Registration Details Filled");
			
			// click on sign up button
			driver.findElement(By.xpath(PageElements.signUpButton)).click();
			// code to check for errors
			if(driver.findElement(By.xpath(PageElements.err_Email)).isDisplayed() == true){
				UtilityMethods.capctureScreenShot(driver, "Reg_Email_Error", "Email Error : "+driver.findElement(By.xpath(PageElements.err_Email)).getText());
				UtilityMethods.generatePdfReport("Registration - Failure", new Font(FontFamily.COURIER,25.0f,Font.BOLD,BaseColor.RED));
				driver.close();
			}else if(driver.findElement(By.xpath(PageElements.err_Mobile)).isDisplayed() == true){
				UtilityMethods.capctureScreenShot(driver, "Reg_Mobile_Error", "Mobile Error : "+driver.findElement(By.xpath(PageElements.err_Mobile)).getText());
				UtilityMethods.generatePdfReport("Registration - Failure", new Font(FontFamily.COURIER,25.0f,Font.BOLD,BaseColor.RED));
				driver.close();
			}else if(driver.findElement(By.xpath(PageElements.err_Password)).isDisplayed() == true){
				UtilityMethods.capctureScreenShot(driver, "Reg_Password_Error", "Password Error : "+driver.findElement(By.xpath(PageElements.err_Password)).getText());
				UtilityMethods.generatePdfReport("Registration - Failure", new Font(FontFamily.COURIER,25.0f,Font.BOLD,BaseColor.RED));
				driver.close();
			}else if(driver.findElement(By.xpath(PageElements.err_Email_Exists)).isDisplayed() == true){
				UtilityMethods.capctureScreenShot(driver, "Reg_Email_Error", "Email_Exists Error : "+driver.findElement(By.xpath(PageElements.err_Email_Exists)).getText());
				UtilityMethods.generatePdfReport("Registration - Failure", new Font(FontFamily.COURIER,25.0f,Font.BOLD,BaseColor.RED));
				driver.close();
			}else{
				
				/*UtilityMethods.generatePdfReport("Registration - Success",new Font(FontFamily.COURIER,25.0f,Font.BOLD,BaseColor.GREEN));
				driver.close();*/
				
				wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(PageElements.logout_Icon))));
				
				// code for user logout
				 UtilityMethods.capctureScreenShot(driver, "User Registered", "User Successfully Registered");
				 Actions actions = new Actions(driver);
				 actions.moveToElement(driver.findElement(By.xpath(PageElements.logout_Icon))).build().perform();
				 Thread.sleep(2000);
				 UtilityMethods.capctureScreenShot(driver, "Logging Out", "User Logging Out");
				 actions.moveToElement(driver.findElement(By.xpath(PageElements.logout))).click().build().perform();
				 UtilityMethods.generatePdfReport("Registration - Success", new Font(FontFamily.COURIER,25.0f,Font.BOLD,BaseColor.GREEN));
			}
		}catch(Exception e){
			e.printStackTrace();
			//System.out.println("[Exception]..... Exception Occurred while registering the user");
			System.out.println(e);
			// Capture screen shot
			UtilityMethods.capctureScreenShot(driver, "Registration_Error", "Exception Occured While Registering the user");
			UtilityMethods.generatePdfReport("Registration - Failure", new Font(FontFamily.COURIER,25.0f,Font.BOLD,BaseColor.RED));
			driver.close();
		}

	}

	public void loginUser(String excelFile, String excelSheet){
		String email,password;
		openApplication();
		try{
			// wait and click on login link
			WebElement loginLink = driver.findElement(By.xpath(PageElements.loginLink));
			WebDriverWait wait = new WebDriverWait(driver, 5);
			wait.until(ExpectedConditions.visibilityOf(loginLink)).click();
			// read and enter the details
			email = UtilityMethods.readExcelCellData(excelFile, excelSheet, "LoginData - 1", "Email");
			password = UtilityMethods.readExcelCellData(excelFile, excelSheet, "LoginData - 1", "Password");
			driver.findElement(By.xpath(PageElements.login_email)).click();
			driver.findElement(By.xpath(PageElements.login_email)).sendKeys(email);
			driver.findElement(By.xpath(PageElements.login_password)).click();
			driver.findElement(By.xpath(PageElements.login_password)).sendKeys(password);
			driver.findElement(By.xpath(PageElements.loginButton)).click();
			// code to check error message
			if(driver.findElement(By.xpath(PageElements.err_login_email)).isDisplayed() == true){
				UtilityMethods.capctureScreenShot(driver, "Login_Email_Error", "Email-Error :"+driver.findElement(By.xpath(PageElements.err_login_email)).getText());
				UtilityMethods.generatePdfReport("Login-Failure", new Font(FontFamily.COURIER,25.0f,Font.BOLD,BaseColor.RED));
				driver.close();
			}else if(driver.findElement(By.xpath(PageElements.err_login_password)).isDisplayed() == true){
				UtilityMethods.capctureScreenShot(driver, "Login_Password_Error", "Password-Error :"+driver.findElement(By.xpath(PageElements.err_login_password)).getText());
				UtilityMethods.generatePdfReport("Login-Failure", new Font(FontFamily.COURIER,25.0f,Font.BOLD,BaseColor.RED));
				driver.close();
			}else{
				UtilityMethods.capctureScreenShot(driver, "LoginDetails", "Login Details Filled Successfully");
				UtilityMethods.generatePdfReport("Login-Success", new Font(FontFamily.COURIER,25.0f,Font.BOLD,BaseColor.GREEN));
				//driver.close();
			}
		}catch (Exception e) {
			System.out.println(e);
			UtilityMethods.capctureScreenShot(driver, "Login-Exception", "Exception Occured While Login to the app");
			UtilityMethods.generatePdfReport("Login-Failure", new Font(FontFamily.COURIER,25.0f,Font.BOLD,BaseColor.RED));
			driver.close();
		}


	}

	public void searchFlight(String excelFile, String excelSheet) throws Exception{


		String dep_City,arr_City,dep_Date,ret_Date,passengers,typeOfTrip,travel_class,flighToTravel;
		String email = "testing@gmail.com";
		//open the application
		//openApplication();
		// login into user account
		loginUser(excelFile, "Login");


		WebDriverWait wait = new WebDriverWait(driver, 10);
		typeOfTrip = UtilityMethods.readExcelCellData(excelFile, excelSheet, "Flight", "TypeOfTrip");
		dep_City = UtilityMethods.readExcelCellData(excelFile, excelSheet, typeOfTrip, "Dep_City");
		arr_City = UtilityMethods.readExcelCellData(excelFile, excelSheet, typeOfTrip, "Arr_City");
		dep_Date = UtilityMethods.readExcelCellData(excelFile, excelSheet, typeOfTrip, "Dep_Date");
		ret_Date = UtilityMethods.readExcelCellData(excelFile, excelSheet, typeOfTrip, "Ret_Date");
		passengers = UtilityMethods.readExcelCellData(excelFile, excelSheet, typeOfTrip, "Passengers");
		travel_class = UtilityMethods.readExcelCellData(excelFile, excelSheet, typeOfTrip, "Travel_Class");
		flighToTravel = UtilityMethods.readExcelCellData(excelFile, excelSheet, typeOfTrip, "FlightName");

		System.out.println("Excel File : "+excelFile);
		System.out.println("Excel Sheet : "+excelSheet);
		System.out.println("Type Of Trip Search : "+typeOfTrip);
		System.out.println("dep_City : "+dep_City);
		System.out.println("arr_City : "+arr_City);
		System.out.println("dep_Date : "+dep_Date);
		System.out.println("ret_Date : "+ret_Date);
		System.out.println("passengers : "+passengers);
		System.out.println("travel_class : "+travel_class);

		// wait and click on flights link
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(PageElements.flights))));

		// code enter departure city
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(PageElements.fromCity)))).sendKeys(dep_City);
		Thread.sleep(1000);


		// code to enter arrival city
		driver.findElement(By.xpath(PageElements.toCity)).click();
		driver.findElement(By.xpath(PageElements.toCity)).sendKeys(arr_City);
		Thread.sleep(1000);


		// code to enter departure date
		driver.findElement(By.xpath(PageElements.depDate)).click();
		selectDate(dep_Date);
		Thread.sleep(1000);


		// enter return date of travel type is round trip
		if(typeOfTrip.equalsIgnoreCase("Round Trip")){
			driver.findElement(By.xpath(PageElements.returnDate)).click();
			selectDate(ret_Date);
			Thread.sleep(1000);
		}

		// code to select passengers
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("document.getElementById('hp-widget__paxCounter').removeAttribute('readonly');");
		js.executeScript("document.getElementById('hp-widget__paxCounter').removeAttribute('onkeypress');");
		driver.findElement(By.xpath(PageElements.Passengers)).click();
		driver.findElement(By.xpath(PageElements.Passengers)).clear();
		driver.findElement(By.xpath(PageElements.Passengers)).sendKeys(passengers);
		Thread.sleep(1000);

		// code to select travel class
		js.executeScript("document.getElementById('hp-widget__paxCounter').removeAttribute('readonly');");
		js.executeScript("document.getElementById('hp-widget__paxCounter').removeAttribute('onkeypress');");
		driver.findElement(By.xpath(PageElements.classOfTravel)).click();
		driver.findElement(By.xpath(PageElements.classOfTravel)).sendKeys(travel_class);
		Thread.sleep(1000);

		// Capture screen
		UtilityMethods.capctureScreenShot(driver, "Flight Search", "Flight Search Details Filled");
		// code to click on search button
		driver.findElement(By.xpath(PageElements.flightSearchButton)).click();

		if(driver.getTitle().contentEquals("MakeMyTrip, India's No 1 Travel Site | Book Hotels, Flights, Holiday")){
			if(driver.findElement(By.xpath(PageElements.err_fromCity)).isDisplayed() == true){
				UtilityMethods.capctureScreenShot(driver, "Error-DepCity", "Departure City Not Selected");
				UtilityMethods.generatePdfReport("Flight Search-Failure", new Font(FontFamily.COURIER,25.0f,Font.BOLD,BaseColor.RED));
				driver.close();
			}else if(driver.findElement(By.xpath(PageElements.err_toCity)).isDisplayed() == true){
				UtilityMethods.capctureScreenShot(driver, "Error-ArrCity", "Arrival City Not Selected");
				UtilityMethods.generatePdfReport("Flight Search-Failure", new Font(FontFamily.COURIER,25.0f,Font.BOLD,BaseColor.RED));
				driver.close();
			}else if(driver.findElement(By.xpath(PageElements.err_Return_Date)).isDisplayed() == true){
				UtilityMethods.capctureScreenShot(driver, "Error-ReturnDate", "Return Date Not Selected");
				UtilityMethods.generatePdfReport("Flight Search-Failure", new Font(FontFamily.COURIER,25.0f,Font.BOLD,BaseColor.RED));
				driver.close();
			}
		}else if(driver.getTitle().contentEquals("MakeMyTrip")){
			try{
				wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//p[text()='"+flighToTravel+"']//following::a[contains(@class,'btn fli_primary_btn')][1]"))));
				// capture screenshot
				UtilityMethods.capctureScreenShot(driver, "Available Flight Details", "Available Flights Searched Successfully");
				if(driver.findElement(By.xpath("//*[text()='Sorry, but we are unable to find flights. Try changing the dates!']")).isDisplayed() == true){
					UtilityMethods.capctureScreenShot(driver, "Unable to find flight", "Unable to find flights");
					UtilityMethods.generatePdfReport("Flight Search-Failure", new Font(FontFamily.COURIER,25.0f,Font.BOLD,BaseColor.RED));
					driver.close();
				}

				driver.findElement(By.xpath("//p[text()='"+flighToTravel+"']//following::a[contains(@class,'btn fli_primary_btn')][1]")).click();
				wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(PageElements.flight_Book_Checkbox))));
				UtilityMethods.capctureScreenShot(driver, "Flight - Selected", "Travel Flight Selected Successfully");

				// step to enter email and click on continue as guest button
				if(driver.findElement(By.xpath(PageElements.flight_Book_Email)).isEnabled() == true){
					driver.findElement(By.xpath(PageElements.flight_Book_Email)).sendKeys(email);
					UtilityMethods.capctureScreenShot(driver, "Email - Non Registered User", "Email Entered Successfully To Send Tickets");
					driver.findElement(By.xpath(PageElements.flight_Book_ContinueAsGuest_Button)).click();
					Thread.sleep(1000);
				}else{
					// click on continue button
					wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(PageElements.flight_Book_Continue_Button)))).click();
					Thread.sleep(1000);
				}

				/*if(driver.findElement(By.xpath(PageElements.flight_Book_Err_Email)).isDisplayed() == true){
					UtilityMethods.capctureScreenShot(driver, "Email - Error", "Email Not Entered");
					UtilityMethods.generatePdfReport("Flight_Book - Failed", new Font(FontFamily.COURIER,25.0f,Font.BOLD,BaseColor.RED));
					driver.close();
				}*/

			}catch(NoSuchElementException ne){
				System.out.println(ne);
				UtilityMethods.capctureScreenShot(driver, "Flight Not Found", "Flight Not Available : "+flighToTravel);
				UtilityMethods.generatePdfReport("Flight_Book - Failed", new Font(FontFamily.COURIER,25.0f,Font.BOLD,BaseColor.RED));
				driver.close();
			}

			// wait and enter given name and surname
			try{
				wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(PageElements.flight_Book_GivenName)))).sendKeys(UtilityMethods.readExcelCellData(excelFile, excelSheet, typeOfTrip, "GivenName"));
			}catch (NoSuchElementException e) {
				System.out.println(e);
			}
		}
		driver.findElement(By.xpath(PageElements.flight_Book_Surname)).sendKeys(UtilityMethods.readExcelCellData(excelFile, excelSheet, typeOfTrip, "SurName"));
		Thread.sleep(1000);

		// step to select nationality
		String nationality = UtilityMethods.readExcelCellData(excelFile, excelSheet, typeOfTrip, "Nationality");
		driver.findElement(By.xpath(PageElements.flight_Book_Nationality)).click();
		List<WebElement> allCountries = driver.findElements(By.xpath(PageElements.flight_Book_Nationality_Options));
		ListIterator<WebElement> itr = allCountries.listIterator();
		while(itr.hasNext()){
			String country = itr.next().getText();
			if(nationality.contentEquals(country)){
				itr.previous().click();
				break;
			}
		}
		Thread.sleep(1000);

		// step to enter mobile number
		driver.findElement(By.xpath(PageElements.flight_Book_Mobile)).sendKeys(UtilityMethods.readExcelCellData(excelFile, excelSheet, typeOfTrip, "Mobile"));
		UtilityMethods.capctureScreenShot(driver, "Flight_Book_Traveller Details", "Trveller Details Filled Successfully");

		// step to click on payment button
		if(driver.findElement(By.xpath(PageElements.flight_Book_PaymentButton)).isDisplayed() && driver.findElement(By.xpath(PageElements.flight_Book_PaymentButton)).isEnabled()){
			driver.findElement(By.xpath(PageElements.flight_Book_PaymentButton)).click();
			wait.until(ExpectedConditions.visibilityOf(driver.findElement((By.xpath("//span[contains(text(),'Debit Card')]")))));
		}

		if(driver.getTitle().contentEquals("International Flights Air Tickets, Cheap International Air fares at MakeMyTrip")){

			if(driver.findElement(By.xpath(PageElements.flight_Book_Err_GivenName)).isDisplayed() == true){
				UtilityMethods.capctureScreenShot(driver, "Flight_Book - Given Name", "Given Name is not entered");
				UtilityMethods.generatePdfReport("Flight_Book - Fail", new Font(FontFamily.COURIER,25.0f,Font.BOLD,BaseColor.RED));
				driver.close();
			}else if(driver.findElement(By.xpath(PageElements.flight_Book_Err_Mobile)).isDisplayed() == true){
				UtilityMethods.capctureScreenShot(driver, "Flight_Book_Error - Mobile", "Mobile Number is not entered");
				UtilityMethods.generatePdfReport("Flight_Book - Fail", new Font(FontFamily.COURIER,25.0f,Font.BOLD,BaseColor.RED));
				driver.close();
			}

		}else if(driver.getTitle().contentEquals("MakeMytrip Payment : Safe and Secure")){
			UtilityMethods.capctureScreenShot(driver, "Flight_Booking - Success", "Successfully Booked a flight");
			UtilityMethods.generatePdfReport("Flight_Book_Success", new Font(FontFamily.COURIER,25.0f,Font.BOLD,BaseColor.GREEN));
			driver.close();
		}
	}

	public void searchBus(String excelFile, String excelSheet)throws Exception{

		String dep_City,arr_City,dep_Date,email;
		// open the application
		openApplication();
		loginUser(excelFile, "Login");
		// wait and click on Bus link
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(PageElements.bus)))).click();

		System.out.println("Excel File : "+excelFile);
		System.out.println("Excel File : "+excelSheet);

		dep_City = UtilityMethods.readExcelCellData(excelFile, excelSheet, "Bus-1", "Dept_City");
		arr_City = UtilityMethods.readExcelCellData(excelFile, excelSheet, "Bus-1", "Arrv_City");
		dep_Date = UtilityMethods.readExcelCellData(excelFile, excelSheet, "Bus-1", "Dept_Date");
		email = UtilityMethods.readExcelCellData(excelFile, excelSheet, "Bus-1", "Email");

		System.out.println("Departure City :"+dep_City);
		System.out.println("Arrival City : "+arr_City);
		System.out.println("Dep. Date : "+dep_Date);
		System.out.println("Email : "+email);

		// enter the values
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(PageElements.bus_Dep_City))));
		driver.findElement(By.xpath(PageElements.bus_Dep_City)).sendKeys(dep_City);
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(PageElements.bus_Dep_City_Text)))).click();

		driver.findElement(By.xpath(PageElements.bus_Arrv_City)).sendKeys(arr_City);
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(PageElements.bus_Arrv_City_Text)))).click();

		// code to select date
		driver.findElement(By.xpath(PageElements.bus_Dep_DatePicker)).click();
		selectDate(dep_Date);
		Thread.sleep(1000);

		// Capture screen shot
		UtilityMethods.capctureScreenShot(driver, "Bus_Search", "Bus Search Details Filled");

		// click on search button
		driver.findElement(By.xpath(PageElements.bus_SearchButton)).click();

		if(driver.getTitle().contentEquals("Online Bus Ticket Booking, Book Confirmed Reservation Tickets @ MakeMyTrip")){
			if(driver.findElement(By.xpath(PageElements.bus_Err_Dep_City)).isDisplayed() == true){
				UtilityMethods.capctureScreenShot(driver, "Travel Cities", driver.findElement(By.xpath(PageElements.bus_Err_Dep_City)).getText());
			}
		}else if(driver.getTitle().contentEquals("Online Bus Ticket Booking !: Best Deals in MakeMyTrip")){
			UtilityMethods.capctureScreenShot(driver, "Available Bus Details", "Available Buses Searched Successfully");
			String typeOfBus = UtilityMethods.readExcelCellData(excelFile, excelSheet, "Bus-1", "TypeOfBus");
			String travelsName = UtilityMethods.readExcelCellData(excelFile, excelSheet, "Bus-1", "TravelsName");
			System.out.println("Type Of Bus : "+typeOfBus);
			System.out.println("Travels Name : "+travelsName);

			// code to book the bus

			// code to filter type of bus (sleeper / seater)
			try{
				wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//strong[contains(text(),'Bus Type')]//following::span[contains(text(),'"+typeOfBus+"')][1]")))).click();
			}catch(TimeoutException e){
				driver.navigate().refresh();
				//driver.get(driver.getCurrentUrl());
			}

			// code to filter the bustype,travels and click on select button
			
			if(driver.findElement(By.xpath("//strong[contains(text(),'"+travelsName+"')]//following::a[text()='Select'][1]")).isDisplayed() == true){
				UtilityMethods.capctureScreenShot(driver, "Bus - Filtered", "Bus Filtered Successfully : "+travelsName);
				Thread.sleep(1000);
				// click on the travels select button
				driver.findElement(By.xpath("//strong[contains(text(),'"+travelsName+"')]//following::a[text()='Select'][1]")).click();
				Thread.sleep(5000);
				int noOfSeats = Integer.parseInt(UtilityMethods.readExcelCellData(excelFile, excelSheet, "Bus-1", "NoOfSeats"));
				// code to select seats

				//==================================================== Bus Booking For Sleeper - Start ====================================================================
				if(typeOfBus.contentEquals("Sleeper")){
					String berth = UtilityMethods.readExcelCellData(excelFile, excelSheet, "Bus-1", "Berth");
					System.out.println("Berth : "+berth);
					if(berth.contentEquals("Upper")){
						List<WebElement> availableSeats = driver.findElements(By.xpath("//div[contains(@class,'bus_view col-xs-12 padd0 marB10')]//span[@class='inlineB seat_type_sleeper']"));
						Iterator<WebElement> itr = availableSeats.iterator();
						for(int i = 1 ; i <= noOfSeats ; i++){
							itr.next().click();
						}

					}else if(berth.contentEquals("Lower")){
						List<WebElement> availableSeats = driver.findElements(By.xpath("//div[contains(@class,'bus_view col-xs-12 padd0 marB10')]//span[@class='inlineB seat_type_sleeper']"));
						Iterator<WebElement> itr = availableSeats.iterator();
						for(int i = 1 ; i <= noOfSeats ; i++){
							itr.next().click();
						}

					}

					//code to select boarding point
					/*String boardingPoint = UtilityMethods.readExcelCellData(excelFile, excelSheet, "Bus-1", "BoardingPoint");
								driver.findElement(By.xpath(PageElements.bus_Book_BoardingPoint)).click();
								Thread.sleep(2000);
								List<WebElement> allBoardingPoints = driver.findElements(By.xpath(PageElements.bus_Book_BoardingPoint_Options));
								ListIterator<WebElement> itr = allBoardingPoints.listIterator();
								while(itr.hasNext()){
									String point = itr.next().getText();
									//System.out.println(point+" , "+(point.contains(boardingPoint)));
									if(point.contains(boardingPoint)){
										Actions actions = new Actions(driver);
										actions.moveToElement(itr.previous()).click().build().perform();
										break;
									}
								}*/
					/*Select select = new Select(driver.findElement(By.xpath(PageElements.bus_Book_BoardingPoint)));
								select.selectByVisibleText(boardingPoint);*/

					// capcture screen
					UtilityMethods.capctureScreenShot(driver, "Seat Selection", "No. Of Seats And Boarding Point Selected");
					// click on select seat button
					driver.findElement(By.xpath(PageElements.bus_Book_SelectSeats_Button_Sleeper)).click();
					Thread.sleep(2000);
					if(driver.findElement(By.xpath(PageElements.bus_Book_SelectSeat_Alert)).isDisplayed() == true){
						UtilityMethods.capctureScreenShot(driver, "No Seats Selected", "No Seats Selected");
						UtilityMethods.generatePdfReport("Bus_Book - Failed", new Font(FontFamily.COURIER,25.0f,Font.BOLD,BaseColor.RED));
						driver.close();
					}else{

						String gender = UtilityMethods.readExcelCellData(excelFile, excelSheet, "Bus-1", "Gender");
						String age = UtilityMethods.readExcelCellData(excelFile, excelSheet, "Bus-1", "Age");
						String bus_Book_Email = UtilityMethods.readExcelCellData(excelFile, excelSheet, "Bus-1", "Email");
						String mobile = UtilityMethods.readExcelCellData(excelFile, excelSheet, "Bus-1", "Mobile");
						String firstName1 = UtilityMethods.readExcelCellData(excelFile, excelSheet, "Bus-1", "FirstName1");
						String lastName1 = UtilityMethods.readExcelCellData(excelFile, excelSheet, "Bus-1", "LastName1");
						/*String firstName2 = UtilityMethods.readExcelCellData(excelFile, excelSheet, "Bus-1", "FirstName2");
									String lastName2 = UtilityMethods.readExcelCellData(excelFile, excelSheet, "Bus-1", "LastName2");*/

						System.out.println("Gender : "+gender);
						System.out.println("Age : "+age);
						System.out.println("Bus Booking Email  : "+bus_Book_Email);
						System.out.println("Mobile : "+mobile);
						System.out.println("First Name1 : "+firstName1);
						System.out.println("Last Name1 : "+lastName1);
						/*System.out.println("First Name2 : "+firstName2);
									System.out.println("Last Name2 : "+lastName2);*/


						// fill the traveller details with respect to the no.of seats selected
						for( int i = 0; i < noOfSeats ; i++){
							wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//input[starts-with(@id,'frstName_trvlr_"+i+"')]")))).click();
							driver.findElement(By.xpath("//input[starts-with(@id,'frstName_trvlr_"+i+"')]")).sendKeys(firstName1);
							Thread.sleep(1000);

							driver.findElement(By.xpath("//input[starts-with(@id,'lastName_trvlr_"+i+"')]")).click();
							driver.findElement(By.xpath("//input[starts-with(@id,'lastName_trvlr_"+i+"')]")).sendKeys(lastName1);
							Thread.sleep(1000);

							if(gender.contentEquals("Male")){
								driver.findElement(By.xpath("//a[starts-with(@id,'genderM_trvlr_"+i+"')]")).click();
							}else if(gender.contentEquals("Female")){
								driver.findElement(By.xpath("//a[starts-with(@id,'genderM_trvlr_"+i+"')]")).click();
							}

							driver.findElement(By.xpath("//*[text()=' Traveller "+(i+1)+": ']//following::label[contains(text(),'Age')][1]//following::input[starts-with(@name,'age')]")).click();
							driver.findElement(By.xpath("//*[text()=' Traveller "+(i+1)+": ']//following::label[contains(text(),'Age')][1]//following::input[starts-with(@name,'age')]")).sendKeys(age);
						}

						driver.findElement(By.xpath(PageElements.bus_Book_Email)).click();
						driver.findElement(By.xpath(PageElements.bus_Book_Email)).sendKeys(bus_Book_Email);

						driver.findElement(By.xpath(PageElements.bus_Book_Mobile)).click();
						driver.findElement(By.xpath(PageElements.bus_Book_Mobile)).sendKeys(mobile);


						// capture screen
						UtilityMethods.capctureScreenShot(driver, "Traveller - details", "Traveller Details Filled");

						// click on continue button
						driver.findElement(By.xpath(PageElements.bus_Book_ContinueButton)).click();

						if(driver.getTitle().contentEquals("Online Bus Ticket Booking !: Best Deals in MakeMyTrip")){
							try{
								if(driver.findElement(By.xpath(PageElements.bus_Book_SameName_Err_Button)).isDisplayed() == true){
									UtilityMethods.capctureScreenShot(driver, "BusBook_Samename", "Traveller Names Should Not Be Same");
									UtilityMethods.generatePdfReport("Bus_Book - Fail", new Font(FontFamily.COURIER,25.0f,Font.BOLD,BaseColor.RED));
									driver.close();
								}else if(driver.findElement(By.xpath(PageElements.bus_Book_Err_FirstName)).isDisplayed() == true){
									UtilityMethods.capctureScreenShot(driver, "BusBook_Firstname", "First Name Not Entered");
									UtilityMethods.generatePdfReport("Bus_Book - Fail", new Font(FontFamily.COURIER,25.0f,Font.BOLD,BaseColor.RED));
									driver.close();
								}else if(driver.findElement(By.xpath(PageElements.bus_Book_Err_LastName)).isDisplayed() == true){
									UtilityMethods.capctureScreenShot(driver, "BusBook_Lastname", "Last Name Not Entered");
									UtilityMethods.generatePdfReport("Bus_Book - Fail", new Font(FontFamily.COURIER,25.0f,Font.BOLD,BaseColor.RED));
									driver.close();
								}else if(driver.findElement(By.xpath(PageElements.bus_Book_Err_Age)).isDisplayed() == true){
									UtilityMethods.capctureScreenShot(driver, "BusBook_Age", "Age Not Entered");
									UtilityMethods.generatePdfReport("Bus_Book - Fail", new Font(FontFamily.COURIER,25.0f,Font.BOLD,BaseColor.RED));
									driver.close();
								}else if(driver.findElement(By.xpath(PageElements.bus_Book_Err_Email)).isDisplayed() == true){
									UtilityMethods.capctureScreenShot(driver, "BusBook_Email", "Email Not Entered");
									UtilityMethods.generatePdfReport("Bus_Book - Fail", new Font(FontFamily.COURIER,25.0f,Font.BOLD,BaseColor.RED));
									driver.close();
								}

							}catch(NoSuchElementException e){
								if(driver.getTitle().contentEquals("MakeMytrip Payment : Safe and Secure")){
									wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//span[text()='CHOOSE PAYMENT MODE']"))));
									UtilityMethods.capctureScreenShot(driver, "Bus_Booking - Success", "Successfully Booked a Bus");
									UtilityMethods.generatePdfReport("Bus_Book - Success", new Font(FontFamily.COURIER,25.0f,Font.BOLD,BaseColor.GREEN));
									driver.close();
								}
							}
						}

					}
					// ==================================================== Bus Booking For Sleeper - End ====================================================================

					// ==================================================== Bus Booking For Seater - Start ====================================================================

				}else if(typeOfBus.contentEquals("Seater")){

					try{
						/*// wait until selected travels and seat type bus availability
						wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[text()='"+travelsName+"']"))))*/;
						// click on the bus type filter
						try{
							Actions actions = new Actions(driver);
							actions.moveToElement(driver.findElement(By.xpath("//strong[contains(text(),'Bus Type')]//following::span[contains(text(),'"+typeOfBus+"')][1]")));
							actions.build().perform();
							//wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//strong[contains(text(),'Bus Type')]//following::span[contains(text(),'"+typeOfBus+"')][1]")))).click();
						}catch(TimeoutException e){
							driver.navigate().refresh();
							//driver.get(driver.getCurrentUrl());
						}	

						// wait until seat selection frame presence
						//wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@class='col-xs-12 padd0']"))));

						Thread.sleep(5000);

						// code to select no. of seats
						List<WebElement> availableSeats = driver.findElements(By.xpath("//div[contains(@class,'bus_view col-xs-12 padd0 marB10')]//span[@class='inlineB seat_available']"));
						Iterator<WebElement> itr = availableSeats.iterator();
						for(int i = 1 ; i <= noOfSeats ; i++){
							itr.next().click();
						}
						Thread.sleep(2000);

						//code to select boarding point
						/*String boardingPoint = UtilityMethods.readExcelCellData(excelFile, excelSheet, "Bus-1", "BoardingPoint");
									driver.findElement(By.xpath(PageElements.bus_Book_BoardingPoint)).click();
									Thread.sleep(2000);
									List<WebElement> allBoardingPoints = driver.findElements(By.xpath(PageElements.bus_Book_BoardingPoint_Options));
									ListIterator<WebElement> itr = allBoardingPoints.listIterator();
									while(itr.hasNext()){
										String point = itr.next().getText();
										//System.out.println(point+" , "+(point.contains(boardingPoint)));
										if(point.contains(boardingPoint)){
											Actions actions = new Actions(driver);
											actions.moveToElement(itr.previous()).click().build().perform();
											break;
										}
									}*/
						/*Select select = new Select(driver.findElement(By.xpath(PageElements.bus_Book_BoardingPoint)));
									select.selectByVisibleText(boardingPoint);*/

						// click on select button
						driver.findElement(By.xpath(PageElements.bus_Book_SelectSeats_Button_Seater)).click();
						Thread.sleep(2000);

						// check for seat alert presence
						if(driver.findElement(By.xpath(PageElements.bus_Book_SelectSeat_Alert)).isDisplayed() == true){
							UtilityMethods.capctureScreenShot(driver, "No Seats Selected", "No Seats Selected");
							UtilityMethods.generatePdfReport("Bus_Book - Failed", new Font(FontFamily.COURIER,25.0f,Font.BOLD,BaseColor.RED));
							driver.close();
						}else{

							String gender = UtilityMethods.readExcelCellData(excelFile, excelSheet, "Bus-1", "Gender");
							String age = UtilityMethods.readExcelCellData(excelFile, excelSheet, "Bus-1", "Age");
							String bus_Book_Email = UtilityMethods.readExcelCellData(excelFile, excelSheet, "Bus-1", "Email");
							String mobile = UtilityMethods.readExcelCellData(excelFile, excelSheet, "Bus-1", "Mobile");
							String firstName1 = UtilityMethods.readExcelCellData(excelFile, excelSheet, "Bus-1", "FirstName1");
							String lastName1 = UtilityMethods.readExcelCellData(excelFile, excelSheet, "Bus-1", "LastName1");
							/*String firstName2 = UtilityMethods.readExcelCellData(excelFile, excelSheet, "Bus-1", "FirstName2");
										String lastName2 = UtilityMethods.readExcelCellData(excelFile, excelSheet, "Bus-1", "LastName2");*/

							System.out.println("Gender : "+gender);
							System.out.println("Age : "+age);
							System.out.println("Bus Booking Email  : "+bus_Book_Email);
							System.out.println("Mobile : "+mobile);
							System.out.println("First Name1 : "+firstName1);
							System.out.println("Last Name1 : "+lastName1);
							/*System.out.println("First Name2 : "+firstName2);
										System.out.println("Last Name2 : "+lastName2);*/
							// capcture screen
							UtilityMethods.capctureScreenShot(driver, "Seat Selection", "No. Of Seats And Boarding Point Selected");

							// fill the traveller details with respect to the no.of seats selected
							for( int i = 0; i < noOfSeats ; i++){
								wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//input[starts-with(@id,'frstName_trvlr_"+i+"')]")))).click();
								driver.findElement(By.xpath("//input[starts-with(@id,'frstName_trvlr_"+i+"')]")).sendKeys(firstName1);
								Thread.sleep(1000);

								driver.findElement(By.xpath("//input[starts-with(@id,'lastName_trvlr_"+i+"')]")).click();
								driver.findElement(By.xpath("//input[starts-with(@id,'lastName_trvlr_"+i+"')]")).sendKeys(lastName1);
								Thread.sleep(1000);

								if(gender.contentEquals("Male")){
									driver.findElement(By.xpath("//a[starts-with(@id,'genderM_trvlr_"+i+"')]")).click();
								}else if(gender.contentEquals("Female")){
									driver.findElement(By.xpath("//a[starts-with(@id,'genderM_trvlr_"+i+"')]")).click();
								}

								driver.findElement(By.xpath("//*[text()=' Traveller "+(i+1)+": ']//following::label[contains(text(),'Age')][1]//following::input[starts-with(@name,'age')]")).click();
								driver.findElement(By.xpath("//*[text()=' Traveller "+(i+1)+": ']//following::label[contains(text(),'Age')][1]//following::input[starts-with(@name,'age')]")).sendKeys(age);
							}

							driver.findElement(By.xpath(PageElements.bus_Book_Email)).click();
							driver.findElement(By.xpath(PageElements.bus_Book_Email)).sendKeys(bus_Book_Email);

							driver.findElement(By.xpath(PageElements.bus_Book_Mobile)).click();
							driver.findElement(By.xpath(PageElements.bus_Book_Mobile)).sendKeys(mobile);


							// capture screen
							UtilityMethods.capctureScreenShot(driver, "Traveller - details", "Traveller Details Filled");

							// click on continue button
							driver.findElement(By.xpath(PageElements.bus_Book_ContinueButton)).click();

							if(driver.getTitle().contentEquals("Online Bus Ticket Booking !: Best Deals in MakeMyTrip")){
								try{
									if(driver.findElement(By.xpath(PageElements.bus_Book_SameName_Err_Button)).isDisplayed() == true){
										UtilityMethods.capctureScreenShot(driver, "BusBook_Samename", "Traveller Names Should Not Be Same");
										UtilityMethods.generatePdfReport("Bus_Book - Fail", new Font(FontFamily.COURIER,25.0f,Font.BOLD,BaseColor.RED));
										driver.close();
									}else if(driver.findElement(By.xpath(PageElements.bus_Book_Err_FirstName)).isDisplayed() == true){
										UtilityMethods.capctureScreenShot(driver, "BusBook_Firstname", "First Name Not Entered");
										UtilityMethods.generatePdfReport("Bus_Book - Fail", new Font(FontFamily.COURIER,25.0f,Font.BOLD,BaseColor.RED));
										driver.close();
									}else if(driver.findElement(By.xpath(PageElements.bus_Book_Err_LastName)).isDisplayed() == true){
										UtilityMethods.capctureScreenShot(driver, "BusBook_Lastname", "Last Name Not Entered");
										UtilityMethods.generatePdfReport("Bus_Book - Fail", new Font(FontFamily.COURIER,25.0f,Font.BOLD,BaseColor.RED));
										driver.close();
									}else if(driver.findElement(By.xpath(PageElements.bus_Book_Err_Age)).isDisplayed() == true){
										UtilityMethods.capctureScreenShot(driver, "BusBook_Age", "Age Not Entered");
										UtilityMethods.generatePdfReport("Bus_Book - Fail", new Font(FontFamily.COURIER,25.0f,Font.BOLD,BaseColor.RED));
										driver.close();
									}else if(driver.findElement(By.xpath(PageElements.bus_Book_Err_Email)).isDisplayed() == true){
										UtilityMethods.capctureScreenShot(driver, "BusBook_Email", "Email Not Entered");
										UtilityMethods.generatePdfReport("Bus_Book - Fail", new Font(FontFamily.COURIER,25.0f,Font.BOLD,BaseColor.RED));
										driver.close();
									}

								}catch(NoSuchElementException e){
									if(driver.getTitle().contentEquals("MakeMytrip Payment : Safe and Secure")){
										wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//span[text()='CHOOSE PAYMENT MODE']"))));
										UtilityMethods.capctureScreenShot(driver, "Bus_Booking - Success", "Successfully Booked a Bus");
										UtilityMethods.generatePdfReport("Bus_Book - Success", new Font(FontFamily.COURIER,25.0f,Font.BOLD,BaseColor.GREEN));
										driver.close();
									}
								}
							}

						}

					}catch (NoSuchElementException e) {
						if(driver.findElement(By.xpath("//div[contains(@class,'alert alert-danger error_case hidden-xs visible-stb')]")).isDisplayed() == true){
							UtilityMethods.capctureScreenShot(driver, "Bus - Unavilable", "Filtered Busses not available : "+travelsName);
							UtilityMethods.generatePdfReport("Bus_Filter - Success", new Font(FontFamily.COURIER,25.0f,Font.BOLD,BaseColor.GREEN));
							driver.close();
						}else{
							UtilityMethods.capctureScreenShot(driver, "Bus - Unavilable", "Selected Travels Busses not available : "+travelsName);
							UtilityMethods.generatePdfReport("Bus_Filter - Failure", new Font(FontFamily.COURIER,25.0f,Font.BOLD,BaseColor.RED));
							driver.close();
						}
					}

				} // Seater if close

				// ==================================================== Bus Booking For Seater - End ====================================================================
			}else if(driver.findElement(By.xpath("//div[contains(@class,'alert alert-danger error_case hidden-xs visible-stb')]")).isDisplayed() == true){
				UtilityMethods.capctureScreenShot(driver, "Bus - Unavilable", "Filtered Busses not available : "+travelsName);
				UtilityMethods.generatePdfReport("Bus_Filter - Success", new Font(FontFamily.COURIER,25.0f,Font.BOLD,BaseColor.GREEN));
				driver.close();
			}
		}
	}

	public void bookHotel(String excelFile, String excelSheet)throws Exception{

		// open application
		openApplication();

		// click on hotel link
		driver.findElement(By.xpath(PageElements.hotels)).click();

		WebDriverWait wait = new WebDriverWait(driver, 10);

		// wait until city column presence and click
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(PageElements.hotel_City)))).click();

		// code to enter city
		String city = UtilityMethods.readExcelCellData(excelFile, excelSheet, "Hotel-1", "City");
		System.out.println("Hotel_City : "+city);
		try{
			wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(PageElements.hotel_Searchbutton))));
			//driver.findElement(By.xpath(PageElements.hotel_City)).click();
			driver.findElement(By.xpath(PageElements.hotel_City)).clear();
			driver.findElement(By.xpath(PageElements.hotel_City)).sendKeys(city);
			wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(PageElements.hotel_City_Option)))).click();
		}catch(Exception e){
			driver.navigate().refresh();
		}
		Thread.sleep(1000);

		// code to enter check in date
		String checkInDate = UtilityMethods.readExcelCellData(excelFile, excelSheet, "Hotel-1", "CheckIn");
		//driver.findElement(By.xpath(PageElements.hotel_checkIn)).click();
		System.out.println("Hotel_CheckIn_Date : "+checkInDate);
		selectDate(checkInDate);
		Thread.sleep(1000);

		// code to select check out date
		String checkOutDate = UtilityMethods.readExcelCellData(excelFile, excelSheet, "Hotel-1", "CheckOut");
		//driver.findElement(By.xpath(PageElements.hotel_checkOut));
		System.out.println("Hotel_CheckIn_Date : "+checkOutDate);
		selectDate(checkOutDate);
		Thread.sleep(1000);

		// code to select rooms
		String rooms = UtilityMethods.readExcelCellData(excelFile, excelSheet, "Hotel-1", "Rooms");
		System.out.println("Hotel Rooms : "+rooms);
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("document.getElementById('hp-widget__paxCounter').removeAttribute('readonly');");
		driver.findElement(By.xpath(PageElements.hotel_rooms_Guests)).click();
		driver.findElement(By.xpath(PageElements.hotel_rooms_Guests)).clear();
		driver.findElement(By.xpath(PageElements.hotel_rooms_Guests)).sendKeys(rooms);
		Thread.sleep(1000);

		// capture screen
		UtilityMethods.capctureScreenShot(driver, "Hotel Details", "Search Hotel Details Filled");
		
		// click on search button
		driver.findElement(By.xpath(PageElements.hotel_Searchbutton)).click();
		if(driver.findElement(By.xpath(PageElements.hotel_Err_CheckIn)).isDisplayed() == true){
			UtilityMethods.capctureScreenShot(driver, "Hotel_Samedates_Error", driver.findElement(By.xpath(PageElements.hotel_Err_CheckIn)).getText());
			UtilityMethods.generatePdfReport("Hotel_Book - Fail", new Font(FontFamily.COURIER,25.0f,Font.BOLD,BaseColor.RED));
			driver.close();
		}else{
			// wait until hotels page loaded

			wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//span[contains(text(),'Hotels in')]"))));

			// click on the hotel
			String hotelName = UtilityMethods.readExcelCellData(excelFile, excelSheet, "Hotel-1", "HotelName");
			List<WebElement> allHotels = driver.findElements(By.xpath("//span[@id='js-hotelName-0']"));
			ListIterator<WebElement> itr = allHotels.listIterator();
			while(itr.hasNext()){
				String hotel = itr.next().getText();
				System.out.println(hotel+"( -> "+hotel.length()+")  , "+hotelName+"( -> "+hotelName.length()+")");
				if(hotel.length() == hotelName.length()){
					Actions actions = new Actions(driver);
					actions.moveToElement(itr.previous());
					Thread.sleep(30000);
					actions.click();
					actions.build().perform();
					//((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+(itr.previous().getLocation().x)+50+")");
					//itr.previous().click();
					break;
				}
			}		

			// wait until hotel page to be loaded and click on book now button
			/*WebDriverWait wait1 = new WebDriverWait(driver, 20);
			wait1.until(ExpectedConditions.titleContains(hotelName));*/
			try{
				wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//div[contains(@id,'detailWalletBanner')]//following::a[contains(text(),'Book Now')][1]")))).click();
			}catch(NoSuchElementException e){
				driver.navigate().refresh();
				wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//div[contains(@id,'detailWalletBanner')]//following::a[contains(text(),'Book Now')][1]")))).click();
			}
			// wait until first name field presence and start filling details
			String firstname = UtilityMethods.readExcelCellData(excelFile, excelSheet, "Hotel-1", "FirstName");
			String lastname = UtilityMethods.readExcelCellData(excelFile, excelSheet, "Hotel-1", "LastName");
			String email = UtilityMethods.readExcelCellData(excelFile, excelSheet, "Hotel-1", "Email");
			String mobile = UtilityMethods.readExcelCellData(excelFile, excelSheet, "Hotel-1", "Mobile");

			wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(PageElements.hotel_Book_FirstName)))).sendKeys(firstname);
			Thread.sleep(1000);

			driver.findElement(By.xpath(PageElements.hotel_Book_LastName)).sendKeys(lastname);
			Thread.sleep(1000);

			driver.findElement(By.xpath(PageElements.hotel_Book_Email)).sendKeys(email);
			Thread.sleep(1000);

			driver.findElement(By.xpath(PageElements.hotel_Book_Mobile)).sendKeys(mobile);
			Thread.sleep(1000);

			// capture screen
			UtilityMethods.capctureScreenShot(driver, "Hotel Booking Guest Details", "Hotel Booking Guest Details Filled");

			// click on payment button
			driver.findElement(By.xpath(PageElements.hotel_Book_PaymentButton)).click();

			if(driver.getTitle().contentEquals("Online Bus Ticket Booking !: Best Deals in MakeMyTrip")){
				try{
					if(driver.findElement(By.xpath(PageElements.hotel_Book_Err_FirstName)).isDisplayed() == true){
						UtilityMethods.capctureScreenShot(driver, "HotelBook_Firstname", driver.findElement(By.xpath(PageElements.hotel_Book_Err_FirstName)).getText());
						UtilityMethods.generatePdfReport("Hotel_Book - Fail", new Font(FontFamily.COURIER,25.0f,Font.BOLD,BaseColor.RED));
						driver.close();
					}else if(driver.findElement(By.xpath(PageElements.hotel_Book_Err_LastName)).isDisplayed() == true){
						UtilityMethods.capctureScreenShot(driver, "HotelBook_Lastname", driver.findElement(By.xpath(PageElements.hotel_Book_Err_LastName)).getText());
						UtilityMethods.generatePdfReport("Hotel_Book - Fail", new Font(FontFamily.COURIER,25.0f,Font.BOLD,BaseColor.RED));
						driver.close();
					}else if(driver.findElement(By.xpath(PageElements.hotel_Book_Err_Email)).isDisplayed() == true){
						UtilityMethods.capctureScreenShot(driver, "HotelBook_Email", driver.findElement(By.xpath(PageElements.hotel_Book_Err_Email)).getText());
						UtilityMethods.generatePdfReport("Hotel_Book - Fail", new Font(FontFamily.COURIER,25.0f,Font.BOLD,BaseColor.RED));
						driver.close();
					}else if(driver.findElement(By.xpath(PageElements.hotel_Book_Err_Mobile)).isDisplayed() == true){
						UtilityMethods.capctureScreenShot(driver, "HotelBook_Mobile", driver.findElement(By.xpath(PageElements.hotel_Book_Err_Mobile)).getText());
						UtilityMethods.generatePdfReport("Hotel_Book - Fail", new Font(FontFamily.COURIER,25.0f,Font.BOLD,BaseColor.RED));
						driver.close();
					}

				}catch(NoSuchElementException e){
					if(driver.getTitle().contentEquals("MakeMytrip Payment : Safe and Secure")){
						wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//span[text()='CHOOSE PAYMENT MODE']"))));
						UtilityMethods.capctureScreenShot(driver, "Hotel_Booking - Success", "Successfully Booked a Hotel");
						UtilityMethods.generatePdfReport("Hotel_Book - Success", new Font(FontFamily.COURIER,25.0f,Font.BOLD,BaseColor.GREEN));
						driver.close();
					}
				}
			}

		}

	}

}