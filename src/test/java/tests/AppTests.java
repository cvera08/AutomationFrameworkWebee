package tests;

import org.testng.annotations.BeforeTest;
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
    @BeforeTest
    public void login() {
        new LoginPage(webDriver)
                .login(BaseUsers.automationUsername, BaseUsers.automationPassword)
                .verifyHamburgerButtonIsDisplayed(true);
    }

    @Test
    public void turnOnPlug() {
        HomePage homePage = new HomePage(webDriver).navigateToPage();
        changeStatePlug(homePage, true);
    }

    @Test
    public void turnOffPlug() {
        HomePage homePage = new HomePage(webDriver).navigateToPage();
        changeStatePlug(homePage, false);
    }

    /**
     * This method is an auxiliary method to the tests, in order to be easier to maintain them
     *
     * @param turnOn
     * @return
     */
    private PlugPage changeStatePlug(HomePage homePage, boolean turnOn) {
        PlugPage plugPage;
        plugPage = homePage
                .homePageIsDisplayed()
                .clickOnProjects()
                .openProject("QA Automation - ARG (No tocar)")
                .clickOnToggleConsole() //To check after the plug events in the console
                .clickOnAppsInANewTab()
                .clickOnPreviewForPlug();
        if (turnOn)
            plugPage.clickOnON();
        else
            plugPage.clickOnOFF();
        BaseSelenium.moveFocusToAnotherTab(webDriver, 0);// move the focus to the original tab
        String eventText = "on-off: " + turnOn;
        BaseSelenium.textInTagIsDisplayed(webDriver, "span", eventText, true, 20, "Event in Console: " + eventText, true);
        return plugPage;
    }
}
