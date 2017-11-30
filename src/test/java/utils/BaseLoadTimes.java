package utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

/**
 * Created by Carlos Vera on 07/31/2017.
 * This class will be used to measure the Load Times of the pages
 */
public class BaseLoadTimes {

    /**
     * It takes and return the time to load the page
     */
    public static Long measurePageResponseTime(WebDriver webDriver) {
        // get the  page load time
        return (Long) ((JavascriptExecutor) webDriver).executeScript(
                "return performance.timing.loadEventEnd - performance.timing.navigationStart;");
    }
}
