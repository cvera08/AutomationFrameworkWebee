package tests;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.BaseUsers;

/**
 * Created by Carlos Vera on 12/04/2017.
 * Hub tests
 */

public class HubTests extends BaseTest {

    @Test
    @Parameters()
    public void addEdgeHubController() {
        new LoginPage(webDriver)
                .login(BaseUsers.automationUsername, BaseUsers.automationPassword)
                .clickOnHubs()
                .clickOnAddEdgeHubController();
    }
}
