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

    /**
     * http://prntscr.com/j0dz17
     *
     * @return
     */
    public DevicesHomePage pressAddDeviceButton() {
        BaseSelenium.pressElementUsingWaits(webDriver, By.xpath("//button[contains(text(),'Add device')]"), 5);
        return this;
    }

    /**
     * http://prntscr.com/j0e0lo
     *
     * @param pairingMethodName
     * @return
     */
    public DevicesHomePage selectAPairingMethod(String pairingMethodName) {
        BaseSelenium.pressElementUsingWaits(webDriver, By.xpath("//div[contains(text(),'" + pairingMethodName + "')]"), 5);
        return this;
    }

    /**
     * http://prntscr.com/j0f9oz
     *
     * @param componentName
     * @return
     */
    public DevicesHomePage selectNewDeviceComponent(String componentName) {
        By optionToSelect = By.xpath("//span[text()='" + componentName + "']");
        BaseSelenium.presenceOfElementUsingExplicitWait(webDriver, 5, optionToSelect);
        //Not able to use the selectTextInSelectDropDown (it doesn't work)
        BaseSelenium.hardcodedDelay(1000);
        BaseSelenium.pressElementUsingWaits(webDriver, By.xpath("(//span[@class='caret'])[1]//parent::div"), 5); //open the drop down
        BaseSelenium.pressElementUsingWaits(webDriver, optionToSelect, 5); //Select the desired option
        return this;
    }

    /**
     * http://prntscr.com/j0e2ot
     *
     * @param deviceName
     * @return
     */
    public DevicesHomePage enterNewDeviceName(String deviceName) {
        BaseSelenium.enterTextUsingWaits(webDriver, By.id("deviceNameId"), deviceName, 5);
        return this;
    }

    /**
     * http://prntscr.com/j0f9x6
     *
     * @param connectionName
     * @return
     */
    public DevicesHomePage selectNewDeviceConnection(String connectionName) {
        //Not able to use the selectTextInSelectDropDown (it doesn't work)
        BaseSelenium.pressElementUsingWaits(webDriver, By.xpath("(//span[@class='caret'])[3]//parent::div"), 5); //open the drop down
        BaseSelenium.pressElementUsingWaits(webDriver, By.xpath("//span[text()='" + connectionName + "']"), 5); //Select the desired option
        return this;
    }

    /**
     * http://prntscr.com/j0fb1n
     *
     * @return
     */
    public DevicesHomePage pressSaveButtonForNewDevice() {
        BaseSelenium.pressElementUsingWaits(webDriver, By.tagName("button"), 5);
        return this;
    }
}
