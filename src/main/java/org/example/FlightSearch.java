package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class FlightSearch {
    private By onWay;
    private final String url;
    private WebDriver driver;
    private By originAirport;
    private By destAirport;
    private By lDate;
    private By searchFlightBtn;
    private By flightSearchResult;
    private By flight;
    private By flightClass;
    private By noUpgrade;
    private WebDriverWait wait;
    private String fromAirport = "ATL";
    private String toAirport = "MIA";


    public FlightSearch() {
         onWay= By.xpath("//label[@for='flightSearchForm.tripType.oneWay']");
         url = "https://www.aa.com";
         driver= new ChromeDriver();
         originAirport= By.name("originAirport");
         destAirport= By.name("destinationAirport");
         lDate= By.id("aa-leavingOn");
         searchFlightBtn= By.id("flightSearchForm.button.reSubmit");
         flightSearchResult= By.xpath("//div[@class='results-grid-container']/child::app-slice-details");
         flight= By.xpath("//button[starts-with(@class,'btn-flight')][1]");
         flightClass= By.xpath("//div[@class='select']/button[@type='button'][1]");
         noUpgrade= By.id("btn-no-upgrade");
         wait = new WebDriverWait(driver, Duration.ofMinutes(2));

    }

    //This function is to open the desired website.
    public void openUrl() {
        driver.get(url);
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    //This select the "one-way" option on American Airlines website
    public void selectOneWay() {
        driver.findElement(onWay).click();
    }

    //This search the flight from ATL - MIA. If the user desire to change the airport
    //private vars "fromAirport" and "toAirport" can be changed.
    //Make sure that the first 3 proper letters of the airport are provided in CAPS
    //Date is set to the current date +1 day.
    public void searchFlight() {
        driver.findElement(originAirport).clear();
        driver.findElement(originAirport).sendKeys(fromAirport);
        driver.findElement(destAirport).sendKeys(toAirport);
        String leavingDate = setDate();
        driver.findElement(lDate).sendKeys(leavingDate);
        driver.findElement(searchFlightBtn).click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.className("show-for-sr"), "Choose flights page loaded"));
    }

    //This selects one of the flight from the search result only if there are search results.
    //In case there is no flight between the two location an error message will be printed.
    //Thread sleep is only for the user to see the selected flight page.
    //Once the flight and class is selected , total price for the flight will be displayed in console.
    public void selectFlight() throws InterruptedException {
        List<WebElement> flightList = driver.findElements(flightSearchResult);
        if (!flightList.isEmpty()) {
            driver.findElement(flight).click();
            wait.until(ExpectedConditions.elementToBeClickable(flightClass));
            driver.findElement(flightClass).click();
            wait.until(ExpectedConditions.elementToBeClickable(noUpgrade));
            driver.findElement(noUpgrade).click();
            wait.until(ExpectedConditions.textToBePresentInElementLocated(By.className("your-trip-summary"), "Your trip summary"));
            Thread.sleep(5000);
            String totalPrice = driver.findElement(By.xpath("//div[@class='price-info']/span[@class='amount']")).getText();
            System.out.printf("Total price for the selected flight is $%s",totalPrice);
        } else {
            System.out.println("No Flight found !!!!");
        }
    }

    //Quit browser
    public void closeBrowser() {
        driver.quit();
    }

    //Function to set the date to next day from current day.
    public String setDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        return dateFormat.format(tomorrow);
    }

    public static void main(String[] args) throws InterruptedException {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        //Setting the driver to the ChromeDriver using the Selenium Manager.

        FlightSearch Aaflightsearch = new FlightSearch();
        Aaflightsearch.openUrl();
        Aaflightsearch.selectOneWay();
        Aaflightsearch.searchFlight();
        Aaflightsearch.selectFlight();
        Aaflightsearch.closeBrowser();

    }
}
