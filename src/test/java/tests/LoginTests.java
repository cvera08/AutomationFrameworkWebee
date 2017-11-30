package tests;

import org.testng.Reporter;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.BaseCompanies;
import utils.BaseUsers;
import utils.HtmlFormatter;

/**
 * Created by Carlos Vera on 08/01/2017.
 * Login tests
 */

public class LoginTests extends BaseTest {

    @Test
    @Parameters({"username", "password"})
    public void successfulLogin(String username, String password) {
        new LoginPage(webDriver)
                .login(username, password) //enter credentials and press login
                .taskPanelPageIsDisplayed(); //validate that it's redirected to Task Panel Page
    }

    @Test
    @Parameters({"username", "password"})
    public void failedLogin(String username, String password) {
        new LoginPage(webDriver)
                .failedLogin(username, password); //enter credentials, press login and validate that the user keeps in Login Page
    }

    @Test
    @Parameters({"username", "password"})
    public void mandatoryFieldsLogin(String username, String password) {
        new LoginPage(webDriver)
                .failedLogin("", "")
                .verifyUsernameIsRequired()
                .verifyPasswordIsRequired()
                .failedLogin(username, "")
                .verifyPasswordIsRequired()
                .failedLogin("", password)
                .verifyUsernameIsRequired();
    }

    @Test
    public void logoutAfterLogin() {
        new LoginPage(webDriver)
                .login(BaseUsers.cSRUsername, BaseUsers.cSRPassword)
                .pressLogout()
                .logoutPopupIsDisplayed(true)
                .pressCloseInLogoutPopup()
                .pressLogout()
                .pressNoInLogoutPopup()
                .pressLogout()
                .pressYesInLogoutPopup();
    }

    @Test
    @Parameters({"username", "password"})
    public void mandatoryFields3MCompany(String username, String password) {
        new LoginPage(webDriver, BaseCompanies.Companies.M3)
                .failedLogin("", "")
                .verifyUsernameIsRequired()
                .verifyPasswordIsRequired()
                .failedLogin(username, "")
                .verifyPasswordIsRequired()
                .failedLogin("", password)
                .verifyUsernameIsRequired();
    }

    @Test
    @Parameters({"username", "password"})
    public void failedLogin3M(String username, String password) {
        new LoginPage(webDriver, BaseCompanies.Companies.M3)
                .failedLogin(username, password)
                .verifyLoginErrorTextGlobal("Login Failed. 3M-LoginErrorTest");
    }

    @Test
    @Parameters({"username", "password"})
    public void failedLoginGlobal(String username, String password) {
        Reporter.log(HtmlFormatter.addKnownBug("", "Verifying that 'Login Button' is displayed.  #Descr: HPE company is not configured in STG yet"));
        new LoginPage(webDriver, BaseCompanies.Companies.HPE)
                .failedLogin(username, password)
                .verifyLoginErrorTextGlobal("Login Failed. Please verify your username/password or contact us at 888-555-9999 or 222-888-1111 from 9am to 8pm ET Monday through Friday except holidays.");
    }
}
