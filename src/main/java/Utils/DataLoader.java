package Utils;

import BaseFramework.GlobalStore;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

public class DataLoader extends GlobalStore {
    XSSFWorkbook workbook = null;
    XSSFSheet sheet = null;

    public DataLoader() {
        reportStep("Loading all data of the project", "INFO");
    }

    public String[][] getAllSheetData(String sheetName, String UserType) {
        String[][] data = null;
        String HOMEDIR = System.getProperty("user.dir");
        String dataSheetFullPath = HOMEDIR + File.separator + "testdata" + File.separator + sheetName;
        File sheetHandle = new File(dataSheetFullPath);
        //String env = GlobalStore.getEnv();
        /*Let's keep the default environment as QA for now*/
        String env = "QA";
        FileInputStream FIS;

        reportStep("HINT: About to read data from this testsheet(" + dataSheetFullPath + ")", "INFO");
        try {
            FIS = new FileInputStream(sheetHandle);

            try {
                if (env.contentEquals("QA")) {
                    workbook = new XSSFWorkbook(FIS);
                    sheet = workbook.getSheetAt(0);
                } else if (env.contentEquals("STAGE")) {
                    workbook = new XSSFWorkbook(FIS);
                    sheet = workbook.getSheetAt(1);
                }
            } catch (Exception e) {
                reportStep("SEVERE: Not able to open XLSX file, Hence terminating execution", "TERMINATE");
                System.exit(100);

            }
            // Get first/desired sheet from the workbook
            // XSSFSheet sheet = workbook.getSheetAt(0);

            // Initialize data set
            int noRows = sheet.getLastRowNum();
            int noCols = sheet.getRow(0).getLastCellNum();

            // Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            rowIterator.next();
            int rowCount = 0;
            int colCount = 0;
            String cellValue = null;

            int whichRowToGet = getRowNumForDataToRead(UserType, rowIterator);
            // System.out.println("Row found for retirval:"+ whichRowToGet);

            // Initialize size
            data = new String[1][noCols];
            Row row = sheet.getRow(whichRowToGet);
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();

                switch (cell.getCellType()) {
                    case NUMERIC:
                        cellValue = String.valueOf(cell.getNumericCellValue());
                        reportStep(cellValue, "INFO");
                        break;
                    case STRING:
                        cellValue = cell.getStringCellValue();
                        break;
                }
                data[rowCount][colCount] = cellValue;
                colCount++;
            }

            // Close all open file handles
            FIS.close();
            workbook.close();
        } catch (Exception e) {
            reportStep("SEVERE: File Not found, Hence terminating execution", "TERMINATE");
        }

        return data;
    }

    public int getRowNumForDataToRead(String RoleType, Iterator<Row> rowIterator) {
        int whichRow = 1;
        String cellValue = null;

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            // For each row, iterate through all the columns
            Iterator<Cell> cellIterator = row.cellIterator();

            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();

                switch (cell.getCellType()) {
                    case NUMERIC:
                        cellValue = String.valueOf(cell.getNumericCellValue());
                        reportStep(cellValue, "INFO");
                        break;
                    case STRING:
                        cellValue = cell.getStringCellValue();
                        break;
                }
                try {
                    if (cellValue.equalsIgnoreCase(RoleType)) {
                        return whichRow;
                    }
                } catch (Exception e) {
                    reportStep("Exception seen when reading value from the sheet:" + e, "INFO");
                }

            }
            whichRow = whichRow + 1;
        }
        return whichRow;
    }

    /**
     * Get datasheet name and type of data needs to return
     */
    public String[] getDataSheetName(String methodName) {
        String[] data = new String[2];

        switch (methodName) {

            case "verifyLoginTest":
                data[0] = "VerifyLogins.xlsx";
                data[1] = "Admin";
                break;
            case "verifyCheckoutTest":
                data[0] = "VerifyLogins.xlsx";
                data[1] = "User1";
                break;
            default:
                data[0] = "VerifyLogins.xlsx";
                data[1] = "IGNORE";
                break;
        }
        return data;
    }

}
