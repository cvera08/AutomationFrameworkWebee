package RESTAssured;

import com.jayway.restassured.RestAssured;
import org.hamcrest.CoreMatchers;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import tests.BaseTest;
import utils.BaseCompanies;
import utils.BaseRestAssured;
import utils.HtmlFormatter;

import static com.jayway.restassured.RestAssured.given;

/**
 * Created by Carlos Vera on 08/25/2017.
 * Tests for check API endpoints
 */
public class RESTAssuredTests extends BaseTest{

    @DataProvider(name = "CompanyInformation")
    public static Object[][] companyInformationData() {
        //Companies companies, String statusCode
        return new Object[][]{{BaseCompanies.Companies.HPE, 200}, {BaseCompanies.Companies.M3, 200}, {BaseCompanies.Companies.CRM, 400}};
    }

    @Test
    @Parameters({"endPoint", "CompanyId", "Name", "Logo"})
    public void companyInformation(String endPoint, String CompanyId, String Name, String Logo) {
        new BaseRestAssured(); //Setting the base url
        Reporter.log("Checking API: " + endPoint);
        RestAssured.basePath = endPoint;
        Reporter.log("Checking statusCode & body");
        given().when().get().then()
                .statusCode(200)
                .body("name", CoreMatchers.equalTo(Name))
                .body("companyId", CoreMatchers.equalTo(CompanyId))
                .body("logo", CoreMatchers.equalTo(Logo));
    }
}
