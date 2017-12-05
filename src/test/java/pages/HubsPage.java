package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.BaseSelenium;

/**
 * Created by Carlos Vera on 12/04/2017.
 */
public class HubsPage extends BasePage{

    public HubsPage(WebDriver webDriver) {
        super(webDriver, "/hubs", "Webee Visual IoT");
    }

    public HubsPage clickOnAddEdgeHubController(){
        BaseSelenium.pressElementUsingWaits(webDriver, By.tagName("button"), 5 );
        return this;
    }
}
