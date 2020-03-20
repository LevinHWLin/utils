package com.common;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.math.BigDecimal;
import java.util.Random;

public class GenerateNum {

    public static void main(String[] args) throws IOException {
        System.out.println();

        String fileName = "F://速算题.xlsx";
        File file = new File(fileName);
        if(!file.exists()){
            file.createNewFile();
        }

        // 获得工作簿
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
        createSheet(workbook, "第二份");
        createSheet(workbook, "第三份");
        createSheet(workbook, "第四份");
        createSheet(workbook, "第五份");
        createSheet(workbook, "第六份");

        FileOutputStream os = new FileOutputStream(file);
        workbook.write(os);
        workbook.close();
        os.close();
    }

    public static void createSheet(XSSFWorkbook workbook,String sheetName){
        XSSFSheet sheet = workbook.getSheet(sheetName);
        if(null == sheet){
            sheet = workbook.createSheet(sheetName);
        }

        setTitle(sheet,0);

        for(int i = 1;i < 72;i++){
            XSSFRow valueRow = sheet.createRow(i);
            valueRow.setHeight(new Integer(20).shortValue());
            int[] values = new int[2];
            for(int j=0;j<2;j++){
                int radomInt = new Random().nextInt(900)+100;
                XSSFCell cell = valueRow.createCell(j);
                cell.setCellValue(radomInt);
                values[j] = radomInt;
            }

            BigDecimal b1 = new BigDecimal(values[0]);
            BigDecimal b2 = new BigDecimal(values[1]);

            XSSFCell valueCell2 = valueRow.createCell(2);
            valueCell2.setCellValue(b1.add(b2).intValue());

            XSSFCell valueCell3 = valueRow.createCell(3);
            valueCell3.setCellValue(b1.subtract(b2).intValue());

            XSSFCell valueCell4 = valueRow.createCell(4);
            valueCell4.setCellValue(b1.divide(b2,2,BigDecimal.ROUND_DOWN).toString());
        }

        setTitle(sheet,72);
        for(int i = 73;i < 144;i++){
            XSSFRow valueRow = sheet.createRow(i);
            int[] values = new int[2];
            for(int j=0;j<2;j++){
                int radomInt = new Random().nextInt(90)+10;
                XSSFCell cell = valueRow.createCell(j);
                cell.setCellValue(radomInt);
                values[j] = radomInt;
            }

            BigDecimal b1 = new BigDecimal(values[0]);
            BigDecimal b2 = new BigDecimal(values[1]);

            XSSFCell valueCell2 = valueRow.createCell(2);
            valueCell2.setCellValue(b1.add(b2).intValue());

            XSSFCell valueCell3 = valueRow.createCell(3);
            valueCell3.setCellValue(b1.subtract(b2).intValue());

            XSSFCell valueCell4 = valueRow.createCell(4);
            valueCell4.setCellValue(b1.divide(b2,2,BigDecimal.ROUND_DOWN).toString());
        }
    }

    public static void setTitle(XSSFSheet sheet, int row){
        XSSFRow titleRow = sheet.createRow(row);
        XSSFCell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("A");
        XSSFCell titleCell1 = titleRow.createCell(1);
        titleCell1.setCellValue("B");
        XSSFCell titleCell2 = titleRow.createCell(2);
        titleCell2.setCellValue("A+B");
        XSSFCell titleCell3 = titleRow.createCell(3);
        titleCell3.setCellValue("A-B");
        XSSFCell titleCell4 = titleRow.createCell(4);
        titleCell4.setCellValue("A/B");
    }
}
