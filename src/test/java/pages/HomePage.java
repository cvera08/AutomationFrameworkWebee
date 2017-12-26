package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import utils.BaseSelenium;

/**
 * Created by Carlos Vera on 08/03/2017.
 */

public class HomePage extends BasePage {

    /**
     * Options available are:  Enterprises, Projects, Linked accounts, Connections, etc
     */
    private String mainSelectorForHomeOption = "//div[text()="; //To be easier to maintain (and avoid duplications)

    public HomePage(WebDriver webDriver) {
        super(webDriver, "/login", "Webee Visual IoT");
    }

    @Override
    public HomePage navigateToPage() {
        Reporter.log("Navigating to Home Page");
        return (HomePage) super.navigateToPage();
    }

    /**
     * Verify that you are in Landing Page
     * Validation about if the page is loaded (using one component of the page)
     *
     * @return
     */
    public HomePage homePageIsDisplayed() {
        Reporter.log("Verifying that 'Webee Visual IoT' title is displayed");
        BaseSelenium.presenceOfElementUsingExplicitWait(webDriver, 5, By.xpath("//title[contains(text(),'Webee Visual IoT')]"));
        return this;
    }

    /**
     * Click on "Hubs" option in the Home Page
     *
     * @return
     */
    public HubsPage clickOnHubs() {
        BaseSelenium.pressElementUsingWaits(webDriver, By.xpath(mainSelectorForHomeOption + "'Hubs']"), 5);
        return new HubsPage(webDriver);
    }

    /**
     * Click on "Projects" option in the Home Page
     *
     * @return
     */
    public ProjectsPage clickOnProjects() {
        BaseSelenium.pressElementUsingWaits(webDriver, By.xpath(mainSelectorForHomeOption + "'Projects']"), 10);
        return new ProjectsPage(webDriver);
    }
}
