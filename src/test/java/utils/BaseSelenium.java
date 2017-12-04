package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import java.util.concurrent.TimeUnit;

/**
 * Created by Carlos Vera on 07/21/2017.
 * This class contains some utilities of Selenium to reuse in the tests
 * It's like the utils but for Selenium methods
 */
public class BaseSelenium {

    /**
     * @param field      : Name of the field to clear the data. Just text to be displayed in the HTML report
     * @param webElement : text/input box to clear data
     */
    public static void clearTextInInputBox(String field, WebElement webElement) {
        Reporter.log("Clear " + field + " input");
        webElement.clear();
    }

    /**
     * @param elementName : Name of the button (or element) to be pressed. Just text to be displayed in the HTML report
     * @param webElement  : button (webElement actually) to be clicked
     * @deprecated use {@link #pressElementUsingWaits}  instead
     */
    @Deprecated
    public static void pressElement(String elementName, WebElement webElement) {
        Reporter.log("Click on '" + elementName + "'");
        webElement.click();
    }

    /**
     * Use this method to check if the element is displayed or not (based on the parameter visibility)
     *
     * @param webElementName : to print in the html report
     * @param webDriver      : to find the element
     * @param by             : selector type
     * @param visibility     : true or false (according what you need)
     */
    public static void isDisplayed(String webElementName, WebDriver webDriver, By by, boolean visibility) {
        Reporter.log("Verifying that '" + webElementName + "' is displayed: " + visibility);
        boolean result;
        int elementsMatching = webDriver.findElements(by).size();
        if (visibility)
            result = elementsMatching > 0; //At least one element is displayed
        else
            result = elementsMatching < 1; //None element is displayed
        Assert.assertTrue(result, "Number of Elements that are being displayed based on your parameters: " + elementsMatching);
    }

    /**
     * Generic method to select BY VISIBLE TEXT (no selectByValue) an element in a drop down
     *
     * @param webDriver
     * @param bySelect
     * @param visibleTextToSelect
     */
    public static void selectTextInSelectDropDown(WebDriver webDriver, By bySelect, String visibleTextToSelect) {
        fluentWaitWaitingElementToBeClickable(webDriver, bySelect);
        Reporter.log("Selecting : " + visibleTextToSelect + " in drop down");
        new Select(webDriver.findElement(bySelect)).selectByVisibleText(visibleTextToSelect);
    }

    /**
     * This method helps to wait until the element is present
     * It uses Explicit Wait (not Implicit or Fluent Wait)
     * If the element is not present in the UI during the timeOutInSeconds, It throws exception
     *
     * @param webDriver        : We need this for the creation of the WebDriverWait (Using the one of the page should be enough)
     * @param timeOutInSeconds : Maximum waiting time to fail if the element is not found
     * @param by               :    the way to search for it
     */
    public static WebElement presenceOfElementUsingExplicitWait(WebDriver webDriver, long timeOutInSeconds, By by) {
        return (new WebDriverWait(webDriver, timeOutInSeconds))
                .until(ExpectedConditions.presenceOfElementLocated(by));
    }

    /**
     * This method helps to wait until the element is clickable
     * It uses Fluent Wait (not Implicit or Explicit Wait)
     * If the element is not present in the UI during the timeOutInSeconds, It throws exception
     *
     * @param webDriver
     * @param by
     */
    public static void fluentWaitWaitingElementToBeClickable(WebDriver webDriver, By by) {
        new FluentWait(webDriver)
                //Specify the timeout of the wait
                .withTimeout(5000, TimeUnit.MILLISECONDS)
                //Specify polling time
                .pollingEvery(250, TimeUnit.MILLISECONDS)
                //Specify what exceptions to ignore
                .ignoring(org.openqa.selenium.NoSuchElementException.class)
                //Expected condition to accomplish
                .until(ExpectedConditions.elementToBeClickable(by));
    }

    /**
     * This method helps to wait until a certain element is not visible
     * It uses Fluent Wait (not Implicit or Explicit Wait)
     * If the element is not present in the UI during the timeOutInSeconds, It throws exception
     *
     * @param webDriver
     * @param webElement
     * @param timeOutInSeconds
     */
    public static void fluentWaitWaitingForInvisibilityOf(WebDriver webDriver, WebElement webElement, long timeOutInSeconds) {
        new FluentWait(webDriver)
                //Specify the timeout of the wait
                .withTimeout(timeOutInSeconds, TimeUnit.SECONDS)
                //Specify polling time
                .pollingEvery(250, TimeUnit.MILLISECONDS)

                .until(ExpectedConditions.invisibilityOf(webElement));
    }

    /**
     * Press Accept Button in Alert Dialog
     *
     * @param webDriver
     */
    public static void acceptAlertDialog(WebDriver webDriver) {
        Reporter.log("Press Accept Button in Alert Dialog");
        hardcodedDelay(1000); //Hardcode sleep to wait 1s until Dialog/Alert is displayed
        webDriver.switchTo().alert().accept();
    }

    /**
     * Dismiss Alert Dialog
     *
     * @param webDriver
     */
    public static void dismissAlertDialog(WebDriver webDriver) {
        Reporter.log("Press Accept Button in Alert Dialog");
        hardcodedDelay(1000); //Hardcode sleep to wait 1s until Dialog/Alert is displayed
        webDriver.switchTo().alert().dismiss();
    }

    /**
     * It waits the time passed as parameter
     * This method should be avoided whenever you can because in general introduces false failures
     * It's preferred to use Fluent or Explicit Wait instead of this method
     *
     * @param millis
     */
    public static void hardcodedDelay(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Use this method to clik on some element. e.g. button, checkbox, etc
     * Wait for presence of element & element is clickable. Move to element
     *
     * @param webDriver
     * @param by
     * @param timeOutInSeconds
     */
    public static void pressElementUsingWaits(WebDriver webDriver, By by, long timeOutInSeconds) {
        //wait for presence of element
        presenceOfElementUsingExplicitWait(webDriver, timeOutInSeconds, by);

        //wait for clickable. FE: the element is present but there is a loading over the element/page
        fluentWaitWaitingElementToBeClickable(webDriver, by);

        WebElement webElement = webDriver.findElement(by);

        //Move To Element (Scroll). To avoid unknown error: Element is not clickable at point (X, Y)
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(false);", webElement);

        //perform the click
        Reporter.log("Click on '" + webElement.getText() + "'");
        webElement.click();
    }

    /**
     * Use this method to enter some value in a component. e.g. textbox
     * Wait for presence of element, Move to element & clear the previous text/value are included
     *
     * @param webDriver
     * @param by
     * @param text
     * @param timeOutInSeconds
     */
    public static void enterTextUsingWaits(WebDriver webDriver, By by, String text, long timeOutInSeconds) {
        //wait for presence of element
        presenceOfElementUsingExplicitWait(webDriver, timeOutInSeconds, by);

        WebElement webElement = webDriver.findElement(by);

        //Move To Element (Scroll)
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(false);", webElement);

        //enter the value
        webElement.clear();
        Reporter.log("Typing: " + text + " in '" + by + "'");
        webElement.sendKeys(text);
    }
}
