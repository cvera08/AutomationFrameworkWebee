package tests;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.DevicesHomePage;
import pages.HomePage;
import pages.LoginPage;
import utils.BaseSelenium;
import utils.BaseUsers;

/**
 * Created by Carlos Vera on 12/27/2017.
 * Devices tests
 */

public class DevicesTests extends BaseTest {

    @BeforeTest
    public void login() {
        new LoginPage(webDriver)
                .login(BaseUsers.automationUsername, BaseUsers.automationPassword)
                .verifyHamburgerButtonIsDisplayed(true);
    }

    @Test
    public void removeDevicesFromHub() {
        String deviceName = "Webee Camera";
        new HomePage(webDriver)
                .navigateToPage()
                .clickOnHubs()
                .selectDevicesOptionFromAHub("ACDBDA375626")
                .verifyDeviceIsPresent(deviceName, true)
                .pressEditDevice(deviceName)
                .pressDeleteDevice(true)
                .verifyDeviceIsPresent(deviceName, false);
    }

    @Test
    public void removeDevicesFromDevicesSection() {
        String deviceName = "Webee Camera";
        DevicesHomePage devicesHomePage = new HomePage(webDriver)
                .navigateToPage()
                .clickOnDevices()
                .verifyDeviceIsPresentInDevicesList(deviceName, true)
                .pressEditButtonForADevice(deviceName)
                .pressDeleteButtonForDeviceDetail();
        BaseSelenium.acceptAlertDialog(webDriver);
        BaseSelenium.hardcodedDelay(2000); //To wait for the update of the list
        devicesHomePage.verifyDeviceIsPresentInDevicesList(deviceName, false);
    }
}
