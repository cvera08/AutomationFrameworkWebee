package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.BaseSelenium;

/**
 * Created by Carlos Vera on 03/07/2018.
 */
public class DevicesHomePage extends BasePage {

    public DevicesHomePage(WebDriver webDriver) {
        super(webDriver, "/devices", "Webee Visual IoT");
    }

    public DevicesHomePage pressEditButtonForADevice(String deviceName) {
        BaseSelenium.pressElementUsingWaits(webDriver, By.xpath("//div[contains(text(),'" + deviceName + "')]//parent::div//following-sibling::div//text()[contains(.,'Edit')]/.."), 5);
        return this;
    }

    /**
     * http://prntscr.com/ioh9n8
     *
     * @return
     */
    public DevicesHomePage pressDeleteButtonForDeviceDetail() {
        BaseSelenium.pressElementUsingWaits(webDriver, By.xpath("//a[contains(text(),'Delete')]"), 5);
        return this;
    }

    /**
     * http://prntscr.com/iohar6
     *
     * @param deviceName
     * @param visible
     * @return
     */
    public DevicesHomePage verifyDeviceIsPresentInDevicesList(String deviceName, boolean visible) {
        BaseSelenium.textInTagIsDisplayed(webDriver, "div", deviceName, true, 5, deviceName, visible);
        return this;
    }

}
