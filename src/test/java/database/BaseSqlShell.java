package database;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import utils.BaseProperties;
import utils.BaseUtilities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Carlos Vera on 08/14/2017.
 * This class is used to run sql scripts on the fly using the sitecore shell
 * It's just a help to our UI Automation Test Cases, in order to avoid unnecessary dependencies with other UI tests
 * Some attributes are hardcoded due the nature of the class. This is why we are not using page object either here. This is why we are not calling BaseSelenium methods
 */
public class BaseSqlShell {
    private String username = "admin";
    private String password = "";
    private String url;
    private String databaseName = "";

    @FindBy(id = "LoginTextBox")
    private WebElement userNameInput;

    @FindBy(id = "PasswordTextBox")
    private WebElement passwordInput;

    @FindBy(css = "input[value='Log in']")
    private WebElement loginButton;

    @FindBy(id = "Query")
    private WebElement queryTextArea;

    @FindBy(id = "Databases")
    private WebElement dataBasesSelect;

    @FindBy(css = "input[value='Execute']")
    private WebElement executeButton;

    /**
     * Constructor of the class, it "initializes" the elements of the page and perform the login
     *
     * @param webDriver
     */
    public BaseSqlShell(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
        url = formatURL() + "";
        login(webDriver);
    }

    /**
     * Obtain an url removing the company from the original url
     *
     * @return
     */
    private String formatURL() {
        BaseProperties baseProperties = new BaseProperties();
        return baseProperties.getBaseUrl().replace("crm", "");
    }

    private void login(WebDriver webDriver) {
        webDriver.navigate().to(url);
        //userNameInput.sendKeys(username); //The default user that the browser give us is enough
        passwordInput.sendKeys(password);
        loginButton.click();
    }

    /**
     * Calls to this method to create the SQL script, paste it in the sitecore shell, select the DB and execute it
     *
     * @param scriptFileName
     */
    private void runScript(String scriptFileName, String... params) {
        String script = createScript(scriptFileName);
        script = BaseUtilities.replaceParameters(script, params);
        queryTextArea.clear();
        queryTextArea.sendKeys(script);
        new Select(dataBasesSelect).selectByVisibleText(databaseName);
        executeButton.click();
    }

    /**
     * Create/Obtain the SQL script based on the file where is located the real script
     *
     * @param scriptFileName
     * @return
     */
    private String createScript(String scriptFileName) {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader("src\\test\\java\\database\\scripts\\" + scriptFileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String script = "";
        String line;
        try {
            while ((line = in.readLine()) != null)
                script += line + "\n";
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return script;
    }

    /**
     * Convert a Completed Task to Open Task based on the parameters of src\test\java\database\scripts\convertCompletedToOpenTask file
     */
    public void convertCompletedToOpenTask(String subject) {
        runScript("convertCompletedToOpenTask", subject);
    }

}
