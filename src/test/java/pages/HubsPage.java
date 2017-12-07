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
        BaseSelenium.pressElementUsingWaits(webDriver, By.tagName("button"), 5);
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
}
