package com.makemytrip.testrunner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features={"features"},
				  glue={"com.makemytrip.stepdefinations"},
				  plugin={"pretty","html:TestReports/MMT-HTML-REPORT"})
public class TestRunner {

}
