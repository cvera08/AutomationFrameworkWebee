package utils;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Reporter;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Carlos Vera on 11/21/2017.
 * This class contains:
 * -The initialization for the class to read data from excel and their methods to perform the operations
 * Mainly Resources / Source Code:
 * -http://www.softwaretestingmaterial.com/read-excel-files-using-apache-poi/
 * -http://learn-automation.com/read-and-write-excel-files-in-selenium/
 */

public class BaseExcel {
    FileInputStream fis;
    XSSFWorkbook workbook;
    XSSFSheet sheet;

    /**
     * Class constructor, it creates the "workbook" based on the location passed as parameter
     * It uses always the first sheet to read
     * The file should be located under utils/resources
     *
     * @param fileName
     */
    public BaseExcel(String fileName) {
        try {
            fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\java\\utils\\resources\\" + "\\" + fileName + ".xlsx");
            workbook = new XSSFWorkbook(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sheet = workbook.getSheetAt(0); // Load sheet- Here we are loading first sheet only
    }

    /**
     * Returns the STRING value from excel based on an specific row and column
     * ROWS & COLUMNS START FROM 0 (no 1)
     * Use this method when you know that the value in the cell is String type. FE: not use it when you know that is an integer
     *
     * @param rowNum
     * @param columnNum
     * @return
     */
    public String getValueFromExcelAsString(int rowNum, int columnNum) {
        String value = sheet.getRow(rowNum).getCell(columnNum).getStringCellValue();
        Reporter.log("Pulling input from Excel. Value: " + value);
        return value;
    }

    /**
     * Returns the NUMERIC value from excel based on an specific row and column
     * ROWS & COLUMNS START FROM 0 (no 1)
     * Use this method when you know that the value in the cell is Numeric type. FE: not use it when you know that is an String
     *
     * @param rowNum
     * @param columnNum
     * @return
     */
    public double getValueFromExcelAsDouble(int rowNum, int columnNum) {
        double value = sheet.getRow(rowNum).getCell(columnNum).getNumericCellValue();
        Reporter.log("Pulling input from Excel. Value: " + value);
        return value;
    }
}
