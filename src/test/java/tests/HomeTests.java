package tests;

import org.testng.annotations.Test;
import pages.LoginPage;
import utils.BaseUsers;

/**
 * Created by Carlos Vera on 12/11/2017.
 * Hub tests
 */

public class HomeTests extends BaseTest {

    @Test
    public void verifyingComponentsOfUpperBar() {
        new LoginPage(webDriver)
                .login(BaseUsers.automationUsername, BaseUsers.automationPassword)
                .homePageIsDisplayed()
                .verifyHamburgerButtonIsDisplayed(true)
                .verifyEnterpriseNameIsDisplayed("QA Automation - ARG", true)
                .verifyUsernameIsDisplayed("carlos.vera", true);
    }
}
