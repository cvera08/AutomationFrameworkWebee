package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import java.util.ArrayList;
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
     * Use this method to check if the element is displayed or not (based on the parameter visibility)
     *
     * @param webElementName : to print in the html report
     * @param webDriver      : to find the element
     * @param by             : selector type
     * @param visibility     : true or false (according what you need)
     * @deprecated use {@link #isDisplayedUsingWaits  instead
     */
    @Deprecated
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
        fluentWaitWaitingElementToBeClickable(webDriver, bySelect, 5);
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
    private static void fluentWaitWaitingElementToBeClickable(WebDriver webDriver, By by, long timeOutInSeconds) {
        new FluentWait(webDriver)
                //Specify the timeout of the wait
                .withTimeout(timeOutInSeconds, TimeUnit.SECONDS)
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
     * Use this method to click on some element. e.g. button, checkbox, etc
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
        fluentWaitWaitingElementToBeClickable(webDriver, by, timeOutInSeconds);

        WebElement webElement = webDriver.findElement(by);

        //Move To Element (Scroll). To avoid unknown error: Element is not clickable at point (X, Y)
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(false);", webElement);

        //perform the click
        Reporter.log("Click on '" + webElement.getText() + "'");
        webElement.click();
    }

    /**
     * Use this method to click on some element when you can't use @link{pressElementUsingWaits} because it returns:
     * >> "the element xxxxx is not clickable at point (X, Y). Other element would receive the click"
     * in real time execution
     * Wait for presence of element & element is clickable. Move to element & click using JS
     *
     * @param webDriver
     * @param by
     * @param timeOutInSeconds
     * @deprecated use {@link #pressElementUsingJavaScriptWithScrollIntoView  instead}
     */
    public static void pressElementUsingJavaScript(WebDriver webDriver, By by, long timeOutInSeconds) {
        //wait for presence of element
        presenceOfElementUsingExplicitWait(webDriver, timeOutInSeconds, by);

        //wait for clickable. FE: the element is present but there is a loading over the element/page
        fluentWaitWaitingElementToBeClickable(webDriver, by, timeOutInSeconds);

        WebElement webElement = webDriver.findElement(by);

        //Move To Element (Scroll). To avoid unknown error: Element is not clickable at point (X, Y)
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(false);", webElement);

        //perform the click
        Reporter.log("Click on '" + webElement.getText() + "'");
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click()", webElement);
    }

    /**
     * Use this method to click on some element when you can't use @link{pressElementUsingWaits} because it returns:
     * >> "the element xxxxx is not clickable at point (X, Y). Other element would receive the click"
     * in real time execution
     * Wait for presence of element & element is clickable. Move to element & click using JS
     *
     * @param webDriver
     * @param by
     * @param scrollIntoView: true or false
     */
    public static void pressElementUsingJavaScriptWithScrollIntoView(WebDriver webDriver, By by, long timeOutInSeconds, boolean scrollIntoView) {
        //wait for presence of element
        presenceOfElementUsingExplicitWait(webDriver, timeOutInSeconds, by);

        //wait for clickable. FE: the element is present but there is a loading over the element/page
        fluentWaitWaitingElementToBeClickable(webDriver, by, timeOutInSeconds);

        WebElement webElement = webDriver.findElement(by);

        //Move To Element (Scroll). To avoid unknown error: Element is not clickable at point (X, Y)
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(" + scrollIntoView + ");", webElement);

        //perform the click
        Reporter.log("Click on '" + webElement.getText() + "'");
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click()", webElement);
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

    /**
     * Use this method to check if the element is displayed or not (based on the parameter visibility)
     *
     * @param webDriver        : to find the element
     * @param by               : selector type
     * @param timeOutInSeconds : time to wait until it fails
     * @param webElementName   : to print in the html report
     * @param visibility       : true or false (according what you need)
     */
    public static void isDisplayedUsingWaits(WebDriver webDriver, By by, long timeOutInSeconds, String webElementName, boolean visibility) {
        Reporter.log("Verifying that '" + webElementName + "' is displayed: " + visibility);
        //To avoid errors "stale element reference: element is not attached to the page document" in execution time
        if (visibility) {
            //wait for presence of the element
            presenceOfElementUsingExplicitWait(webDriver, timeOutInSeconds, by);
        } else {
            int elementsMatching = webDriver.findElements(by).size();
            //(elementsMatching < 1) == None element is displayed
            Assert.assertTrue(elementsMatching < 1, "Number of Elements that are being displayed based on your parameters: " + elementsMatching + ". ");
        }
    }

    /**
     * Use this method to check if the element is displayed or not (based on the parameter visibility)
     *
     * @param webDriver        : to find the element
     * @param tagName          : the one to be used in the selector
     * @param text             : the text to be searched for
     * @param contains         : if the selector should be used contains or text()='xxxxx'
     * @param timeOutInSeconds : time to wait until it fails
     * @param webElementName   : to print in the html report
     * @param visibility       : true or false (according what you need)
     */
    public static void textInTagIsDisplayed(WebDriver webDriver, String tagName, String text, boolean contains, long timeOutInSeconds, String webElementName, boolean visibility) {
        By selector;
        if (contains)
            selector = By.xpath("//" + tagName + "[contains(text(), '" + text + "')]");
        else
            selector = By.xpath("//" + tagName + "[text() = '" + text + "')]");
        isDisplayedUsingWaits(webDriver, selector, timeOutInSeconds, webElementName, visibility);
    }

    /**
     * When you click on a link that is opened in a new tab, we need to move the selenium's focus to the new tab if we want to work on it
     *
     * @param tabNumber: current=0, new=1
     */
    public static void moveFocusToAnotherTab(WebDriver webDriver, int tabNumber) {
        ArrayList<String> tabs = new ArrayList<String>(webDriver.getWindowHandles());
        webDriver.switchTo().window(tabs.get(tabNumber));
    }

    /**
     * Use this method to open the link in a new tab
     * Keep in mind where is the focus of your selenium webdriver
     *
     * @param webDriver
     * @param by
     * @param timeOutInSeconds
     */
    public static void openLinkInANewTab(WebDriver webDriver, By by, int timeOutInSeconds) {
        presenceOfElementUsingExplicitWait(webDriver, timeOutInSeconds, by);
        String selectLinkOpeninNewTab = Keys.chord(Keys.CONTROL, Keys.RETURN);
        webDriver.findElement(by).sendKeys(selectLinkOpeninNewTab);
    }

    /**
     * Open alert message while the automation is being ran
     * You can use this, to inform to the user about certain things or to request some manual actions
     * <p>
     * JavaScript has three kind of popup boxes: Alert box, Confirm box, and Prompt box.
     * alert('Hello\\nHow are you?');
     * confirm('Press a button!');
     * prompt('Please enter your name');
     * https://www.w3schools.com/js/js_popup.asp
     *
     * @param webDriver
     * @param alertMessage
     */
    public static void injectAlert(WebDriver webDriver, String alertMessage) {
        ((JavascriptExecutor) webDriver).executeScript("alert('" + alertMessage + "');");
    }

    /**
     * To continue after the alert is dismissed
     * FE: to use after {@link #injectAlert}
     *
     * @param webDriver
     * @param checkByMillis
     */
    public static void alertIsNotPresentAnymore(WebDriver webDriver, long checkByMillis) {
        boolean present = true;
        while (present) {
            try {
                webDriver.switchTo().alert();
            } catch (NoAlertPresentException n) {
                present = false;
                System.out.println("<<NoAlertPresentException>>"); //Just to help in the debug
                hardcodedDelay(checkByMillis);
            } catch (Exception e) {
                present = false;
                System.out.println("<<Exception>>" + e.toString()); //Just to help in the debug
                hardcodedDelay(checkByMillis);
            }
        }
    }


}
