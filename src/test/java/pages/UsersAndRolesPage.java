package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.BaseSelenium;

/**
 * Created by Carlos Vera on 04/09/2018.
 * http://prntscr.com/j31chu
 */
public class UsersAndRolesPage extends BasePage {

    public UsersAndRolesPage(WebDriver webDriver) {
        super(webDriver, "/users", "Webee Visual IoT");
    }

    /**
     * http://prntscr.com/j31cwc
     *
     * @return
     */
    public UsersAndRolesPage pressCreateNew() {
        BaseSelenium.pressElementUsingWaits(webDriver, By.xpath("//button[contains(text(),'Create new user')]"), 5);
        return this;
    }

    /**
     * http://prntscr.com/j31d5a
     *
     * @param text
     * @return
     */
    public UsersAndRolesPage enterUserDetailField(String field, String text) {
        BaseSelenium.enterTextUsingWaits(webDriver, By.xpath("//label[contains(text(),'" + field + "')]//preceding-sibling::input"), text, 5);
        return this;
    }

    /**
     * http://prntscr.com/j31hto
     *
     * @return
     */
    public UsersAndRolesPage pressSaveButtonForAddNewUser() {
        BaseSelenium.pressElementUsingWaits(webDriver, By.xpath("//button[contains(text(),'Save')]"), 5);
        return this;
    }

    /**
     * http://prntscr.com/j31lfa
     *
     * @param textToValidate
     * @param visible
     * @return
     */
    public UsersAndRolesPage verifyUserIsPresentInUserList(String textToValidate, boolean visible) {
        BaseSelenium.isDisplayedUsingWaits(webDriver, By.xpath("//div[@class='card-panel row']//div[contains(text(),'" + textToValidate + "')]"), 5, textToValidate, visible);
        return this;
    }

    /**
     * http://prntscr.com/j31rol
     *
     * @return
     */
    public UsersAndRolesPage pressEditForSpecificUser(String userField) {
        BaseSelenium.pressElementUsingWaits(webDriver, By.xpath("//div[contains(text(),'" + userField + "')]//parent::div//parent::div//text()[contains(.,'Edit')]//parent::button"), 5);
        return this;
    }

    /**
     * http://prntscr.com/j31u8o
     *
     * @return
     */
    public UsersAndRolesPage pressDeleteForUserDetail() {
        BaseSelenium.pressElementUsingWaits(webDriver, By.xpath("//a[text()='Delete']"), 5);
        return this;
    }

}
