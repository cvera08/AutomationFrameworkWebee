package tests;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.HubsPage;
import pages.LoginPage;
import utils.BaseUsers;

/**
 * Created by Carlos Vera on 12/04/2017.
 * Hub tests
 */

public class HubTests extends BaseTest {

    @Test
    public void addEdgeHubControllerEmptyMacAddress() {
        new LoginPage(webDriver)
                .login(BaseUsers.automationUsername, BaseUsers.automationPassword)
                .clickOnHubs()
                .clickOnAddEdgeHubController()
                .selectFirstAccountOffered()
                .pressReadyButton()
                .verifyMessageIsDisplayed(HubsPage.Messages.MAC_ADDRESS_CAN_NOT_BE_EMPTY, true);
    }

    @Test
    @Parameters("alphanumericCode")
    public void addEdgeHubControllerLessThan12Characters(String alphanumericCode) {
        new HomePage(webDriver)
                .navigateToPage()
                .clickOnHubs()
                .clickOnAddEdgeHubController()
                .selectFirstAccountOffered()
                .enterHubsMacAddress(alphanumericCode)
                .pressReadyButton()
                .verifyMessageIsDisplayed(HubsPage.Messages.MAC_ADDRESS_MUST_HAVE_12_CHARACTERES, true);
    }
}
