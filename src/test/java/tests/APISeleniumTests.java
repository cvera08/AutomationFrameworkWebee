package tests;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.BaseAPIEndPoints;

/**
 * Created by Carlos Vera on 08/25/2017.
 * End Point Tests
 */

public class APISeleniumTests extends BaseTest {

    @Test
    @Parameters({"endPoint", "CompanyId", "Name", "Logo"})
    public void companyInformation(String endPoint, String CompanyId, String Name, String Logo) {
        new BaseAPIEndPoints()
                .goToUrl(webDriver, endPoint)
                .verifyResponseValues("CompanyId", CompanyId)
                .verifyResponseValues("Name", Name)
                .verifyResponseValues("Logo", Logo);
    }
}
