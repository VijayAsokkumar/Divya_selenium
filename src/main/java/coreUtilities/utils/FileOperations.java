package coreUtilities.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;

public class FileOperations 
{
	public JSONParser jsonParser;
	public JSONObject jsonObject;
	public Fillo fillo;
	public Connection connection;
	public Properties properties;
	
	/**
     * This method is useful to read the excel sheet based on the Filename and sheet name. It'll return the values for the respective
     * sheet in {@link Map} where the first column name as a key and the value as per the value entered in econd column.
     * @param excelFilePath - {@link String} excel sheet location
     * @param sheetName - {@link String} Sheet name to read the excel
     * @return {@link Map}
     * @throws Exception
     */
        public Map<String, String> readExcelPOI(String excelFilePath, String sheetName) throws FilloException
        {
            Map<String, String> excelData = new HashMap<>();
            try (FileInputStream inputStream = new FileInputStream(excelFilePath);
                    Workbook workbook = new XSSFWorkbook(inputStream))
            {
                Sheet sheet = workbook.getSheet(sheetName);
                if (sheet == null)
                {
                    throw new FilloException("Sheet '" + sheetName + "' not found in file: " + excelFilePath);
                }

                DataFormatter dataFormatter = new DataFormatter();
                Iterator<Row> rowIterator = sheet.iterator();
                while (rowIterator.hasNext())
                {
                    Row row = rowIterator.next();
                    Cell keyCell = row.getCell(0);
                    if (keyCell == null)
                    {
                        continue;
                    }

                    String key = dataFormatter.formatCellValue(keyCell).trim();
                    if (key.isEmpty())
                    {
                        continue;
                    }

                    Cell valueCell = row.getCell(1);
                    String value = valueCell != null ? dataFormatter.formatCellValue(valueCell).trim() : "";
                    excelData.put(key, value);
                }
            }
            catch (IOException exception)
            {
                throw new FilloException(
                        "Unable to read excel file '" + excelFilePath + "': " + exception.getMessage());
            }

            return excelData;
        }

	
}
