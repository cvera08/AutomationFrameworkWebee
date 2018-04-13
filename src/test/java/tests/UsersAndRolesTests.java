package tests;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.UsersAndRolesPage;
import utils.BaseSelenium;
import utils.BaseUsers;
import utils.BaseUtilities;

/**
 * Created by Carlos Vera on 04/09/2018.
 * Users & Roles tests
 */

public class UsersAndRolesTests extends BaseTest {

    int postId = BaseUtilities.generateRandomValue();
    String userName = "newAutomationUsername" + postId;

    @BeforeTest
    public void login() {
        new LoginPage(webDriver)
                .login(BaseUsers.automationUsername, BaseUsers.automationPassword)
                .verifyHamburgerButtonIsDisplayed(true);
    }

    @Test
    public void createNewUser() {
        String userPassword = "131313";
        String userEmail = "newAutomationEmail@auto.com" + postId;
        new HomePage(webDriver)
                .navigateToPage()
                .clickOnUsersAndRoles()
                .pressCreateNew()
                .enterUserDetailField("Username*", userName)
                .enterUserDetailField("Name*", "newAutomationName")
                .enterUserDetailField("Last Name*", "newAutomationLastName")
                .enterUserDetailField("Email*", userEmail)
                .enterUserDetailField("Password*", userPassword)
                .enterUserDetailField("Confirm password*", userPassword)
                .pressSaveButtonForAddNewUser()
                .verifyUserIsPresentInUserList(userName, true)
                .verifyUserIsPresentInUserList(userEmail, true);
    }

    @Test(dependsOnMethods = "createNewUser")
    public void deleteUser() {
        UsersAndRolesPage usersAndRolesPage = new HomePage(webDriver)
                .navigateToPage()
                .clickOnUsersAndRoles()
                .pressEditForSpecificUser(userName)
                .pressDeleteForUserDetail();
        BaseSelenium.acceptAlertDialog(webDriver);
        BaseSelenium.hardcodedDelay(1000); //Wait for load page & table
        usersAndRolesPage.verifyUserIsPresentInUserList(userName, false);

    }
}
