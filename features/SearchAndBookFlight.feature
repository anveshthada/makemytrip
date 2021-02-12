Feature: Search and Book a flight in makemytrip

	Scenario: Searching available flights between two cities
	Given searchFlight "<excelFile>" and "<excelSheet>"
	
#	Scenario: Booking a flight 
#	Given bookFlight "Flight"