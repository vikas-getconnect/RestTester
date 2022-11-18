package utils;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class ExcelDataProvider {
    public static Logger logger = LoggerFactory.getLogger(ExcelDataProvider.class);

    public static void main(String[] args) {
        ExcelDataProvider ex = new ExcelDataProvider();
        String file = System.getProperty("user.dir") + "/src/test/resources/testdata/users.xls";
        logger.info(file);
        String data[][] = ex.getTableArray(file, "emails", "email");
        System.out.println(data);
        for (String[] row : data) {
            for (String element : row) {
                System.out.println(element);
            }

        }
    }

    /**
     * @param xlFilePath
     * @param sheetName
     * @param tableName
     * @return
     */
    public String[][] getTableArray(String xlFilePath, String sheetName,
                                    String tableName) {


        String[][] tabArray = null;

        try {

            File initialFile = new File(xlFilePath);
            InputStream in = new FileInputStream(initialFile);
            System.out.println(in);
            Workbook workbook = Workbook.getWorkbook(in);
            Sheet sheet = workbook.getSheet(sheetName);
            int startRow, startCol, endRow, endCol, ci, cj;
            Cell tableStart = sheet.findCell(tableName);
            startRow = tableStart.getRow();
            startCol = tableStart.getColumn();

            Cell tableEnd = sheet.findCell(tableName, startCol + 1,
                    startRow + 1, 100, 64000, false);

            endRow = tableEnd.getRow();
            endCol = tableEnd.getColumn();
            tabArray = new String[endRow - startRow - 1][endCol - startCol - 1];
            ci = 0;

            for (int i = startRow + 1; i < endRow; i++, ci++) {
                cj = 0;
                for (int j = startCol + 1; j < endCol; j++, cj++) {
                    tabArray[ci][cj] = sheet.getCell(j, i).getContents();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return (tabArray);
    }

    /**
     * @param xlFilePath
     * @param sheetName
     * @param tableName
     * @return
     */
    public String[][] xw(String xlFilePath, String sheetName,
                         String tableName) {

        String[][] tabArray = null;

        try {

            ClassLoader cl = getClass().getClassLoader();
            InputStream in = cl.getResourceAsStream(xlFilePath);
            Workbook workbook = Workbook.getWorkbook(in);
            Sheet sheet = workbook.getSheet(sheetName);
            int startRow, startCol, endRow, endCol, ci, cj;
            Cell tableStart = sheet.findCell(tableName);
            startRow = tableStart.getRow() - 1;
            startCol = tableStart.getColumn();

            Cell tableEnd = sheet.findCell(tableName, startCol + 1,
                    startRow + 1, 100, 64000, false);

            endRow = tableEnd.getRow();
            endCol = tableEnd.getColumn();
            tabArray = new String[endRow - startRow - 1][endCol - startCol - 1];
            ci = 0;

            for (int i = startRow + 1; i < endRow; i++, ci++) {
                cj = 0;
                for (int j = startCol + 1; j < endCol; j++, cj++) {
                    tabArray[ci][cj] = sheet.getCell(j, i).getContents();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return (tabArray);
    }
}
