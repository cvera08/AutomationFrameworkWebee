package tests;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.DevicesPage;
import pages.HomePage;
import pages.LoginPage;
import utils.BaseUsers;
import utils.BaseUtilities;

/**
 * Created by Carlos Vera on 12/26/2017.
 * Project tests
 */

public class ProjectTests extends BaseTest {
    @BeforeTest
    public void login() {
        new LoginPage(webDriver)
                .login(BaseUsers.automationUsername, BaseUsers.automationPassword)
                .verifyHamburgerButtonIsDisplayed(true);
    }

    @Test
    public void createNewProject() {
        String projectName = "New Automation Project " + BaseUtilities.generateRandomValue();
        new HomePage(webDriver)
                .navigateToPage()
                .homePageIsDisplayed()
                .clickOnProjects()
                .verifyProjectIsDisplayed("QA Automation - ARG", true) //To avoid timing failure
                .createNewProject(projectName, true)
                .verifyProjectIsDisplayed(projectName, true);
    }

    @Test
    public void createNewFlow() {
        String projectName = "New Automation Project"; //To use the project created in the test "createNewProject"
        String newFlowName = "New Automation Flow";
        DevicesPage devicesPage = new HomePage(webDriver)
                .navigateToPage()
                .homePageIsDisplayed()
                .clickOnProjects()
                .verifyProjectIsDisplayed(projectName, true) //To avoid timing failure
                .openProject(projectName, true)
                .projectNameIsDisplayed(projectName, true) //To avoid false failures due timing issues
                .clickOnPlusCreateANewFlow()
                .enterFlowName(newFlowName)
                .pressSaveNewFlowButton()
                .flowTabIsDisplayed(newFlowName);
    }
}
