package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.Reporter;
import utils.BaseCompanies;
import utils.BaseSelenium;

import java.util.concurrent.TimeUnit;

/**
 * Created by Carlos Vera on 08/01/2017.
 */

public class LoginPage extends BasePage {

    private String userName;
    private String password;

    @FindBy(id = "Username")
    private WebElement userNameInput;

    @FindBy(id = "Password")
    private WebElement passwordInput;

    @FindBy(css = "input[value='Login']")
    private WebElement loginButton;

    @FindBy(xpath = "//span[@class='field-validation-error' and text()='Password is required']")
    private WebElement passwordRequired;

    @FindBy(xpath = "//span[@class='field-validation-error' and text()='Username is required']")
    private WebElement usernameRequired;

    public LoginPage(WebDriver webDriver) {
        super(webDriver, "/login", "Login");
        setLoginPage();
    }

    public LoginPage(WebDriver webDriver, BaseCompanies.Companies companyName) {
        super(webDriver, companyName, "/login", "Login");
        setLoginPage();
    }

    public LoginPage verifyLoginErrorTextGlobal(String errorMessage) {
        By by = By.xpath("(//div[@class='row']//form[@action='/login']//div[contains(text(),'" + errorMessage + "')]//text())[1]//parent::div");
        BaseSelenium.isDisplayed(errorMessage, webDriver.findElement(by));
        return this;
    }

    private void setLoginPage() {
        Reporter.log("Navigating to Login");
        this.navigateToPage();
        PageFactory.initElements(webDriver, this);
        loginPageIsDisplayed();
    }

    /**
     * Verify that you are in Login Page
     * Validation about if the page is loaded (using one component of the page)
     *
     * @return
     */
    private LoginPage loginPageIsDisplayed() {
        BaseSelenium.isDisplayed("Login Button", loginButton);
        return this;
    }

    /**
     * It performs the steps needed to login (enter credentials and press login)
     * This method is used for the successful log in
     * After the user is logged is redirected to Task Panel Page
     *
     * @param username
     * @param password
     * @return
     */
    public TaskPanelPage login(String username, String password) {
        this.userName = username;
        this.password = password;
        return enterUserName(username)
                .enterPassword(password)
                .pressLoginButton();
    }

    /**
     * Use this method to avoid to wait for load of the next page after successfully login (TaskPanelPage)
     *
     * @param username
     * @param password
     * @param timeoutSeconds
     * @return
     */
    public BasePage login(String username, String password, int timeoutSeconds) {
        this.userName = username;
        this.password = password;

        // Set the page load timeout to X seconds.
        webDriver.manage().timeouts().pageLoadTimeout(timeoutSeconds, TimeUnit.SECONDS);

        try {
            return enterUserName(username)
                    .enterPassword(password)
                    .pressLoginButton();
        } catch (TimeoutException e) {
            // Ignore the exception.
            webDriver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS); //new default Timeout set in 2 mins
            return new BasePage(webDriver, "", ""); //Just to improving execution time (avoid to wait for load of TaskPanelPage)
        }
    }

    private LoginPage enterUserName(String username) {
        BaseSelenium.enterTextInInputBox("UserName", username, userNameInput);
        return this;
    }

    private LoginPage enterPassword(String password) {
        BaseSelenium.enterTextInInputBox("Password", password, passwordInput);
        return this;
    }

    /**
     * Press Login button and it's logged ok
     * The user is redirected to the Task Panel Page after valid credentials
     *
     * @return
     */
    private TaskPanelPage pressLoginButton() {
        BaseSelenium.pressElement("Login", loginButton);
        return new TaskPanelPage(webDriver);
    }

    /**
     * It performs the steps needed to login (enter credentials and press login)
     * This method is used for the not successful log in. FE: Invalid credentials
     *
     * @param username
     * @param password
     * @return
     */
    public LoginPage failedLogin(String username, String password) {
        this.userName = username;
        this.password = password;
        return enterUserName(username)
                .enterPassword(password)
                .pressLoginButtonAndKeepInLoginPage();
    }

    /**
     * Press Login button and the user is NOT able to log in
     * The user is NOT redirected to the Employee Search Page. He is in Login Page yet
     *
     * @return
     */
    private LoginPage pressLoginButtonAndKeepInLoginPage() {
        BaseSelenium.pressElement("Login", loginButton);
        loginPageIsDisplayed();
        return this;
    }

    /**
     * After you didn't enter any credential in USERNAME field and pressed login button, validate that the field is required
     *
     * @return
     */
    public LoginPage verifyUsernameIsRequired() {
        BaseSelenium.isDisplayed("Username is required", usernameRequired);
        return this;
    }

    /**
     * After you didn't enter any credential in PASSWORD field and pressed login button, validate that the field is required
     *
     * @return
     */
    public LoginPage verifyPasswordIsRequired() {
        BaseSelenium.isDisplayed("Password is required", passwordRequired);
        return this;
    }

    /**
     * It verifies that the user is redirected to login page when he/she tries to enter to a secure page and it's not previously logged
     *
     * @param url
     * @return
     */
    public LoginPage userIsRedirectedToLogin(String url) {
        Reporter.log("Trying to access to: " + url);
        webDriver.get(baseProperties.getBaseUrl() + url);
        String redirectedURL = webDriver.getCurrentUrl();
        boolean matchWithExpectedURL = redirectedURL.toLowerCase().startsWith(baseProperties.getBaseUrl() + "/login?returnurl=");
        Assert.assertTrue(matchWithExpectedURL, "The user was not redirected to Login page. URL Found: " + redirectedURL + ". ");
        return new LoginPage(webDriver);
    }
}
