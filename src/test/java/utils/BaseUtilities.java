package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Carlos Vera on 08/10/2017.
 * This class contains some helpful utilities
 */
public class BaseUtilities {

    /**
     * Return random values from 100 to 10000
     */
    public static int generateRandomValue() {
        return 100 + (int) (Math.random() * 10000);
    }

    /**
     * Convert an String to another based on the parameters that is passed to this method
     * The params will be replacing each occurrence of the pattern %p
     *
     * @param base
     * @param params
     * @return
     */
    public static String replaceParameters(String base, String... params) {
        String modifiedString = base;
        if (params == null)
            return base;
        else {
            for (String replace : params) {
                modifiedString = modifiedString.replaceFirst("%p", replace);
            }
        }
        return modifiedString;
    }

    /**
     * Check if the specific path and file exists in the system
     *
     * @param downloadPath
     * @param partialFileName
     * @param archiveFormat
     * @return
     */
    public static boolean isFileDownloaded(String downloadPath, String partialFileName, String archiveFormat) {
        File dir = new File(downloadPath);
        File[] dir_contents = dir.listFiles();

        for (int i = 0; i < dir_contents.length; i++) {
            if (dir_contents[i].getName().contains(partialFileName))
                if (dir_contents[i].getName().contains(archiveFormat))
                    return true;
        }
        return false;
    }

    /**
     * Check if the file exists in the Downloads folder in your system
     * Recommended: delete the file after/before this check if the filename is always the same (use "deleteFile" method)
     *
     * @param partialFileName
     * @param archiveFormat
     * @return
     */
    public static boolean verifyFileIsDownloaded(String partialFileName, String archiveFormat) {
        BaseSelenium.hardcodedDelay(2000); //Wait 2 seconds until the file is completed downloaded
        Reporter.log("Verifying that archive: " + partialFileName + "*" + archiveFormat + " is downloaded");
        return BaseUtilities.isFileDownloaded(System.getProperty("user.home") + "\\Downloads\\", partialFileName, archiveFormat);
    }

    /**
     * Use this method to delete an specific file from your system. FE: as a tear down
     *
     * @param downloadPath
     * @param partialFileName
     * @param archiveFormat
     */
    public static void deleteFile(String downloadPath, String partialFileName, String archiveFormat) {
        File dir = new File(downloadPath);
        File[] dir_contents = dir.listFiles();
        for (int i = 0; i < dir_contents.length; i++) {
            if (dir_contents[i].getName().contains(partialFileName))
                if (dir_contents[i].getName().contains(archiveFormat))
                    dir_contents[i].delete();
        }
    }

    /**
     * It returns today's date in the format that is passed as parameter
     *
     * @param format
     * @return
     */
    public static String obtainformattedDate(String format) {
        Date date = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat(format, Locale.ENGLISH);
        return formatter.format(date);
    }

    /**
     * It Compares two List
     *
     * @param webDriver
     * @param by
     * @param expectedListOrdered :   separated by spaces
     * @param orderCriteria
     */
    public static void listAreEquals(WebDriver webDriver, By by, String expectedListOrdered, String orderCriteria) {
        Reporter.log("Verifying that " + orderCriteria + " List are equals. Expected:" + expectedListOrdered);
        BaseSelenium.presenceOfElementUsingExplicitWait(webDriver, 5, by);
        List<WebElement> listFound = webDriver.findElements(by); //Multiple divs that match the criteria
        String actualData = "";
        for (WebElement w : listFound)
            actualData += w.getText() + " ";
        Reporter.log("Found " + orderCriteria + " List: " + actualData);
        Assert.assertEquals(actualData, expectedListOrdered);
    }

    /**
     * Sent a list of web elements (more than 1) and obtain an ORDERED String list (the elements will be separated by spaces)
     * Output Example: 03/09/2017 01/09/2017 07/09/2017  (reverse order)
     * Output Example: 01/09/2017 03/09/2017 07/09/2017  (normal/asc order)
     *
     * @param actualList
     * @param order        : true or false depending if you want to obtain the same list (formatted) or an ordered one
     * @param reverseOrder : true or false
     * @param datesFormat
     * @return
     */
    public static String orderDatesAsStringList(List<WebElement> actualList, boolean order, final boolean reverseOrder, String datesFormat) {
        List<Date> orderedList = new ArrayList<Date>();
        DateFormat formatter = new SimpleDateFormat(datesFormat, Locale.ENGLISH);

        for (WebElement w : actualList)
            try {
                orderedList.add(formatter.parse(w.getText()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

        if (order) {
            Collections.sort(orderedList, new Comparator<Date>() {
                public int compare(Date o1, Date o2) {
                    if (reverseOrder)
                        return o2.compareTo(o1);
                    else
                        return o1.compareTo(o2);
                }
            });
        }

        String orderedDateList = "";
        for (int i = 0; i < orderedList.size(); i++) {
            orderedDateList += formatter.format(orderedList.get(i)) + " ";
        }

        return orderedDateList;
    }

    /**
     * Sent a list of web elements (more than 1) and obtain a String list (the elements will be separated by spaces)
     * Output Example: 03/09/2017 01/09/2017 07/09/2017
     *
     * @param list : input list
     * @return
     */
    public static String listAsStringList(List<WebElement> list) {
        String stringList = "";
        for (WebElement w : list)
            stringList += w.getText() + " ";
        return stringList;
    }

    /**
     * It verifies that the present URL is equal to the expected one
     *
     * @param webDriver
     * @param expectedUrl
     * @return
     */
    public static boolean actualurlIsEqualToTheExpectedUrl(WebDriver webDriver, String expectedUrl) {
        String actualURL = webDriver.getCurrentUrl();
        String expectedURL = new BaseProperties().getBaseUrl() + expectedUrl;
        if (actualURL.equals(expectedURL))
            return true;
        else
            return false;
    }
}
