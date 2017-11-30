package tests;

import org.testng.Reporter;
import org.testng.annotations.Test;

/**
 * Created by Carlos Vera on 07/04/2017.
 * You can run this test to check that all is configured and working ok (locally in your computer)
 */

public class CheckHealthTests extends BaseTest {

    @Test
    public void googleTest() {
        System.out.println("Opening Google Page for Check health test"); //Printed in your console (IDE or Terminal) and in Jenkins
        Reporter.log("Navigating to Google page"); //Printed in Extent Report (Look at test-output/HTML Report - ExtentReportTestNG.html)
        webDriver.navigate().to("http://www.google.com");
    }
}
