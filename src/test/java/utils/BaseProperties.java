package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Carlos Vera on 07/21/2017.
 * This class read the properties file to obtain some general configurations like base url
 */
public class BaseProperties {

    private Properties properties = new Properties();

    /**
     * Constructor of the class:
     * -loadPropertiesFile
     */
    public BaseProperties() {
        loadPropertiesFile();
    }

    public String getBaseUrl() {
        return getUrlToUse();
    }

    private String getUrlToUse() {
        return properties.getProperty("URL_TO_USE");
    }

    public boolean getCloseBrowser() {
        return Boolean.valueOf(properties.getProperty("CLOSE_BROWSER"));
    }

    public boolean getMaximizeWindows() {
        return Boolean.valueOf(properties.getProperty("MAXIMIZE_WINDOWS"));
    }

    public boolean getTakeScreenshotAlways() {
        return Boolean.valueOf(properties.getProperty("TAKE_SCREENSHOT_ALWAYS"));
    }

    public boolean getWaitJenkinsDeploy() {
        return Boolean.valueOf(properties.getProperty("WAIT_JENKINS_DEPLOY"));
    }

    public boolean getPublishReportInServer() {
        return Boolean.valueOf(properties.getProperty("PUBLISH_REPORT_IN_SERVER"));
    }

    public int getRetryExecutionForFailures() {
        return Integer.valueOf(properties.getProperty("RETRY_EXECUTION_FOR_FAILURES"));
    }

    public boolean getHeadlessMode() {
        return Boolean.valueOf(properties.getProperty("HEADLESS_MODE"));
    }

    /**
     * Set location of config.properties and load properties from this file
     */
    private void loadPropertiesFile() {
        File file = new File("config.properties");

        FileInputStream fileInput = null;
        try {
            fileInput = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Path to config.properties not valid." + e);
        }

        //load properties file
        try {
            properties.load(fileInput);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
