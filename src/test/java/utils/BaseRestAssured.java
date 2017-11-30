package utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.jayway.restassured.RestAssured;
import org.testng.Assert;
import org.testng.Reporter;

import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Created by Carlos Vera on 08/25/2017.
 * This class contains some helpful methods for check endpoints of the APIs using REST Assured tool
 * To obtain the completed response in execution time: given().when().get().body().asString()
 * Rest Assured fully Tutorial: https://semaphoreci.com/community/tutorials/testing-rest-endpoints-using-rest-assured
 */
public class BaseRestAssured {
    BaseProperties baseProperties = new BaseProperties();

    /**
     * Initialize Rest Assured
     * Set the base end point url
     *
     * @return
     */
    public BaseRestAssured() {
        RestAssured.baseURI = baseProperties.getBaseUrl();
        RestAssured.useRelaxedHTTPSValidation(); //to avoid SSLHandshakeException (certpath.SunCertPathBuilderException)
    }

    /**
     * Initialize Rest Assured
     * Set the base end point url based on the company passed as parameter
     *
     * @param companies
     * @return
     */
    public BaseRestAssured(BaseCompanies.Companies companies) {
        String newURL = baseProperties.getBaseUrl().replace("crm", companies.getValue()); //Modifying the default URL
        Reporter.log("Setting Base URI: " + newURL);
        RestAssured.baseURI = newURL;
        RestAssured.useRelaxedHTTPSValidation(); //to avoid SSLHandshakeException (certpath.SunCertPathBuilderException)
    }

    public void validateBasicCompanyInformationAndValidators3M(String jsonReturned) {
        compareTwoJsons("src\\test\\java\\RESTAssured\\APIExpectedOutputs\\basicCompanyInformationAndValidators3M", jsonReturned);
    }

    public void validateBasicCompanyInformationAndValidatorsHPE(String jsonReturned) {
        compareTwoJsons("src\\test\\java\\RESTAssured\\APIExpectedOutputs\\basicCompanyInformationAndValidatorsHPE", jsonReturned);
    }

    public void validateTerms3M(String jsonReturned) {
        compareTwoJsons("src\\test\\java\\RESTAssured\\APIExpectedOutputs\\terms3M", jsonReturned);
    }

    private void compareTwoJsons(String jsonExpLocation, String jsonReturned) {
        JsonParser parser = new JsonParser();
        JsonElement jsonExpected = null;
        try {
            jsonExpected = parser.parse(new FileReader(jsonExpLocation));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        JsonElement jsonRet = parser.parse(jsonReturned);
        Assert.assertTrue(jsonExpected.equals(jsonRet), "Expected and Returned Jsons are Different.");
    }
}
