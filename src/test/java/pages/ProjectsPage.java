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
}
