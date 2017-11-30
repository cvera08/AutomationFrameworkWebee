package utils;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.*;
import org.testng.xml.XmlSuite;
import tests.BaseTest;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Carlos Vera on 07/13/2017.
 * This class contains the Extent Reports code in order to generate the html reports after your testNG execution
 * You can find the report in test-output\HTML Report - ExtentReportTestNG.html > open it using your browser
 */

public class ExtentReporterNG implements IReporter {
    BaseProperties baseProperties = new BaseProperties();
    private ExtentReports extent;

    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        extent = new ExtentReports(outputDirectory + File.separator + "HTML Report - ExtentReportTestNG.html", true);
        extent.loadConfig(new File("extent-config.xml")); //another OS: System.getProperty("user.dir")+"\\extent-config.xml"
        extent.addSystemInfo("Environment", baseProperties.getBaseUrl());
        extent.addSystemInfo("Browser", BaseTest.browser);

        if (baseProperties.getPublishReportInServer()) { //If YES, You can find the tests in extent server: http://54.90.131.194:1337/#/search
            extent.assignProject("HTML Report - ExtentReportTestNG");
            extent.x("54.90.131.194", 27017);
        }

        for (ISuite suite : suites) {
            Map<String, ISuiteResult> result = suite.getResults();

            for (ISuiteResult r : result.values()) {
                ITestContext context = r.getTestContext();

                buildTestNodes(context.getPassedTests(), LogStatus.PASS);
                buildTestNodes(context.getFailedTests(), LogStatus.FAIL);
                buildTestNodes(context.getSkippedTests(), LogStatus.SKIP);
            }
        }

        extent.flush();
        extent.close();
    }

    private void buildTestNodes(IResultMap tests, LogStatus status) {
        ExtentTest test;

        if (tests.size() > 0) {
            for (ITestResult result : tests.getAllResults()) {
                test = extent.startTest(result.getMethod().getMethodName());

                test.getTest().setStartedTime(getTime(result.getStartMillis()));
                test.getTest().setEndedTime(getTime(result.getEndMillis()));

                test.assignAuthor("Carlos Vera");

                String message = "Test " + status.toString().toLowerCase() + "ed";
                message = HtmlFormatter.addBig(message, true);
                message = HtmlFormatter.addHTMLTag(message, colourForResult(status));

                printLogsOfTheTest(test, result);

                if (result.getThrowable() != null) {
                    String snapshotPath = result.getTestClass().getXmlTest().getName() + " - " + result.getMethod().getMethodName() + ".jpg";
                    test.log(LogStatus.WARNING,
                            HtmlFormatter.formatHTMLText("Failed Snapshot below: " + test.addScreenCapture(snapshotPath), false, true, false, "gray", false, false));
                    ExtentTest childErrorLog = extent.startTest("Output / Log:");
                    childErrorLog.log(LogStatus.ERROR, HtmlFormatter.formatHTMLText(result.getThrowable().getMessage(), false, false, false, "gray", false, false));
                    test.appendChild(childErrorLog);
                }

                test.log(status, message);

                extent.endTest(test);
            }
        }
    }

    private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }

    private void printLogsOfTheTest(ExtentTest test, ITestResult result) {
        List<String> logList = Reporter.getOutput(result);
        for (String log : logList) {
            test.log(LogStatus.INFO, log);
        }
    }

    private String colourForResult(LogStatus status) {
        if (status == LogStatus.FAIL)
            return "label failure";
        else if (status == LogStatus.PASS)
            return "label success";
        else if (status == LogStatus.SKIP)
            return "label info";
        else
            return "label warn";
    }
}