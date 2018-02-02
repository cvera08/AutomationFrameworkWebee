package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.BaseSelenium;

/**
 * Created by Carlos Vera on 12/04/2017.
 */
public class HubsPage extends BasePage {

    public HubsPage(WebDriver webDriver) {
        super(webDriver, "/hubs", "Webee Visual IoT");
    }

    public HubsPage clickOnAddEdgeHubController() {
        BaseSelenium.pressElementUsingWaits(webDriver, By.cssSelector("button.waves-teal"), 5);
        return this;
    }

    public HubsPage selectFirstAccountOffered() {
        BaseSelenium.pressElementUsingWaits(webDriver, By.cssSelector("#hubModal strong"), 5);
        return this;
    }

    public HubsPage enterHubsMacAddress(String alphanumericCode) {
        BaseSelenium.enterTextUsingWaits(webDriver, By.id("macAddress"), alphanumericCode, 5);
        return this;
    }

    public HubsPage pressReadyButton() {
        BaseSelenium.pressElementUsingWaits(webDriver, By.cssSelector("div p"), 5);
        return this;
    }

    public HubsPage verifyMessageIsDisplayed(Messages message, boolean visible) {
        BaseSelenium.isDisplayed("MAC address can not be empty", webDriver, By.xpath("//div[@id='toast-container']//div[contains(text(),'" + message.getValue() + "')]"), visible);
        return this;
    }

    public static enum Messages {
        MAC_ADDRESS_CAN_NOT_BE_EMPTY("MAC address can not be empty"),
        MAC_ADDRESS_MUST_HAVE_12_CHARACTERES("MAC address must have 12 characteres");
        private String value;

        private Messages(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public DevicesOfAHubPage selectDevicesOptionFromAHub(String macAddress) {
        BaseSelenium.pressElementUsingWaits(webDriver, By.xpath("//strong[contains(text(),'" + macAddress + "')]//ancestor::div[@class='row hub-top']//button[contains(text(),'Devices')]"), 5);
        return new DevicesOfAHubPage(webDriver);
    }

    /**
     * After you enter the Hub Mac Address & Press Ready button is displayed the Connect Page. EX: http://prntscr.com/hthfb6
     * It checks if the "Connecting your Edge Hub Controller" text is displayed or not
     *
     * @param visible
     * @return
     */
    public HubsPage verifyConnectingYourEdgeHubControllerIsDisplayed(boolean visible) {
        BaseSelenium.textInTagIsDisplayed(webDriver, "h5", "Connecting your Edge Hub Controller", true, 15, "Connecting your Edge Hub Controller", visible);
        return this;
    }

    /**
     * After you enter the Hub Mac Address & Press Ready button is displayed the Connect Page. EX: http://prntscr.com/hthfb6
     * Click on the green button
     *
     * @return
     */
    public HubsPage pressMyHubIsReadyButton() {
        BaseSelenium.pressElementUsingWaits(webDriver, By.cssSelector("a.btn"), 5);
        return this;
    }

    /**
     * After you press "My Hub Is Ready" button in the Connect Page
     * You are in the Connecting Page. EX: http://prntscr.com/hthhyj
     * Click on the "using ethernet cable" button
     *
     * @return
     */
    public HubsPage pressUsingEthernetCableButton() {
        BaseSelenium.pressElementUsingWaits(webDriver, By.xpath("//a[contains(text(),'using ethernet cable')]"), 5);
        return this;
    }

    /**
     * After you press "My Hub Is Ready" button in the Connect Page
     * You are in the Connecting Page. EX: http://prntscr.com/hthhyj
     * Click on the "using wi-fi network" button
     *
     * @return
     */
    public HubsPage pressUsingWifiNetworkButton() {
        BaseSelenium.pressElementUsingWaits(webDriver, By.xpath("//a[contains(text(),'using wi-fi network')]"), 5);
        return this;
    }

    /**
     * After you press "using ethernet cable" or "using wi-fi network" button in the Connecting Page
     * You are in the CONNECT ETHERNET/WIFI Page. EX: http://prntscr.com/hthno6
     * Click on the "READY" button
     *
     * @return
     */
    public HubsPage presReadyButtonInConnectEthernetOrWifi() {
        BaseSelenium.pressElementUsingWaits(webDriver, By.cssSelector("button.offset-l1"), 5);
        return this;
    }

    /**
     * After you press "Ready" button in the CONNECT ETHERNET/WIFI Page
     * You are in the CONNECT ETHERNET/WIFI Page, a pop up is displayed. EX: http://prntscr.com/hy63ai
     * It checks if the "Your hub is now connected." text is displayed or not
     *
     * @param visible
     * @return
     */
    public HubsPage verifyYourHubIsNowConnectedIsDisplayed(boolean visible) {
        BaseSelenium.textInTagIsDisplayed(webDriver, "h5", "Your Hub is now connected", true, 120, "Your hub is now connected", visible);
        return this;
    }

    /**
     * After you press "Ready" button in the CONNECT ETHERNET Page
     * You are in the CONNECT ETHERNET/WIFI Page, a pop up is displayed. EX: http://prntscr.com/hy63ai
     * Click on the "CONTINUE" button
     *
     * @return
     */
    public HubsSetupPage pressContinueButtonInPopupForConnectEthernetOrWifi() {
        BaseSelenium.pressElementUsingWaits(webDriver, By.xpath("//button[text()='Continue']"), 5);
        return new HubsSetupPage(webDriver);
    }

    /**
     * Use this method when you need to click on some button that contains some text
     *
     * @param text
     * @return
     */
    public HubsPage pressButtonWithText(String text) {
        BaseSelenium.pressElementUsingWaits(webDriver, By.xpath("//button[contains(text(),'" + text + "')]"), 5);
        return this;
    }

    /**
     * Use this method when you need to click on some div that contains some text
     *
     * @param text
     * @return
     */
    public HubsPage pressDivWithText(String text) {
        BaseSelenium.pressElementUsingWaits(webDriver, By.xpath("//div[contains(text(),'" + text + "')]"), 5);
        return this;
    }

    /**
     * Enter Wifi password after you select wifi connection & the network that you need to be connected to
     *
     * @param wifiPassword
     * @return
     */
    public HubsPage enterWifiPassword(String wifiPassword) {
        BaseSelenium.enterTextUsingWaits(webDriver, By.id("wifi-password"), wifiPassword, 5);
        return this;
    }

    /**
     * Use this method to verify that H5 tag contains certain text
     *
     * @param message
     * @param visible
     * @return
     */
    public HubsPage verifyTextIsDisplayedInH5Tag(String message, boolean visible) {
        BaseSelenium.textInTagIsDisplayed(webDriver, "h5", message, true, 5, message, visible);
        return this;
    }

}
