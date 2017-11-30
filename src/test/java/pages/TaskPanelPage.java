package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.Reporter;
import utils.BaseSelenium;
import utils.BaseUtilities;

/**
 * Created by Carlos Vera on 08/03/2017.
 */

public class TaskPanelPage extends BasePage {

    @FindBy(xpath = "//task-root//a[contains(text(), 'Shared Task List')]")
    private WebElement sharedTaskListLabel;

    @FindBy(xpath = "//task-root//div[@class='dropdown m-inline-block open']//a[contains(text(), 'Assign to me')]")
    private WebElement assignToMeOption;

    public TaskPanelPage(WebDriver webDriver) {
        super(webDriver, "/secure/taskpanel", "Panel Page");
        PageFactory.initElements(webDriver, this);
    }

    @Override
    public TaskPanelPage navigateToPage() {
        Reporter.log("Navigating to Task Panel Page");
        return (TaskPanelPage) super.navigateToPage();
    }

    /**
     * Verify that you are in Task Panel Page
     * Validation about if the page is loaded (using one component of the page)
     *
     * @return
     */
    public TaskPanelPage taskPanelPageIsDisplayed() {
        Reporter.log("Verifying that 'Tasks Title' is displayed");
        BaseSelenium.presenceOfElementUsingExplicitWait(webDriver, 5, By.xpath("//h1[contains(text(),'Task')]"));
        return this;
    }

    /**
     * When the user information could not be retrieved an error dialog is displayed
     *
     * @return
     */
    public TaskPanelPage acceptAlert() {
        BaseSelenium.acceptAlertDialog(webDriver);
        return this;
    }

}
