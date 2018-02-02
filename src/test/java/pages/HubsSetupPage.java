package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.BaseSelenium;

/**
 * Created by Carlos Vera on 01/09/2018.
 * Page: http://prntscr.com/hy66zh
 */
public class HubsSetupPage extends BasePage {

    public HubsSetupPage(WebDriver webDriver) {
        super(webDriver, "/hubs/setup", "Webee Visual IoT");
    }

    public HubsSetupPage enterYourHubLocation(String hubLocation) {
        BaseSelenium.enterTextUsingWaits(webDriver, By.id("space"), hubLocation, 5);
        return this;
    }

    public HubsSetupPage pressDoneButton() {
        BaseSelenium.pressElementUsingWaits(webDriver, By.cssSelector("p.center.col"), 5);
        return this;
    }

    /**
     * After you press "DONE" button in hubs/setup/space Page
     * A pop up is displayed. EX: http://prntscr.com/hy6emy
     * Click on the "GO TO DEVICES" button
     *
     * @return
     */
    public DevicesOfAHubPage pressGoToDevicesButton() {
        BaseSelenium.pressElementUsingWaits(webDriver, By.xpath("//p[text()='GO TO DEVICES']"), 25);
        return new DevicesOfAHubPage(webDriver);
    }

}
