package utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

/**
 * Created by Carlos Vera on 08/25/2017.
 * This class contains some helpful methods for check endpoints of the APIs using Selenium and JSONObject
 */
public class BaseAPIEndPoints {

    JSONObject jsonObject;

    @FindBy(xpath = "//pre")
    private WebElement responseText;

    /**
     * Go to endpoint URL and then obtain the json response
     *
     * @param webDriver
     * @param endpointUrl
     * @return
     */
    public BaseAPIEndPoints goToUrl(WebDriver webDriver, String endpointUrl) {
        BaseProperties baseProperties = new BaseProperties();
        webDriver.get(baseProperties.getBaseUrl() + endpointUrl);
        PageFactory.initElements(webDriver, this);
        try {
            jsonObject = new JSONObject(responseText.getText());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Check if the response matches key:value (for the specific endpoint)
     *
     * @param key
     * @param value
     * @return
     */
    public BaseAPIEndPoints verifyResponseValues(String key, String value) {
        Reporter.log("Verifying endpoint values. '" + key + ":" + value + "'");
        String returnedValue = null;
        try {
            returnedValue = jsonObject.get(key).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        boolean match = value.equals(returnedValue);
        Assert.assertTrue("Incorrect Endpoint Value for key: '" + key + "'. Expected Value: '" + value + "' Found: '" + returnedValue + "'.", match);
        return this;
    }
}
