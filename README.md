1.Clone the GIT repository.
<br /> 2. Adjust the selenium version in POM.xml ( currently set to 4.25.0)
<br /> 3.If the version is lower than 4.6.0 System property has to be set as: 
System.setProperty(“webdriver.chrome.driver”,”path_to_executable”); before line 38 in FlightSearch.java
<br /> 4.Right click and run the Main function .

<br /> 5.User can change the from and to airport in the respective variables fromAirport and toAirport in Flightsearch.java
<br /> 6.User has to set first 3 letters of the airport 
