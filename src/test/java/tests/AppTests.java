package tests;

import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.PlugPage;
import utils.BaseSelenium;
import utils.BaseUsers;

/**
 * Created by Carlos Vera on 12/13/2017.
 * Apps tests
 */

public class AppTests extends BaseTest {

    @Test
    public void turnOnPlug() {
        HomePage homePage = new LoginPage(webDriver)
                .login(BaseUsers.automationUsername, BaseUsers.automationPassword);
        changeStatePlug(homePage, true);
    }

    /**
     * This method is an auxiliary method to the tests, in order to be easier to maintain them
     *
     * @param turnOn
     * @return
     */
    private PlugPage changeStatePlug(HomePage homePage, boolean turnOn) {
        PlugPage plugPage;
        try {
            plugPage = homePage
                    .homePageIsDisplayed()
                    .clickOnProjects()
                    .openProject("QA Automation - ARG (No tocar)")
                    .clickOnApps()
                    .clickOnPreviewForPlug();
            if (turnOn)
                plugPage.clickOnON();
            else
                plugPage.clickOnOFF();
        } finally {
            webDriver.close(); //The focus is on the new tab & we close it
            BaseSelenium.moveFocusToAnotherTab(webDriver, 0);// move the focus to the original tab
        }
        return plugPage;
    }
}
