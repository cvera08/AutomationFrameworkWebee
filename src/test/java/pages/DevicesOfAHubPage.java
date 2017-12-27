package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.BaseSelenium;

/**
 * Created by Carlos Vera on 12/27/2017.
 */
public class DevicesOfAHubPage extends BasePage {

    public DevicesOfAHubPage(WebDriver webDriver) {
        super(webDriver, "/hubs/./devices", "Webee Visual IoT");
    }

    public DevicesOfAHubPage pressEditDevice(String deviceName) {
        BaseSelenium.pressElementUsingWaits(webDriver, By.xpath("//div[contains(text(),'" + deviceName + "')]//ancestor::div[@class='card-panel row']//button[contains(.,'Edit')]"), 5);
        return this;
    }

    /**
     * After you press Edit Device one of the option that you'll see is "DELETE" in red colour
     *
     * @param accept
     * @return
     */
    public DevicesOfAHubPage pressDeleteDevice(boolean accept) {
        By deleteSelector = By.cssSelector("a.red-text");
        BaseSelenium.pressElementUsingWaits(webDriver, deleteSelector, 5);
        if (accept)
            BaseSelenium.acceptAlertDialog(webDriver);
        BaseSelenium.fluentWaitWaitingForInvisibilityOf(webDriver, webDriver.findElement(deleteSelector), 5);
        return this;
    }

    public DevicesOfAHubPage verifyDeviceIsPresent(String deviceName, boolean displayed) {
        BaseSelenium.textInTagIsDisplayed(webDriver, "div", deviceName, true, 10, deviceName + " is displayed: " + displayed, displayed);
        return this;
    }
}
