package tests;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.LoginPage;

/**
 * Created by Carlos Vera on 08/01/2017.
 * Login tests
 */

public class LoginTests extends BaseTest {

    @Test
    @Parameters({"username", "password"})
    public void failedLogin(String username, String password) {
        new LoginPage(webDriver)
                .failedLogin(username, password); //enter credentials, press login and validate that the user keeps in Login Page
    }

    @Test
    @Parameters({"username", "password"})
    public void successfulLogin(String username, String password) {
        new LoginPage(webDriver)
                .login(username, password) //enter credentials and press login
                .landingPageIsDisplayed(); //validate that it's redirected to the Landing Page
    }
}
