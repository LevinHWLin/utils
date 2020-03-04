package com.common.generate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class GenerateExcel
{

    public static void main(String[] args) throws FileNotFoundException, IOException
    {
       //generateSql();

        File xlsFile = new File("C:\\Users\\Administrator\\Desktop\\问答社区日志\\symphony_article_update20200120.xlsx");
        /**
         * 这里根据不同的excel类型
         * 可以选取不同的处理类：
         *          1.XSSFWorkbook
         *          2.HSSFWorkbook
         */
        // 获得工作簿
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(xlsFile));

        // 获得工作表
        XSSFSheet sheet = workbook.getSheetAt(0);

        int rows = sheet.getPhysicalNumberOfRows();
        System.out.println(rows);
        StringBuilder sqlBuilder = new StringBuilder();
        for (int i = 0; i < rows; i++) {

            // 获取第i行数据
            XSSFRow sheetRow = sheet.getRow(i);
            if(null == sheetRow) {
                break;
            }
            // 获取第0格数据
            XSSFCell idCell = sheetRow.getCell(0);
            // 调用toString方法获取内容
            //System.out.println(idCell.toString().replace(".0", ""));

            // 获取第0格数据
            XSSFCell titleCell = sheetRow.getCell(1);

            // 获取第0格数据
            XSSFCell contentCell = sheetRow.getCell(2);

            if(idCell.toString() == "" || idCell.toString() == null) {
                continue;
            }

            sqlBuilder.append("update symphony_article set articleTitle='")
                    .append(titleCell.toString().trim()).append("', articleContent='")
                    .append(contentCell.toString().trim()).append("' where oId='")
                    .append(idCell.toString().replace(".0", "")).append("';").append("\r\n");
        }

        String encoding = "UTF-8";

        String generPath = "C:\\Users\\Administrator\\Desktop\\问答社区日志\\symphony_article.sql";
        FileOutputStream fs = new FileOutputStream(new File(generPath));
        PrintStream p = new PrintStream(fs, true, encoding);

        p.println(sqlBuilder.toString());
        //System.out.println(sqlBuilder.toString());

        p.close();
        fs.close();
        workbook.close();
    }

    public static void generateSql() throws FileNotFoundException, IOException
    {
        File xlsFile = new File("C:\\Users\\Administrator\\Desktop\\问答社区日志\\1125技术开通VIP名单.xlsx");
        /**
         * 这里根据不同的excel类型
         * 可以选取不同的处理类：
         *          1.XSSFWorkbook
         *          2.HSSFWorkbook
         */
        // 获得工作簿
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(xlsFile));

        // 获得工作表
        XSSFSheet sheet = workbook.getSheetAt(0);

        int rows = sheet.getPhysicalNumberOfRows();
        System.out.println(rows);
        StringBuilder sqlBuilder = new StringBuilder();
        for (int i = 0; i < rows; i++) {

            // 获取第i行数据
            XSSFRow sheetRow = sheet.getRow(i);
            if(null == sheetRow) {
                break;
            }
            // 获取第0格数据
            XSSFCell idCell = sheetRow.getCell(0);
            // 调用toString方法获取内容
            //System.out.println(idCell.toString().replace(".0", ""));

            // 获取第0格数据
            XSSFCell titleCell = sheetRow.getCell(1);

            // 获取第0格数据
            XSSFCell contentCell = sheetRow.getCell(2);

            if(idCell.toString() == "" || idCell.toString() == null) {
                continue;
            }

            sqlBuilder.append("'").append(idCell.getRawValue()).append("',");
        }

        String encoding = "UTF-8";

        String generPath = "C:\\Users\\Administrator\\Desktop\\问答社区日志\\symphony_article.sql";
        FileOutputStream fs = new FileOutputStream(new File(generPath));
        PrintStream p = new PrintStream(fs, true, encoding);

        p.println(sqlBuilder.toString());
        System.out.println(sqlBuilder.toString());

        p.close();
        fs.close();
        workbook.close();
    }

}

