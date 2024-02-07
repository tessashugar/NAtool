import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.ArrayList;
 
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelWriter {
    private Workbook workbook;
    private Sheet sheet = null;

    public ExcelWriter() {
        workbook = new XSSFWorkbook();
    }

    public void createSheet(String sheetName) {
        sheet = workbook.createSheet(sheetName);
    }

    public void addHeaders(String[] headers) {
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }
    }

    public void writeData(ArrayList<String> data) {
        for (int rowNum = 0; rowNum < data.size(); rowNum++) {
            Row row = sheet.createRow(rowNum + 1);
            String contactInfo = data.get(rowNum);
            String[] contactInfoArray = contactInfo.split("\n");
            for (int i = 0; i < contactInfoArray.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(contactInfoArray[i]);
            }
        }
    }

    public void saveToFile(String fileName) {
        try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
            workbook.write(outputStream);
            System.out.println("Data saved to " + fileName);
        } catch (IOException e) {
            System.err.println("Error saving data to Excel file: " + e.getMessage());
        }
    }
} //end class