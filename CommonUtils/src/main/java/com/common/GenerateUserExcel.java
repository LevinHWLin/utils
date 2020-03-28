package com.common;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class GenerateUserExcel {

    private static final String FILE_PATH = "C:\\Users\\Administrator\\Desktop\\用户客情数据统计\\20200229\\";

    public static Map<String, String> handlFileData(String fileName) {
        System.out.println("解析文件："+fileName);
        Map<String, String> resultMap = new HashMap<String, String>();
        File file = new File(FILE_PATH + fileName);
        try {
            String encoding = "UTF-8";
            if (file.isFile() && file.exists()) { // 判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);// 考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    String[] lineTxts = lineTxt.split("#");
                    if (resultMap.containsKey(lineTxts[0])) {
                        String value = resultMap.get(lineTxts[0]);
                        value = value.concat(";").concat(lineTxts[1]);
                        System.out.println(">>>>>>>>>>>>>>>>>>>>"+lineTxts[0] +"  value="+value);
                        resultMap.put(lineTxts[0], value);
                    } else {
                        resultMap.put(lineTxts[0], lineTxts[1]);
                    }

                }
                read.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultMap;
    }

    public static void getUserTelFile(int sheetNum){
        try {
            File xlsFile = new File(FILE_PATH + "用户客情表.xlsx");
            // 获得工作簿
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(xlsFile));
            // 获得工作表
            XSSFSheet sheet = workbook.getSheetAt(sheetNum);

            int rows1 = sheet.getPhysicalNumberOfRows();

            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < rows1; i++) {
                // 获取第i行数据
                XSSFRow sheetRow = sheet.getRow(i);
                if (null == sheetRow) {
                    break;
                }
                // 获取第0格数据
                XSSFCell idCell = sheetRow.getCell(2);
                if (idCell == null) {
                    break;
                }
                String value = idCell.getRawValue();
                //System.out.println(idCell.getRawValue());
                if (value != null && value.length() > 10) {
                    buffer.append("'").append(value).append("',");
                }
            }

            String fileName = FILE_PATH + sheet.getSheetName()+".sql";
            System.out.println("文件路径："+fileName);
            String encoding = "UTF-8";
            File file = new File(fileName);
            FileOutputStream fs = new FileOutputStream(file);
            PrintStream p = new PrintStream(fs);

            String userTel = buffer.toString();
            userTel = userTel.substring(0, userTel.length() - 1);

            String queryResgiterSql = "select tel,CONCAT(IF(DATE_FORMAT(create_time, '%Y-%m-%d') < '2020-02-15','是','不是'),'-','(',DATE_FORMAT(create_time, '%Y-%m-%d'),')注册') AS register" +
                    " from t_user where tel in (" + userTel +") ORDER BY tel;\r\n";
            p.println(queryResgiterSql);

            String queryVipSql = "SELECT u.tel, CONCAT(IF(DATE_FORMAT(v.create_time, '%Y-%m-%d') < '2020-02-15','老VIP','新VIP'),'-',tv.`title`,'(',DATE_FORMAT(v.due_time, '%Y-%m-%d'),')') AS vip," +
                    "v.due_time,u.type,v.create_time FROM t_user_vip v LEFT JOIN t_user u ON v.user_id = u.id LEFT JOIN t_vip tv ON tv.id = v.vip_id " +
                    "WHERE u.tel IN ( " + userTel + ") order by u.tel;\r\n";
            p.println(queryVipSql);

            String queryBuySql = "SELECT u.tel,sum(o.buy_price)/100 FROM t_order o LEFT JOIN t_user u ON o.user_id = u.id " +
                    "WHERE o.pay_type in (0,1) and o.`status`=0 and u.tel IN ( " + userTel + ") GROUP BY o.user_id ORDER BY u.tel;\r\n";
            p.println(queryBuySql);

            p.close();
            fs.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
//        for(int i = 0;i<5;i++){
////            getUserTelFile(i);
////        }

//        handleExcel(0,"15日余水静老师直播");
//        handleExcel(1,"16日王立成老师直播");
//        handleExcel(2,"17日张戈壁老师直播");
//        handleExcel(3,"18日吴老三老师直播");
        handleExcel(4,"19日龚金才老师直播");
//        handleExcel(5,"2月6日葡萄");
//        handleExcel(6,"2月6日草莓");
//        handleExcel(7,"2月6日猕猴桃");
//        handleExcel(8,"2月7日草莓");
//        handleExcel(9,"2月7日猕猴桃");
//        handleExcel(10,"2月7日葡萄");
//        handleExcel(11,"2月9日柑橘");
//        handleExcel(12,"2月10日猕猴桃");
//        handleExcel(13,"2月11日葡萄");

    }

    public static void handleExcel( int sheetNum, String fileName){
        try {
            Map<String, String> registerMap = handlFileData(fileName + "注册.txt");
            Map<String, String> vipMap = handlFileData(fileName + "VIP.txt");
            Map<String, String> buyMap = handlFileData(fileName + "实际消费.txt");

            File xlsFile = new File(FILE_PATH + "用户客情表.xlsx");
            // 获得工作簿
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(
                    xlsFile));
            // 获得工作表
            XSSFSheet sheet1 = workbook.getSheetAt(sheetNum);
            System.out.println("第"+sheetNum+"页签，页签名："+sheet1.getSheetName());
            int rows1 = sheet1.getPhysicalNumberOfRows();
           // System.out.println(rows1);
            for (int i = 0; i < rows1; i++) {

                // 获取第i行数据
                XSSFRow sheetRow = sheet1.getRow(i);
                if (null == sheetRow) {
                    System.out.println("=====================================行为空");
                    break;
                }

//                XSSFCell cell = sheetRow.getCell(0);
//                if("昵称".equals(cell.getStringCellValue())){
//                    continue;
//                }

                // 获取第0格数据
                XSSFCell idCell = sheetRow.getCell(2);
                if(idCell == null){
                    System.out.println("=====================================列为空");
                    break;
                }
                String value = idCell.getRawValue();
                //System.out.println(idCell.getRawValue());
                 //System.out.println(sheetRow.getCell(5).toString()+"<>"+sheetRow.getCell(6).toString()+"<>"+sheetRow.getCell(7).toString());
                if (registerMap.containsKey(value)) {
                    sheetRow.getCell(5).setCellValue(registerMap.get(value));
                }
                else if(value != null && sheetRow.getCell(5) != null){
                    sheetRow.getCell(5).setCellValue("未注册");
                }

                if (vipMap.containsKey(value)) {
                    try{
                        sheetRow.getCell(6).setCellValue(vipMap.get(value));
                    }catch(Exception e1){

                    }
                }
                else if(value != null){
                    if(sheetRow.getCell(6) != null){
                        sheetRow.getCell(6).setCellValue("未购买");
                    }
                }

                if (buyMap.containsKey(value)) {
                    sheetRow.getCell(7).setCellValue(buyMap.get(value));
                }
                else if(value != null && sheetRow.getCell(7) != null){
                    sheetRow.getCell(7).setCellValue(0);
                }
            }
            FileOutputStream os = new FileOutputStream(xlsFile);
            workbook.write(os);
            workbook.close();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void handleExcel(){
        try {
            Map<String, String> registerMap = handlFileData("2月11日葡萄注册");
            Map<String, String> vipMap = handlFileData("2月11日葡萄VIP");
            Map<String, String> buyMap = handlFileData("2月11日葡萄实际消费");
            int sheetNum = 13;

            File xlsFile = new File(FILE_PATH + "用户客情表.xlsx");
            // 获得工作簿
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(
                    xlsFile));
            // 获得工作表
            XSSFSheet sheet1 = workbook.getSheetAt(sheetNum);

            int rows1 = sheet1.getPhysicalNumberOfRows();
            System.out.println(rows1);
            for (int i = 0; i < rows1; i++) {

                // 获取第i行数据
                XSSFRow sheetRow = sheet1.getRow(i);
                if (null == sheetRow) {
                    break;
                }
                // 获取第0格数据
                XSSFCell idCell = sheetRow.getCell(2);
                if(idCell == null){
                    break;
                }
                String value = idCell.getRawValue();
                System.out.println(idCell.getRawValue());
                // System.out.println(sheetRow.getCell(5).toString()+"<>"+sheetRow.getCell(6).toString()+"<>"+sheetRow.getCell(7).toString());
                if (registerMap.containsKey(value)) {
                    sheetRow.getCell(5).setCellValue(registerMap.get(value));
                }else if(value != null && sheetRow.getCell(5) != null){
                    sheetRow.getCell(5).setCellValue("否");
                }

                if (vipMap.containsKey(value)) {
                    try{
                        sheetRow.getCell(6).setCellValue(vipMap.get(value));
                    }catch(Exception e1){

                    }
                }else if(value != null){
                    if(sheetRow.getCell(6) != null){
                        sheetRow.getCell(6).setCellValue("未购买");
                    }

                }
//
                if (buyMap.containsKey(value)) {
                    sheetRow.getCell(7).setCellValue(buyMap.get(value));
                }else if(value != null && sheetRow.getCell(7) != null){
                    sheetRow.getCell(7).setCellValue(0);
                }
            }
            FileOutputStream os = new FileOutputStream(xlsFile);
            workbook.write(os);
            workbook.close();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
