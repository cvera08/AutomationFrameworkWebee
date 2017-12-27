package tests;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
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
        String deviceName = "Open Closed Sensor";
        new HomePage(webDriver)
                .navigateToPage()
                .clickOnHubs()
                .selectDevicesOptionFromAHub("ACDBDA375480")
                .verifyDeviceIsPresent(deviceName, true)
                .pressEditDevice(deviceName)
                .pressDeleteDevice(true)
                .verifyDeviceIsPresent(deviceName, false);
    }
}
