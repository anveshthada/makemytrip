package com.makemytrip.stepdefinations;

import com.makemytrip.utility.AppUtility;
import com.makemytrip.utility.PageElements;
import com.makemytrip.utility.UtilityMethods;

import cucumber.api.java.en.Given;

public class SearchAndBookFlight extends PageElements{
	
	UtilityMethods utilMethods = new UtilityMethods();
	AppUtility appUtil = new AppUtility();
		
	@Given("^searchFlight \"([^\"]*)\" and \"([^\"]*)\"$")
	public void searchflight_and(String excelFile, String excelSheet) throws Throwable {
		appUtil.searchFlight(defaultSheetPath, "Flight");
	}

	/*@Given("^bookFlight \"([^\"]*)\" and \"([^\"]*)\"$")
	public void bookflight_and(String excelFile, String excelSheet) throws Throwable {
		appUtil.bookFlight(defaultSheetPath, "Flight");
	}*/

}
