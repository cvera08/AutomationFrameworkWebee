package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;
import utils.BaseSelenium;

/**
 * Created by Carlos Vera on 08/03/2017.
 */

public class LandingPage extends BasePage {

    public LandingPage(WebDriver webDriver) {
        super(webDriver, "/", "Webee Visual IoT");
        PageFactory.initElements(webDriver, this);
    }

    @Override
    public LandingPage navigateToPage() {
        Reporter.log("Navigating to Landing Page");
        return (LandingPage) super.navigateToPage();
    }

    /**
     * Verify that you are in Landing Page
     * Validation about if the page is loaded (using one component of the page)
     *
     * @return
     */
    public LandingPage landingPageIsDisplayed() {
        Reporter.log("Verifying that 'Webee Visual IoT' title is displayed");
        BaseSelenium.presenceOfElementUsingExplicitWait(webDriver, 5, By.xpath("//title[contains(text(),'Webee Visual IoT')]"));
        return this;
    }
}
