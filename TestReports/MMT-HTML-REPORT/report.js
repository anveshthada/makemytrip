$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("RegisterAndLogin.feature");
formatter.feature({
  "line": 1,
  "name": "Registering an account and login into makemytrip",
  "description": "",
  "id": "registering-an-account-and-login-into-makemytrip",
  "keyword": "Feature"
});
formatter.scenario({
  "line": 3,
  "name": "User registration in makemytrip",
  "description": "",
  "id": "registering-an-account-and-login-into-makemytrip;user-registration-in-makemytrip",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 4,
  "name": "registerUser \"Registration\"",
  "keyword": "Given "
});
formatter.match({
  "arguments": [
    {
      "val": "Registration",
      "offset": 14
    }
  ],
  "location": "RegisterAndLogin.registeruser(String)"
});
formatter.result({
  "duration": 23511212299,
  "status": "passed"
});
formatter.uri("SearchAndBookBus.feature");
formatter.feature({
  "line": 1,
  "name": "Search and Book a Buses in makemytrip",
  "description": "",
  "id": "search-and-book-a-buses-in-makemytrip",
  "keyword": "Feature"
});
formatter.scenario({
  "line": 3,
  "name": "Searching available Buses between two cities",
  "description": "",
  "id": "search-and-book-a-buses-in-makemytrip;searching-available-buses-between-two-cities",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 4,
  "name": "searchBus \"\u003cexcelFile\u003e\" and \"\u003cexcelSheet\u003e\"",
  "keyword": "Given "
});
formatter.match({
  "arguments": [
    {
      "val": "\u003cexcelFile\u003e",
      "offset": 11
    },
    {
      "val": "\u003cexcelSheet\u003e",
      "offset": 29
    }
  ],
  "location": "SearchAndBookBus.searchbus_and(String,String)"
});
formatter.result({
  "duration": 27974246217,
  "status": "passed"
});
formatter.uri("SearchAndBookFlight.feature");
formatter.feature({
  "line": 1,
  "name": "Search and Book a Flight in makemytrip",
  "description": "",
  "id": "search-and-book-a-flight-in-makemytrip",
  "keyword": "Feature"
});
formatter.scenario({
  "line": 3,
  "name": "Searching available Flights between two cities",
  "description": "",
  "id": "search-and-book-a-flight-in-makemytrip;searching-available-flights-between-two-cities",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 4,
  "name": "searchFlight \"\u003cexcelFile\u003e\" and \"\u003cexcelSheet\u003e\"",
  "keyword": "Given "
});
formatter.match({
  "arguments": [
    {
      "val": "\u003cexcelFile\u003e",
      "offset": 14
    },
    {
      "val": "\u003cexcelSheet\u003e",
      "offset": 32
    }
  ],
  "location": "SearchAndBookFlight.searchflight_and(String,String)"
});
formatter.result({
  "duration": 23396922800,
  "status": "passed"
});
});