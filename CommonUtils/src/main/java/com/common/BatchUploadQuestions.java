package com.common;

import com.common.enums.AnswerOptionsEnum;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Iterator;

public class BatchUploadQuestions {

    public static void main(String[] args) throws IOException {

        String fileName = "C:\\Users\\Administrator\\Desktop\\upload_questions.xlsx";

        handlerExcel(fileName);
    }

    public static void handlerExcel(String fileName) throws FileNotFoundException, IOException
    {
        File xlsFile = new File(fileName);
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

        int answerChooseToIndex = 3;
        int answerChooseFromIndex = 10;

        StringBuilder sqlBuilder = new StringBuilder();
        for (int i = 1; i < rows; i++) {

            // 获取第i行数据
            XSSFRow sheetRow = sheet.getRow(i);
            if(null == sheetRow) {
                break;
            }

            XSSFCell questionTypeCell = sheetRow.getCell(0);
            String questionTypeText = questionTypeCell.toString();

            XSSFCell questionContentCell = sheetRow.getCell(1);
            String questionContent = questionContentCell.toString();

            sqlBuilder.append("'").append(questionTypeText).append(": index:").append(questionTypeCell.getColumnIndex()).append("',");
            sqlBuilder.append("'").append(questionContent).append(": index:").append(questionContentCell.getColumnIndex()).append("',");

            if(!"问答题".equals(questionTypeText)){
                XSSFCell answerAnalyseTextCell = sheetRow.getCell(2);
                XSSFCell answerCell = sheetRow.getCell(3);
                String answer = answerCell.toString();

                String formatAnswer = AnswerOptionsEnum.fomartAnswerIndex(answer);
                sqlBuilder.append("'").append(answerAnalyseTextCell.toString()).append(": index:").append(answerAnalyseTextCell.getColumnIndex()).append("',");
                sqlBuilder.append("'").append(answerCell.toString()).append(": index:").append(answerCell.getColumnIndex()).append("',");

                Iterator iterator = sheetRow.cellIterator();
                while (iterator.hasNext()){
                    XSSFCell cell = (XSSFCell) iterator.next();

                    int columnIndex = cell.getColumnIndex();
                    if( columnIndex > answerChooseToIndex && columnIndex < answerChooseFromIndex){
                        sqlBuilder.append("'").append(cell.toString()).append(": index:").append(cell.getColumnIndex()).append(",");
                    }
                }
            }

            sqlBuilder.append("\r\n");

//            // 获取第0格数据
//            XSSFCell idCell = sheetRow.getCell(0);
//            // 调用toString方法获取内容
//            //System.out.println(idCell.toString().replace(".0", ""));
//
//            // 获取第0格数据
//            XSSFCell titleCell = sheetRow.getCell(1);
//
//            // 获取第0格数据
//            XSSFCell contentCell = sheetRow.getCell(2);
//
//            sqlBuilder.append("'").append(idCell.getRawValue()).append("',").append(titleCell.getRawValue()).append(", ").append(contentCell.getRawValue()).append("/r/n");

//            if(idCell.toString() == "" || idCell.toString() == null) {
//                continue;
//            }
//
//            sqlBuilder.append("'").append(idCell.getRawValue()).append("',");
        }

        String encoding = "UTF-8";

//        String generPath = "C:\\Users\\Administrator\\Desktop\\问答社区日志\\symphony_article.sql";
//        FileOutputStream fs = new FileOutputStream(new File(generPath));
//        PrintStream p = new PrintStream(fs, true, encoding);

//        p.println(sqlBuilder.toString());
        System.out.println(sqlBuilder.toString());

//        p.close();
//        fs.close();
        workbook.close();
    }
}
