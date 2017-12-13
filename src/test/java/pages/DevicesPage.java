package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.BaseSelenium;

/**
 * Created by Carlos Vera on 12/13/2017.
 */
public class DevicesPage extends BasePage {

    public DevicesPage(WebDriver webDriver) {
        super(webDriver, "/devices", "Webee Visual IoT");
    }

    /**
     * Click on Apps option (the one in the head)
     *
     * @return
     */
    public DevicesPage clickOnApps() {
        BaseSelenium.pressElementUsingWaits(webDriver, By.cssSelector("a.btn.btn-flat.white-text"), 5);
        //to avoid StaleElementReferenceException:
        BaseSelenium.presenceOfElementUsingExplicitWait(webDriver, 5, By.cssSelector("button.waves-effect.waves-light.btn.btn-small.teal.white-text.preview-btn:nth-of-type(2)"));
        return this;
    }

    /**
     * Click on Preview for a device (in this case a Plug)
     * You will be able to see this option after you click on Apps option
     *
     * @return
     */
    public PlugPage clickOnPreviewForPlug() {
        BaseSelenium.pressElementUsingWaits(webDriver, By.cssSelector("label.hide-on-med-and-down.white-text.pointer"), 5);
        //Its opened a new tab, we need to move our focus to the new one
        BaseSelenium.moveFocusToAnotherTab(webDriver, 1);
        return new PlugPage(webDriver);
    }
}
