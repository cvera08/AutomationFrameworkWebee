package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Reporter;
import utils.*;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Carlos Vera on 07/11/2017.
 * This class contains:
 * -The base for all the pages. FE: URL & Title
 */

public class BasePage {
    public URL url;
    public String title;
    public WebDriver webDriver;
    BaseProperties baseProperties;

    @FindBy(css = "li a[href='/Secure/CompanyList']")
    private WebElement companiesButton;

    /**
     * Using Default company "CRM"
     *
     * @param webDriver
     * @param pathUrl
     * @param title
     */
    public BasePage(WebDriver webDriver, String pathUrl, String title) {
        settingBasePage(webDriver, null, pathUrl, title);
    }

    /**
     * Use this constructor to modify the base url to use
     * FE: 3m.nextgen-pre.itxtest.net, hpe.nextgen-pre.itxtest.net, etc
     *
     * @param webDriver
     * @param companyName
     * @param pathUrl
     * @param title
     */
    public BasePage(WebDriver webDriver, BaseCompanies.Companies companyName, String pathUrl, String title) {
        settingBasePage(webDriver, companyName, pathUrl, title);
    }

    private void settingBasePage(WebDriver webDriver, BaseCompanies.Companies companyName, String pathUrl, String title) {
        baseProperties = new BaseProperties();
        this.webDriver = webDriver;
        String newUrl;
        if (companyName == null)
            newUrl = getBaseUrl() + pathUrl;
        else
            newUrl = baseProperties.getBaseUrl().replace("crm", companyName.getValue()) + pathUrl;
        try {
            this.url = new URL(newUrl);
        } catch (MalformedURLException e) {
            throw new RuntimeException("The url is not well formed. URL: " + newUrl);
        }
        this.title = title;
    }

    public URL getURL() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public BasePage navigateToPage() {
        webDriver.navigate().to(url);
        measurePageResponseTime();
        return this;
    }

    private URL getBaseUrl() {
        try {
            return new URL(baseProperties.getBaseUrl());
        } catch (MalformedURLException e) {
            throw new RuntimeException("The url is not well formed. URL: " + baseProperties.getBaseUrl());
        }
    }

    /**
     * It takes the time to load the page
     * Print the result in HTML Extent Report (gray colour)
     */
    private void measurePageResponseTime() {
        Long loadTime = BaseLoadTimes.measurePageResponseTime(webDriver);

        Reporter.log(
                HtmlFormatter.formatHTMLText("Total Page Load Time : " + loadTime + " milliseconds", false, false, false, "gray", false, false));
    }

    /**
     * It verifies that Hamburger icon/button is/is not displayed in the Home Page
     *
     * @return
     */
    public BasePage verifyHamburgerButtonIsDisplayed(boolean visible) {
        BaseSelenium.isDisplayedUsingWaits(webDriver, By.cssSelector("a.button-collapse"), 5, "Hamburger Button", visible);
        return this;
    }

    /**
     * It verifies that enterprise name is/is not displayed in the Home Page
     *
     * @return
     */
    public BasePage verifyEnterpriseNameIsDisplayed(String enterpriseName, boolean visible) {
        BaseSelenium.textInTagIsDisplayed(webDriver, "h4", enterpriseName, true, 5, "Enterprise Name: " + enterpriseName, visible);
        return this;
    }

    /**
     * It verifies that user name is/is not displayed in the Home Page
     *
     * @return
     */
    public BasePage verifyUsernameIsDisplayed(String username, boolean visible) {
        BaseSelenium.textInTagIsDisplayed(webDriver, "div", username, true, 5, "Username: " + username, visible);
        return this;
    }

    /**
     * Log out of the system
     * This method click on user image and then hit "Logout"
     *
     * @return
     */
    public BasePage pressLogout() {
        BaseSelenium.pressElementUsingWaits(webDriver, By.id("profile-drop"), 5);
        BaseSelenium.pressElementUsingWaits(webDriver, By.cssSelector("a[data-target='#logoutModal']"), 5);
        return this;
    }

    /**
     * It verifies that pop up / alert is displayed after you pressLogout
     *
     * @param displayed
     * @return
     */
    public BasePage logoutPopupIsDisplayed(boolean displayed) {
        By by = By.xpath("//div[@id='logoutModal']//h3[text()='Are you sure you wish to log out?']");
        BaseSelenium.isDisplayed("Logout Popup", webDriver, by, displayed);
        return this;
    }

    /**
     * Hit "No" button in log out pop up
     * Expected: NO Navigate to Log in Page. Stay in the same page
     *
     * @return
     */
    public BasePage pressNoInLogoutPopup() {
        BaseSelenium.fluentWaitWaitingElementToBeClickable(webDriver, By.xpath("//div[@id='logoutModal']//button[@class='btn btn-default']"));
        By noButtonInLogoutPopupSelector = By.xpath("//div[@id='logoutModal']//button[@class='btn btn-default']");
        BaseSelenium.pressElementUsingWaits(webDriver, noButtonInLogoutPopupSelector, 5);
        BaseSelenium.fluentWaitWaitingForInvisibilityOf(webDriver, webDriver.findElement(noButtonInLogoutPopupSelector), 5); //To avoid false failures in the next steps
        return this;
    }

    /**
     * Hit "X" (close) button in log out pop up
     * Expected: NO Navigate to Log in Page. Stay in the same page
     *
     * @return
     */
    public BasePage pressCloseInLogoutPopup() {
        BaseSelenium.fluentWaitWaitingElementToBeClickable(webDriver, By.xpath("//div[@id='logoutModal']//button[@class='close']"));
        By closeButtonInLogoutPopupSelector = By.xpath("//div[@id='logoutModal']//button[@class='close']");
        BaseSelenium.pressElementUsingWaits(webDriver, closeButtonInLogoutPopupSelector, 5);
        BaseSelenium.fluentWaitWaitingForInvisibilityOf(webDriver, webDriver.findElement(closeButtonInLogoutPopupSelector), 5);
        return this;
    }

    /**
     * Hit "Yes" button in log out pop up
     * Expected: Navigate to Log in Page
     *
     * @return
     */
    public LoginPage pressYesInLogoutPopup() {
        By yesButtonInLogoutPopupXpath = By.xpath("//div[@id='logoutModal']//a[contains(@href,'logout.aspx')]");
        BaseSelenium.fluentWaitWaitingElementToBeClickable(webDriver, yesButtonInLogoutPopupXpath);
        BaseSelenium.pressElementUsingWaits(webDriver, yesButtonInLogoutPopupXpath, 5);
        return new LoginPage(webDriver);
    }
}
