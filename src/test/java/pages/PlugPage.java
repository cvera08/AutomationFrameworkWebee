package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.BaseSelenium;

/**
 * Created by Carlos Vera on 12/14/2017.
 */
public class PlugPage extends BasePage {

    public PlugPage(WebDriver webDriver) {
        super(webDriver, "/apps/view", "Webee Visual IoT");
    }

    /**
     * Click on ON button
     *
     * @return
     */
    public PlugPage clickOnON() {
        BaseSelenium.pressElementUsingWaits(webDriver, By.cssSelector("button[style='background-color: rgb(38, 166, 154);']"), 30);
        return this;
    }

    /**
     * Click on OFF button
     *
     * @return
     */
    public PlugPage clickOnOFF() {
        BaseSelenium.pressElementUsingWaits(webDriver, By.cssSelector("button[style='background-color: rgb(166, 38, 54);']"), 30);
        return this;
    }
}
