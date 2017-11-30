package tests;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.*;
import utils.BaseProperties;
import utils.CreateWebDriverFromSession;

import java.io.File;
import java.io.IOException;

import static com.jayway.restassured.RestAssured.given;

/**
 * Created by Carlos Vera on 07/11/2017.
 * This class contains:
 * -The initialization for the browser type
 * -Close the browser at the end of the execution of the test
 */

public class BaseTest {

    public static String browser;
    WebDriver webDriver;
    boolean closeBrowserAtTheEnd;
    BaseProperties baseProperties = new BaseProperties();
    boolean maximizeBrowserWindows;

    /**
     * This method is only helpful in Jenkins server to wait until environment is up again after deploy
     * To run some test locally, please change WAIT_JENKINS_DEPLOY to false in config.properties file
     */
    @BeforeSuite
    public void preSetup() {
        if (baseProperties.getWaitJenkinsDeploy()) {
            System.out.println("Waiting for Environment after Jenkins deploy");
            int statusCode = 0;
            long timeoutExpiredMs = System.currentTimeMillis() + 180000; //Maximum waiting time: 3 mins
            System.out.println("Timeout Set in: " + timeoutExpiredMs);
            while (statusCode != 200 && System.currentTimeMillis() < timeoutExpiredMs) {
                try {
                    Thread.sleep(1000); //Waiting for 1 second to avoid system overload
                    System.out.println("Time: " + System.currentTimeMillis());
                    statusCode = given().relaxedHTTPSValidation()
                            .get(baseProperties.getBaseUrl().replace("crm", "3m") + "/ngapi/v1/employees/discovercompany")
                            .statusCode();
                    System.out.println("Status Code: " + statusCode);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Exit time: " + System.currentTimeMillis());
        }
    }

    /**
     * Before test or tests execution will be called this method to:
     * - Set Browser type
     * - Configure if the browser session will be closed at the end
     * - If it is not passed an specific browser parameter, the test would be run using Google Chrome
     */
    @BeforeTest
    @Parameters("browser")
    public void setup(@Optional("CHROME") String browser) {
        setBrowser(browser);
        setBrowserSize();
        setValueForCloseBrowserAtTheEnd();
    }

    /**
     * Set value to close the browser when the test finish the execution
     * The configuration value is available in config.properties, key CLOSE_BROWSER
     */
    private void setValueForCloseBrowserAtTheEnd() {
        closeBrowserAtTheEnd = baseProperties.getCloseBrowser();
    }

    /**
     * Set value to browser type
     * This means that your tests will be executed in the browser that you have configured in the config.properties file, key BROWSER
     */
    private void setBrowser(String browser) {
        this.browser = browser;
        Reporter.log("Opening browser : " + browser);
        if (browser.equals("CHROME"))
            webDriver = new ChromeDriver();
        else if (browser.equals("FIREFOX"))
            webDriver = new FirefoxDriver();
        else if (browser.equals("INTERNET_EXPLORER"))
            webDriver = new InternetExplorerDriver();
        else //TODO support to Safari browser
            throw new UnsupportedOperationException("Unsupported Browser type. Browser parameter : " + browser + ". Expected values: CHROME, FIREFOX OR INTERNET_EXPLORER");
    }

    /**
     * Establish if the browser windows will appear as minimized (default) or maximized
     */
    private void setBrowserSize() {
        maximizeBrowserWindows = baseProperties.getMaximizeWindows();
        if (maximizeBrowserWindows)
            webDriver.manage().window().maximize();
    }

    /**
     * After each method (let's say test) it takes screenshot of the last browser screen
     * It calls to takeScreenshot method based on the value of TAKE_SCREENSHOT_ALWAYS
     * - TAKE_SCREENSHOT_ALWAYS:true = take screenshot after each test execution
     * - TAKE_SCREENSHOT_ALWAYS:false = take screenshot just when the test fails
     *
     * @param result
     */
    @AfterMethod
    public void afterMethod(ITestResult result) {
        if (baseProperties.getTakeScreenshotAlways())
            takeScreenshot(result);
        else if (result.getThrowable() != null)
            takeScreenshot(result);
    }

    private void takeScreenshot(ITestResult result) {
        File scrFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(scrFile,
                    new File("test-output" + File.separator + result.getTestClass().getXmlTest().getName() + " - " + result.getMethod().getMethodName() + ".jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method will be called at the end of your test execution
     * Will / Won't close the browser session based on the value of the key CLOSE_BROWSER located in config.properties file
     */
    @AfterTest
    public void closeBrowser() {
        if (closeBrowserAtTheEnd)
            webDriver.quit(); // close the browser at the end
        try { //Firefox Known Bug: https://bugzilla.mozilla.org/show_bug.cgi?id=1027222 & https://github.com/seleniumhq/selenium-google-code-issue-archive/issues/7506
            if (browser.equals("FIREFOX"))
                Runtime.getRuntime().exec("taskkill /F /IM WerFault.exe");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
