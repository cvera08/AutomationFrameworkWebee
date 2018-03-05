package tests;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.HubsPage;
import pages.LoginPage;
import utils.BaseSelenium;
import utils.BaseUsers;

/**
 * Created by Carlos Vera on 12/04/2017.
 * Hub tests
 */

public class HubTests extends BaseTest {

    @BeforeTest
    public void login() {
        new LoginPage(webDriver)
                .login(BaseUsers.automationUsername, BaseUsers.automationPassword)
                .verifyHamburgerButtonIsDisplayed(true);
    }


    @Test
    public void addEdgeHubControllerEmptyMacAddress() {
        new HomePage(webDriver)
                .navigateToPage()
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

    @Test
    @Parameters({"alphanumericCode", "spaceName"})
    public void addValidEdgeHubControllerUsingEthernetCable(String alphanumericCode, String spaceName) {
        HubsPage hubsPage = new HomePage(webDriver)
                .navigateToPage()
                .clickOnHubs()
                .clickOnAddEdgeHubController()
                .selectFirstAccountOffered()
                .enterHubsMacAddress(alphanumericCode)
                .pressReadyButton()
                .verifyConnectingYourEdgeHubControllerIsDisplayed(true)
                .pressMyHubIsReadyButton()
                .verifyTextInSpanIsDisplayed("Select how would you want to connect the Hub to the Internet", true);
        BaseSelenium.hardcodedDelay(1000);
        hubsPage.pressUsingEthernetCableButton()
                .presReadyButtonInConnectEthernetOrWifi()
                .verifyYourHubIsNowConnectedForEthIsDisplayed(true)
                .pressContinueButtonInPopupForConnectEthernetOrWifi()
                .enterYourHubLocation(spaceName)
                .pressDoneButton()
                .pressGoToDevicesButton();
    }

    /**
     * Preconditions:
     * -Search for the hub in the panel admin https://admin.webeelife.com/smartees.php
     * -Ping + Timeline > To review if the Hub is previously configured correctly
     * -Factory Reset > Make sure that it's state is Unregistered
     * <p>
     * -Wait until the hotspot appears in your wifi network (2mins approx)
     * <p>
     * -Delete Hub from VIOT. FE: https://stage.visual.webee.io/enterprises/5a29786739031e24066fbc7a/hubs
     * <p>
     * - Run this automation, make sure of connect (manually) to the hotspot after it's press "Using wi-fi network" button
     *
     * @param alphanumericCode
     * @param spaceName
     * @param networkName
     * @param wifiPassword
     */
    @Test
    @Parameters({"alphanumericCode", "spaceName", "networkName", "wifiPassword"})
    public void addValidEdgeHubControllerUsingWifi(String alphanumericCode, String spaceName, String networkName, String wifiPassword) {
        HubsPage hubsPage = new HomePage(webDriver)
                .navigateToPage()
                .homePageIsDisplayed()
                .clickOnHubs()
                .clickOnAddEdgeHubController()
                .selectFirstAccountOffered()
                .enterHubsMacAddress(alphanumericCode)
                .pressReadyButton()
                .verifyConnectingYourEdgeHubControllerIsDisplayed(true)
                .pressMyHubIsReadyButton()
                .pressUsingWifiNetworkButton()
                .verifyTextIsDisplayedInH5Tag("Using Wi-Fi to connect your Edge Hub Controller", true);
        //SOLUTION 1:
        //BaseSelenium.injectAlert(webDriver, "\\nCHANGE WIFI NETWORK by the Hub network MANUALLY. \\nWill continue in 60 seconds. \\n>Press Accept before the timeout");
        //BaseSelenium.hardcodedDelay(60000);//1 minute to connect to the hotspot network (in manual way)
        //SOLUTION 2:
        BaseSelenium.injectAlert(webDriver, "\\nCHANGE MANUALLY your WIFI network by the Hub network. \\n>Press Accept button when you did it");
        BaseSelenium.alertIsNotPresentAnymore(webDriver, 2000);
        hubsPage.presReadyButtonInConnectEthernetOrWifi()
                .pressDivWithText(networkName)
                .enterWifiPassword(wifiPassword)
                .pressButtonWithText("Connect")
                .verifyYourHubIsNowConnectedForWifiIsDisplayed(true)
                .pressContinueButtonInPopupForConnectEthernetOrWifi()
                .enterYourHubLocation(spaceName)
                .pressDoneButton()
                .pressGoToDevicesButton();
    }
}
