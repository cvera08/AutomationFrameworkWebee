package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.BaseSelenium;

/**
 * Created by Carlos Vera on 12/13/2017.
 */
public class ProjectsPage extends BasePage {

    public ProjectsPage(WebDriver webDriver) {
        super(webDriver, "/projects", "Webee Visual IoT");
    }

    public DevicesPage openProject(String projectName) {
        BaseSelenium.pressElementUsingWaits(webDriver, By.xpath("//app-projects//div[text()='" + projectName + "']//parent::div//following-sibling::div//a[contains(.,'Open')]"), 5);
        return new DevicesPage(webDriver);
    }

    public ProjectsPage createNewProject(String projectName, boolean active) {
        //Press create new
        BaseSelenium.pressElementUsingWaits(webDriver, By.cssSelector("button.bar-btn.s12"), 5);
        //Enter Project Name
        BaseSelenium.enterTextUsingWaits(webDriver, By.id("projectName"), projectName, 5);
        if (active) //Mark the project as active
            BaseSelenium.pressElementUsingWaits(webDriver, By.cssSelector("form span.lever"), 5);
        //Press Save
        BaseSelenium.pressElementUsingWaits(webDriver, By.xpath("//a[contains(text(), 'Save')]"), 5);
        return this;
    }

    public ProjectsPage verifyProjectIsDisplayed(String projectName, boolean visible) {
        BaseSelenium.textInTagIsDisplayed(webDriver, "div", projectName, true, 5, projectName, visible);
        return this;
    }
}
