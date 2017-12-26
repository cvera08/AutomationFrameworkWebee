package tests;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
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
}
