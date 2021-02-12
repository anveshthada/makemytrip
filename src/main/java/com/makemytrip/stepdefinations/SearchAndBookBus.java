package com.makemytrip.stepdefinations;

import com.makemytrip.utility.AppUtility;
import com.makemytrip.utility.PageElements;
import com.makemytrip.utility.UtilityMethods;

import cucumber.api.java.en.Given;

public class SearchAndBookBus extends PageElements{

	UtilityMethods utilMethods = new UtilityMethods();
	AppUtility appUtil = new AppUtility();
	
	@Given("^searchBus \"([^\"]*)\" and \"([^\"]*)\"$")
	public void searchbus_and(String arg1, String arg2) throws Throwable {
		appUtil.searchBus(defaultSheetPath, "Bus");
	}

	@Given("^bookBus \"([^\"]*)\" and \"([^\"]*)\"$")
	public void bookbus_and(String arg1, String arg2) throws Throwable {
	}

	
}
