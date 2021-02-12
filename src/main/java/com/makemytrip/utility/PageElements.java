package com.makemytrip.utility;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.xpath.XPathExpression;

import org.openqa.selenium.WebDriver;


public class PageElements {

	public static WebDriver driver;
	public static String defaultSheetPath = "F:\\Selenium Practise\\makemytrip\\ExcelFiles\\MakeMyTrip_Data.xlsx";
	public static String defaultSheetName = "Configuration";
	public static String chromeDriverPath;
	public static String mozillaDriverPath;
	public static String ieDriverPath;
	public static String appUrl;
	public static String testResultPath;
	public static String testBrowser;
	public static String screensPath;
	public static Map<File, String> pdfReportMap = new LinkedHashMap<File, String>();

	public static String signUpLink = "//a[contains(@id,'ch_signup_icon')]";
	public static String loginLink = "//a[contains(@id,'ch_login_icon')]//span[2]";
	public static String makeMyTripLogo = "//img[contains(@class,'mmt_header_logo')]";
	public static String flights = "//a[contains(@class,'hdr_item_link') and contains(text(),'flights')]";
	public static String hotels = "//a[contains(@class,'hdr_item_link') and contains(text(),'hotels')]";
	public static String holidays = "//a[contains(@class,'hdr_item_link') and contains(text(),'holidays')]";
	public static String bus = "//a[contains(@class,'hdr_item_link') and contains(text(),'bus')]";
	public static String rail = "//a[contains(@class,'hdr_item_link') and contains(text(),'rail')]";
	public static String flight_Hotel = "//a[contains(@class,'hdr_item_link') and contains(text(),'Flight+Hotel')]";
	public static String more = "//a[contains(@id,'ch_funnel_more') ]";
	public static String giftCards = "//a[contains(@id,'header_tab_gift-card') ]";
	public static String deals = "//a[contains(@id,'ch_logged-intrip') and contains(@class,'hdr_item_link')]";
	public static String stories = "//a[contains(@id,'ch_logged-inprinttkt') and contains(@class,'hdr_item_link')]";
	public static String rightStay = "//a[contains(@id,'ch_logged-incancel') and contains(@class,'hdr_item_link')]";

	// registration objects
	public static String email = "//div[contains(@class,'inputM')]//input[contains(@id,'ch_signup_email')]";
	public static String mobile = "//input[contains(@id,'ch_signup_phone')]";
	public static String password = "//input[contains(@id,'ch_signup_password')]";
	public static String err_Email = "//div[contains(@id,'ch_signup_email_error')]//span[2]";
	public static String err_Email_Exists = "//div[@id='ch_signup_error']";
	public static String err_Mobile = "//div[contains(@id,'ch_signup_phone_error')]//span[2]";
	public static String err_Password = "//div[contains(@id,'ch_signup_password_error')]//span[2]";
	public static String having_account = "//a[contains(@id,'ch_signup_haveaccount')]";
	public static String signUpButton = "//button[contains(@id,'ch_signup_btn')]";

	// ************************************* Login Objects - Start ***************************************
	public static String login_email = "//input[contains(@id,'ch_login_email')]";
	public static String err_login_email = "//div[contains(@id,'ch_login_email_error')]//span[2]";
	public static String login_password = "//input[contains(@id,'ch_login_password')]";
	public static String err_login_password = "//div[contains(@id,'ch_login_password_error')]//span[2]";
	public static String loginButton = "//button[contains(@id,'ch_login_btn')]";
	// ************************************* Login Objects - End ***************************************

	// ************************************* Logout Objects - Start ***************************************
	public static String logout_Icon = "//a[@id='ch_logged-in']";
	public static String logout = "//ul[@class='ch__profileOverlayTabs ch__capitalize']//a[text()='Logout']";
	// ************************************* Logout Objects - End ***************************************


	// ************************* flight objects - Start ************************
	public static String oneWay = "//label[contains(@class,'label_text flight-trip-type')]";
	public static String roundTrip = "//label[contains(@class,'label_text') and contains(text(),'round trip')]";
	public static String multiCity = "//label[contains(@class,'label_text') and contains(text(),'multicity')]";
	public static String fromCity = "//input[contains(@id,'hp-widget__sfrom')]";
	public static String fromCity_Options = "//span[contains(@class,'autoCompleteItem__label')]";
	public static String toCity = "//input[contains(@id,'hp-widget__sTo')]";
	public static String toCity_Options = fromCity_Options;
	public static String depDate = "//input[contains(@id,'hp-widget__depart')]";
	public static String depDate_Month = "//span[contains(text(),'March')]";
	public static String deptDate_Options = "//table[contains(@class,'ui-datepicker-calendar')]//tr//td//a";
	public static String returnDate = "//input[contains(@id,'hp-widget__return')]";
	public static String Passengers = "//input[contains(@id,'hp-widget__paxCounter')]";
	public static String pass_Adult = "//ul[contains(@id,'js-adult_counter')]//li";
	public static String pass_Child = "//ul[contains(@id,'js-child_counter')]//li";
	public static String pass_infrant = "//ul[contains(@id,'js-infant_counter')]//li";
	public static String classOfTravel = "//input[contains(@id,'hp-widget__class')]";
	public static String class_Filters = "//div[contains(@id,'js-classFilters')]//label";
	public static String multicity_from = "//input[contains(@id,'js-multiCitySearchFrom_1')]";
	public static String multicity_to = "//input[contains(@id,'js-multiCitySearchTo_1')]";
	public static String multicity_dept_Date = "//input[contains(@class,'multiCitySearchDepart1')]";
	public static String multicity_AddCity = "//button[contains(@id,'addModifyBtn')]";
	public static String flightSearchButton = "//button[contains(@id,'searchBtn')]";
	public static String err_fromCity = "//div[contains(@id,'hp-widget__sFrom_invalid_error')]//span[2]";
	public static String err_toCity = "//div[contains(@id,'hp-widget__sTo_invalid_error')]//span[2]";
	public static String err_Return_Date = "//div[contains(@id,'hp-widget__sReturnDate_invalid_error')]//span[2]";


	public static String flight_Book_Button = "//*[text()='Air India']//ancestor::div[contains(@class,'fli-list-heading-section clearfix')]//a[contains(text(),'Book Now')]";
	public static String flight_Book_Checkbox = "//span[contains(@class,'checkbox_state_review pull-left active')]";
	public static String flight_Book_Checkbox_Alert = "//div[contains(@class,'overlay_cases clearfix ng-scope')]";
	public static String flight_Book_Continue_Button = "//div[contains(@ng-show,'isUserLoggedIn ||isOdcFlow')]//a";
	public static String flight_Book_GivenName = "//input[contains(@placeholder,'Given Name')]";
	public static String flight_Book_Err_GivenName = "//span[contains(@ng-i18next,'Given name cannot be left blank')]";
	public static String flight_Book_Surname = "//input[contains(@placeholder,'Surname')]";
	public static String flight_Book_Nationality = "//select[contains(@ng-model,'traveller.nationality')]";
	public static String flight_Book_Nationality_Options = "//select[contains(@ng-model,'traveller.nationality')]//option";
	public static String flight_Book_Mobile = "//input[contains(@maxlength,'10')]";
	public static String flight_Book_Err_Mobile = "//span[contains(@class,'error_icon_txtinfo pull-left contactErrorPadLeft col-xs-12 ng-binding')]";
	public static String flight_Book_PaymentButton = "//button[contains(@ng-disabled,'isPaymentDisabled')]";
	public static String flight_Book_Email = "//input[contains(@id,'inputEmailIdForLoggin')]";
	public static String flight_Book_Err_Email = "//span[contains(text(),'Please enter a valid email address')]";
	public static String flight_Book_ContinueAsGuest_Button = "//a[contains(@ng-i18next,'Continue as Guest')]";


	// ************************* flight objects - End ************************


	// ************************* Bus objects - Start ************************

	public static String bus_Dep_City = "//input[contains(@id,'from_city_typeahead')]";
	public static String bus_Dep_City_Text = "//div[contains(@class,'tt-dataset-bus_from_city_list')]";
	public static String bus_Arrv_City = "//input[contains(@id,'to_city_typeahead')]";
	public static String bus_Arrv_City_Text = "//div[contains(@class,'tt-dataset-bus_to_city_list')]";
	public static String bus_Dep_DatePicker = "//a[contains(@id,'start_date_sec')]";
	public static String bus_Dep_Date_Month = depDate_Month;
	public static String bus_Dep_Date_Month_Next = "//a[contains(@data-handler,'next')]";
	public static String bus_Dep_Date_Options = "//table[contains(@class,'ui-datepicker-calendar')]//tr//td[contains(@data-event,'click')]";
	public static String bus_Email = "//input[contains(@id,'bus_email')]";
	public static String bus_SearchButton = "//a[contains(@id,'submit_button_bus')]";
	public static String bus_Err_Dep_City = "//span[contains(@id,'alert_span')]";

	public static String bus_Book_BoardingPoint = "//select[contains(@id,'boarding_ptdbl_select')]";
	public static String bus_Book_BoardingPoint_Options = "//select[contains(@id,'boarding_ptdbl_select')]//option";
	public static String bus_Book_SelectSeats_Button_Sleeper = ".//*[@id='selectSeatsdbl']";
	public static String bus_Book_SelectSeats_Button_Seater = ".//*[@id='selectSeats']";
	public static String bus_Book_SelectSeat_Alert = "//div[@class='modal-body paddTB15']//a";

	public static String bus_Book_FirstName = "//input[starts-with(@id,'frstName_trvlr')]";
	public static String bus_Book_Err_FirstName = "//input[starts-with(@id,'frstName_trvlr')]//following::span[contains(text(),'First name is required')]";
	public static String bus_Book_LastName = "//input[starts-with(@id,'lastName_trvlr')]";
	public static String bus_Book_Err_LastName = "//input[starts-with(@id,'lastName_trvlr')]//following::span[contains(text(),'Last name is required')]";
	public static String bus_Book_gender_Male = "//a[starts-with(@id,'genderM_trvlr')]";
	public static String bus_Book_gender_Female = "//a[starts-with(@id,'genderF_trvlr')]";
	public static String bus_Book_Age = "//input[starts-with(@id,'age_trvlr')]";
	public static String bus_Book_Err_Age = "//input[starts-with(@id,'age_trvlr')]//following::span[contains(text(),'Age is required')]";
	public static String bus_Book_Email = "//input[contains(@id,'user_emailId')]";
	public static String bus_Book_Err_Email = "//div[@class='alert alert-danger error_case ng-active']";
	public static String bus_Book_Mobile = "//input[contains(@id,'user_mobNo')]";
	public static String bus_Book_Err_Mobile = "//input[contains(@id,'user_mobNo')]//following::span[contains(text(),'Mobile number is required.')]";
	public static String bus_Book_ContinueButton = "//button[contains(text(),'Continue as Guest')]";
	public static String bus_Book_SameName_Err_Button = "//div[contains(@class,'modal-body paddTB15')]//a";
	// ************************* Bus objects - End ************************


	// ************************* Hotel objects - Start ************************
	public static String domestic_Hotels = "//a[contains(@href,'//www.makemytrip.com/hotels') and contains(text(),'domestic')]";
	public static String international_Hotels = "//a[contains(@href,'//www.makemytrip.com/hotels-international') and contains(text(),'international')]";
	public static String hotel_City = "//input[contains(@id,'hp-widget__sDest')]";
	public static String hotel_City_Option = "//span[contains(@class,'autoCompleteItem__label')]";
	public static String hotel_checkIn = "//input[contains(@id,'hp-widget__chkIn')]";
	public static String hotel_Err_CheckIn = ".//*[@id='htl_widget_chkIn_error']";
	public static String hotel_checkOut = "//input[contains(@id,'hp-widget__chkOut')]";
	public static String hotel_rooms_Guests = "//input[contains(@id,'hp-widget__paxCounter')]";
	public static String hotel_adultsInRoom = "//div[contains(@id,'js-roomOption__details')]//ul[contains(@id,'js-adult_counter')]//li";
	public static String hotel_childsInRoom = "//div[contains(@id,'js-roomOption__details')]//ul[contains(@id,'js-child_counter')]//li";
	public static String hotel_addRoom = "//button[contains(@id,'js-addGuestRoom')]";
	public static String hotel_adv_RoomSearch = "//a[contains(@class,'hp-widget__advLink')]";
	public static String hotel_priceRange  = "//input[contains(@id,'hp-widget__price')]";
	public static String hotel_Searchbutton = "//button[@id='searchBtn']"; 
	public static String hotel_ContinueAsGuest_Button = "//a[contains(@class,'btn btn-lg btn-block btn-secondary')]";
	public static String hotel_Book_FirstName = "//input[contains(@id,'firstName')]";
	public static String hotel_Book_Err_FirstName = "//p[contains(@class,'help-block ng-hide')]";
	public static String hotel_Book_LastName = "//input[contains(@id,'lastName')]";
	public static String hotel_Book_Err_LastName = "//p[contains(@class,'help-block ng-hide')]";
	public static String hotel_Book_Email = "//input[contains(@id,'email')]";
	public static String hotel_Book_Err_Email = "//p[contains(@class,'help-block ng-hide')]";
	public static String hotel_Book_Mobile = "//input[contains(@id,'mobile_traveller')]";
	public static String hotel_Book_Err_Mobile = "//p[contains(@class,'help-block ng-hide')]";
	public static String hotel_Book_Bookingmyself_Checkbox = "//input[contains(@class,'ng-pristine ng-valid')]";
	public static String hotel_Book_AgreeTerms_Checkbox = "//input[contains(@class,'ng-pristine ng-valid ng-valid-required')]";
	public static String hotel_Book_MMVWallet_Checkbox = "//input[contains(@class,'ng-pristine ng-valid') and @ng-model='amex']";
	public static String hotel_Book_PaymentButton = "//button[contains(@class,'btn btn-block btn-lg btn-primary-red')]";
	// ************************* Hotel objects - End ************************



}
