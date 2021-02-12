package com.makemytrip.stepdefinations;

import com.makemytrip.utility.AppUtility;
import com.makemytrip.utility.PageElements;
import com.makemytrip.utility.UtilityMethods;

import cucumber.api.java.en.Given;

public class RegisterAndLogin extends PageElements {
	
	UtilityMethods utilMethods = new UtilityMethods();
	AppUtility appUtil = new AppUtility();
	
	@Given("^registerUser \"([^\"]*)\"$")
	public void registeruser(String excelSheet) throws Throwable {
		appUtil.registerUser(defaultSheetPath, "Registration");
	}

	@Given("^loginUser \"([^\"]*)\"$")
	public void loginuser(String excelSheet) throws Throwable {
		appUtil.loginUser(defaultSheetPath, "Login");
	}


}
