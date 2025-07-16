package com.botplus.algotrade.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.nio.file.*;

public class CsvToExcelConverter {

    public static void main(String[] args) {
        String inputCsvPath = "stocks/ADANIENT-EQ-14-07-2024-to-14-07-2025.csv";
        String outputXlsxPath = "stocks/NEWADANIENT-EQ-14-07-2024-to-14-07-2025.xlsx";

        try (
            BufferedReader reader = Files.newBufferedReader(Paths.get(inputCsvPath));
            Workbook workbook = new XSSFWorkbook();
            FileOutputStream fileOut = new FileOutputStream(outputXlsxPath)
        ) {
            Sheet sheet = workbook.createSheet("Sheet1");
            String line;
            int rowNum = 0;

            while ((line = reader.readLine()) != null) {
                Row row = sheet.createRow(rowNum++);
                String[] data = line.split(",", -1); // Keep empty cells too

                System.out.print("\n");
                for (int i = 0; i < data.length; i++) {
                    Cell cell = row.createCell(i);
                    
                    
/*                    
                    
                    // Remove surrounding quotes if any
                    String cellValue = cells[i].trim();
                    if (cellValue.startsWith("\"") && cellValue.endsWith("\"")) {
                        cellValue = cellValue.substring(1, cellValue.length() - 1);
                    }

                    // Try to write numeric value, else write as string
                    if (cellValue.matches("-?\\d+(\\.\\d+)?")) {
                        cell.setCellValue(Double.parseDouble(cellValue));
                    } else {
                        cell.setCellValue(cellValue);
                    }
                    
*/
                    
                    System.out.print("\t" + data[i]);
                    
                    
                }
            }

            workbook.write(fileOut);
            System.out.println("Excel written to " + outputXlsxPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
