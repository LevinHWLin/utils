package com.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class GenerateArticleExcel
{

    public static void main(String[] args)
            throws FileNotFoundException,
            IOException
    {
        try{
            String[] fileNames = {"symphony_article_base.xlsx","symphony_article202002280931.xlsx"};
            String cleanFilePath = "C:\\Users\\Administrator\\Desktop\\问答社区日志\\symphony_article20200306.xlsx";
            String filePath = "C:\\Users\\Administrator\\Desktop\\问答社区日志\\";

        for(String fileName : fileNames){
            System.out.println("过滤文件："+(filePath + fileName));
            cleanRepeatArticle(filePath + fileName,cleanFilePath);
        }

            //delNullRow(cleanFilePath);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void cleanRepeatArticle(String filePath,String cleanFilePath) throws IOException {
        File xlsFile = new File(filePath);
        File xlsFile1 = new File(cleanFilePath);
        /**
         * 这里根据不同的excel类型 可以选取不同的处理类： 1.XSSFWorkbook 2.HSSFWorkbook
         */
        // 获得工作簿
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(xlsFile));
        // 获得工作表
        XSSFSheet sheet = workbook.getSheetAt(0);

        int rows = sheet.getPhysicalNumberOfRows();
        System.out.println(rows);
        Set<String> idSet = new HashSet<String>();
        StringBuilder sqlBuilder = new StringBuilder();
        for (int i = 0; i < rows; i++ ) {

            // 获取第i行数据
            XSSFRow sheetRow = sheet.getRow(i);
            if (null == sheetRow) {
                break;
            }
            // 获取第0格数据
            XSSFCell idCell = sheetRow.getCell(0);
            // 调用toString方法获取内容
            // System.out.println(idCell.toString().replace(".0", ""));
            idSet.add(idCell.toString());
        }
        workbook.close();
        System.out.println("oId size : "+idSet.size());
        // 获得工作簿
        XSSFWorkbook workbook1 = new XSSFWorkbook(new FileInputStream(xlsFile1));
        // 获得工作表
        XSSFSheet sheet1 = workbook1.getSheetAt(0);

        int rows1 = sheet1.getPhysicalNumberOfRows();
        System.out.println(rows1);
        for (int i = 0; i < rows1; i++ ) {

            // 获取第i行数据
            XSSFRow sheetRow = sheet1.getRow(i);
            if (null == sheetRow) {
                break;
            }
            // 获取第0格数据
            XSSFCell idCell = sheetRow.getCell(0);
            if(idSet.contains(idCell.toString())) {
                System.out.println("删除"+idCell.toString());
                sheet1.removeRow(sheetRow);
            }
        }
        FileOutputStream os = new FileOutputStream(xlsFile1);
        workbook1.write(os);
        workbook1.close();
        os.close();
    }

    public static void delNullRow(String cleanFilePath) throws IOException {
        File xlsFile1 = new File(cleanFilePath);
        // 获得工作簿
        XSSFWorkbook workbook1 = new XSSFWorkbook(new FileInputStream(xlsFile1));
        // 获得工作表
        XSSFSheet sheet1 = workbook1.getSheetAt(0);
        System.out.println(sheet1.getSheetName());
        int rows1 = sheet1.getPhysicalNumberOfRows();
        System.out.println("读取行数："+rows1);
        Iterator integer = sheet1.iterator();
//        while (integer.hasNext()){
//            Row row = (Row) integer.next();
//            System.out.println(row.isFormatted()+ "第一列："+row.getCell(1));
//        }

        int delRow=0;
        for (int i = 0; i < rows1; i++ ) {

            // 获取第i行数据
            XSSFRow sheetRow = sheet1.getRow(i);
            if (null == sheetRow) {
                sheet1.removeMergedRegion(i);
                ++delRow;
            }
        }
        System.out.println("共删除空白行："+delRow);
        FileOutputStream os = new FileOutputStream(xlsFile1);
        workbook1.write(os);
        workbook1.close();
        os.close();
    }
}

