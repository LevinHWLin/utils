package com.common;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.elasticsearch.client.security.user.User;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Generate321LiveUserExcel {

    private static final String FILE_PATH = "D:\\TTXN\\321直播客情表\\321\\";

    public static Map<String, String> handlFileData(String fileName) {
        System.out.println("解析文件：" + fileName);
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
                        System.out.println(">>>>>>>>>>>>>>>>>>>>" + lineTxts[0] + "  value=" + value);
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

    public static Map<String, UserBuyInfo> handlFileDataByUserBuyInfo(String fileName) {
        System.out.println("解析文件：" + fileName);
        Map<String, UserBuyInfo> resultMap = new HashMap<String, UserBuyInfo>();
        File file = new File(FILE_PATH + fileName);
        try {
            String encoding = "UTF-8";
            if (file.isFile() && file.exists()) { // 判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);// 考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    String[] lineTxts = lineTxt.split("###");
                    UserBuyInfo userBuyInfo = new UserBuyInfo();
                    userBuyInfo.setTel(lineTxts[0]);
                    userBuyInfo.setRegister(lineTxts[1]);
                    //userBuyInfo.setRegister(StringUtils.equals("是",lineTxts[1]) ? "否":"是");
                    userBuyInfo.setBuyVip(lineTxts[2]);
                    userBuyInfo.setBuyVipMoney(lineTxts[3]);
                    userBuyInfo.setBuySubject(lineTxts[4]);
                    userBuyInfo.setBuySubjectMoney(lineTxts[5]);
                    userBuyInfo.setBuySSubject(lineTxts[6]);
                    userBuyInfo.setBuySSubjectMoney(lineTxts[7]);
                    userBuyInfo.setBuyVip1(lineTxts[8]);
                    userBuyInfo.setBuyVipMoney1(lineTxts[9]);
                    userBuyInfo.setBuySSubject1(lineTxts[10]);
                    userBuyInfo.setBuySSubjectMoney1(lineTxts[11]);
                    userBuyInfo.setBuySum(lineTxts[12]);

                     resultMap.put(userBuyInfo.getTel(), userBuyInfo);

                }
                read.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultMap;
    }

    public static void getUserTelFile() {
        try {
            File xlsFilePath = new File(FILE_PATH);
            for (File xlsfile : xlsFilePath.listFiles()) {
                if (!(xlsfile.getName().endsWith(".xls") || xlsfile.getName().endsWith(".xlsx"))) {
                    continue;
                }
                String xlsFileName = xlsfile.getName().replace(".xlsx","");

                System.out.println(xlsfile.getName());
                // 获得工作簿
                XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(xlsfile));
                int sheetName = workbook.getNumberOfSheets();
                for (int index = 0; index < sheetName; index++) {
                    // 获得工作表
                    XSSFSheet sheet = workbook.getSheetAt(index);

                    int rows1 = sheet.getPhysicalNumberOfRows();

                    StringBuffer buffer = new StringBuffer();
                    for (int i = 0; i < rows1; i++) {
                        // 获取第i行数据
                        XSSFRow sheetRow = sheet.getRow(i);
                        if (null == sheetRow) {
                            break;
                        }
                        // 获取第0格数据
                        XSSFCell idCell = sheetRow.getCell(4);
                        if (idCell == null) {
                            break;
                        }
                        String value = idCell.getRawValue();
                        //System.out.println(idCell.getRawValue());
                        if (value != null && value.length() > 10) {
                            buffer.append("'").append(value).append("',");
                        }
                    }

                    String fileName = FILE_PATH + xlsFileName + "_" + sheet.getSheetName() + ".sql";
                    String registerTime = "2020-03-21";
                    if(xlsFileName.startsWith("柑橘直播客情")){
                        switch (sheet.getSheetName()){
                            case "黄伟":
                                registerTime = "2020-03-20";
                                break;
                            case "吴老三":
                                registerTime = "2020-03-21";
                                break;
                            case "龚金才":
                                registerTime = "2020-03-22";
                                break;
                            case "莫刚凡":
                                registerTime = "2020-03-23";
                                break;
                            case "卓添":
                                registerTime = "2020-03-24";
                                break;
                        }
                    }
                    System.out.println("文件路径：" + fileName);
                    String encoding = "UTF-8";
                    File file = new File(fileName);
                    FileOutputStream fs = new FileOutputStream(file);
                    PrintStream p = new PrintStream(fs);

                    String userTel = buffer.toString();
                    userTel = userTel.substring(0, userTel.length() - 1);

                    String queryResgiterSql = gotSqlTemplete(userTel,registerTime);
                    p.println(queryResgiterSql);
                    p.close();
                    fs.close();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        getUserTelFile();
        //handleExcel();
    }

    public static void handleExcel() {
        try {
            File xlsFilePath = new File(FILE_PATH);
            for (File xlsFile : xlsFilePath.listFiles()) {
                if (!(xlsFile.getName().endsWith(".xls") || xlsFile.getName().endsWith(".xlsx"))) {
                    continue;
                }
                String xlsFileName = xlsFile.getName().replace(".xlsx", "");

                System.out.println(xlsFile.getName());
                // 获得工作簿
                XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(xlsFile));
                int sheetName = workbook.getNumberOfSheets();
                for (int index = 0; index < sheetName; index++) {
                    // 获得工作表
                    XSSFSheet sheet = workbook.getSheetAt(index);

                    int rows1 = sheet.getPhysicalNumberOfRows();

                    Map<String, UserBuyInfo> userBuyInfoMap = handlFileDataByUserBuyInfo(xlsFileName + "_" + sheet.getSheetName() + ".txt");

                    for (int i = 0; i < rows1; i++) {

                        // 获取第i行数据
                        XSSFRow sheetRow = sheet.getRow(i);
                        if (null == sheetRow) {
                            System.out.println("=====================================行为空");
                            break;
                        }

                        // 获取第0格数据
                        XSSFCell telCell = sheetRow.getCell(4);
                        if (telCell == null) {
                            System.out.println("=====================================列为空");
                            break;
                        }

                        String tel = telCell.getRawValue();
                        if(userBuyInfoMap.containsKey(tel)) {
                            UserBuyInfo userBuyInfo = userBuyInfoMap.get(tel);
                            sheetRow.createCell(8).setCellValue(userBuyInfo.getRegister());
                            sheetRow.createCell(9).setCellValue(userBuyInfo.getBuyVip());
                            sheetRow.createCell(10).setCellValue(userBuyInfo.getBuyVipMoney());
                            sheetRow.createCell(11).setCellValue(userBuyInfo.getBuySubject());
                            sheetRow.createCell(12).setCellValue(userBuyInfo.getBuySubjectMoney());
                            sheetRow.createCell(13).setCellValue(userBuyInfo.getBuySSubject());
                            sheetRow.createCell(14).setCellValue(userBuyInfo.getBuySSubjectMoney());
                            sheetRow.createCell(15).setCellValue(userBuyInfo.getBuyVip1());
                            sheetRow.createCell(16).setCellValue(userBuyInfo.getBuyVipMoney1());
                            sheetRow.createCell(17).setCellValue(userBuyInfo.getBuySSubject1());
                            sheetRow.createCell(18).setCellValue(userBuyInfo.getBuySSubjectMoney1());
                            //sheetRow.getCell(19).setCellValue(userBuyInfo.getBuySum());
                        }
                    }
                }
                FileOutputStream os = new FileOutputStream(xlsFile);
                workbook.write(os);
                workbook.close();
                os.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String gotSqlTemplete(String tel, String registerTime) {
        String querySql = "SELECT\n" +
                "\tu.tel,\n" +
                "\t(\n" +
                "\t\tCASE\n" +
                "\t\tWHEN DATE_FORMAT(u.create_time, '%Y-%m-%d') < '"+registerTime+"' THEN\n" +
                "\t\t\t'是'\n" +
                "\t\tELSE\n" +
                "\t\t\t'否'\n" +
                "\t\tEND\n" +
                "\t) AS register,\n" +
                "\t(\n" +
                "\t\tCASE\n" +
                "\t\tWHEN (\n" +
                "\t\t\tSELECT\n" +
                "\t\t\t\tcount(1)\n" +
                "\t\t\tFROM\n" +
                "\t\t\t\tt_order o\n" +
                "\t\t\tLEFT JOIN t_goods_info g ON g.id = o.goods_info_id\n" +
                "\t\t\tWHERE\n" +
                "\t\t\t\tuser_id = u.id\n" +
                "\t\t\tAND g.type = 3\n" +
                "\t\t\tAND `status` = 0\n" +
                "\t\t\tAND (pay_type = 0 OR pay_type = 1)\n" +
                "\t\t\tAND DATE_FORMAT(o.create_time, '%Y-%m-%d') <= '2020-03-19'\n" +
                "\t\t) > 0 THEN\n" +
                "\t\t\t'是'\n" +
                "\t\tELSE\n" +
                "\t\t\t'否'\n" +
                "\t\tEND\n" +
                "\t) AS '3月19日前是否购买VIP',\n" +
                "\t(\n" +
                "\t\tSELECT\n" +
                "\t\t\tIFNULL(SUM(buy_price) / 100, 0)\n" +
                "\t\tFROM\n" +
                "\t\t\tt_order o\n" +
                "\t\tLEFT JOIN t_goods_info g ON g.id = o.goods_info_id\n" +
                "\t\tWHERE\n" +
                "\t\t\tuser_id = u.id\n" +
                "\t\tAND g.type = 3\n" +
                "\t\tAND `status` = 0\n" +
                "\t\tAND (pay_type = 0 OR pay_type = 1)\n" +
                "\t\tAND DATE_FORMAT(o.create_time, '%Y-%m-%d') <= '2020-03-19'\n" +
                "\t) AS '购买VIP金额',\n" +
                "\t(\n" +
                "\t\tCASE\n" +
                "\t\tWHEN (\n" +
                "\t\t\tSELECT\n" +
                "\t\t\t\tcount(1)\n" +
                "\t\t\tFROM\n" +
                "\t\t\t\tt_order o\n" +
                "\t\t\tLEFT JOIN t_goods_info g ON g.id = o.goods_info_id\n" +
                "\t\t\tWHERE\n" +
                "\t\t\t\tuser_id = u.id\n" +
                "\t\t\tAND g.type = 0\n" +
                "\t\t\tAND `status` = 0\n" +
                "\t\t\tAND (pay_type = 0 OR pay_type = 1)\n" +
                "\t\t\tAND DATE_FORMAT(o.create_time, '%Y-%m-%d') <= '2020-03-19'\n" +
                "\t\t) > 0 THEN\n" +
                "\t\t\t'是'\n" +
                "\t\tELSE\n" +
                "\t\t\t'否'\n" +
                "\t\tEND\n" +
                "\t) AS '3月19日前是否购买课程',\n" +
                "\t(\n" +
                "\t\tSELECT\n" +
                "\t\t\tIFNULL(SUM(buy_price) / 100, 0)\n" +
                "\t\tFROM\n" +
                "\t\t\tt_order o\n" +
                "\t\tLEFT JOIN t_goods_info g ON g.id = o.goods_info_id\n" +
                "\t\tWHERE\n" +
                "\t\t\tuser_id = u.id\n" +
                "\t\tAND g.type = 0\n" +
                "\t\tAND `status` = 0\n" +
                "\t\tAND (pay_type = 0 OR pay_type = 1)\n" +
                "\t\tAND DATE_FORMAT(o.create_time, '%Y-%m-%d') <= '2020-03-19'\n" +
                "\t) AS '购买课程金额',\n" +
                "\t(\n" +
                "\t\tCASE\n" +
                "\t\tWHEN (\n" +
                "\t\t\tSELECT\n" +
                "\t\t\t\tcount(1)\n" +
                "\t\t\tFROM\n" +
                "\t\t\t\tt_order o\n" +
                "\t\t\tLEFT JOIN t_goods_info g ON g.id = o.goods_info_id\n" +
                "\t\t\tWHERE\n" +
                "\t\t\t\tuser_id = u.id\n" +
                "\t\t\tAND g.type = 7\n" +
                "\t\t\tAND `status` = 0\n" +
                "\t\t\tAND (pay_type = 0 OR pay_type = 1)\n" +
                "\t\t\tAND DATE_FORMAT(o.create_time, '%Y-%m-%d') <= '2020-03-19'\n" +
                "\t\t) > 0 THEN\n" +
                "\t\t\t'是'\n" +
                "\t\tELSE\n" +
                "\t\t\t'否'\n" +
                "\t\tEND\n" +
                "\t) AS '3月19日前是否购买课堂',\n" +
                "\t(\n" +
                "\t\tSELECT\n" +
                "\t\t\tIFNULL(SUM(buy_price) / 100, 0)\n" +
                "\t\tFROM\n" +
                "\t\t\tt_order o\n" +
                "\t\tLEFT JOIN t_goods_info g ON g.id = o.goods_info_id\n" +
                "\t\tWHERE\n" +
                "\t\t\tuser_id = u.id\n" +
                "\t\tAND g.type = 7\n" +
                "\t\tAND `status` = 0\n" +
                "\t\tAND (pay_type = 0 OR pay_type = 1)\n" +
                "\t\tAND DATE_FORMAT(o.create_time, '%Y-%m-%d') <= '2020-03-19'\n" +
                "\t) AS '购买课堂金额',\n" +
                "\t(\n" +
                "\t\tCASE\n" +
                "\t\tWHEN (\n" +
                "\t\t\tSELECT\n" +
                "\t\t\t\tcount(1)\n" +
                "\t\t\tFROM\n" +
                "\t\t\t\tt_order o\n" +
                "\t\t\tLEFT JOIN t_goods_info g ON g.id = o.goods_info_id\n" +
                "\t\t\tWHERE\n" +
                "\t\t\t\tuser_id = u.id\n" +
                "\t\t\tAND g.type = 3\n" +
                "\t\t\tAND `status` = 0\n" +
                "\t\t\tAND (pay_type = 0 OR pay_type = 1)\n" +
                "\t\t\tAND DATE_FORMAT(o.create_time, '%Y-%m-%d') > '2020-03-19'\n" +
                "\t\t) > 0 THEN\n" +
                "\t\t\t'是'\n" +
                "\t\tELSE\n" +
                "\t\t\t'否'\n" +
                "\t\tEND\n" +
                "\t) AS '3月19日后是否购买VIP',\n" +
                "\t(\n" +
                "\t\tSELECT\n" +
                "\t\t\tIFNULL(SUM(buy_price) / 100, 0)\n" +
                "\t\tFROM\n" +
                "\t\t\tt_order o\n" +
                "\t\tLEFT JOIN t_goods_info g ON g.id = o.goods_info_id\n" +
                "\t\tWHERE\n" +
                "\t\t\tuser_id = u.id\n" +
                "\t\tAND g.type = 3\n" +
                "\t\tAND `status` = 0\n" +
                "\t\tAND (pay_type = 0 OR pay_type = 1)\n" +
                "\t\tAND DATE_FORMAT(o.create_time, '%Y-%m-%d') > '2020-03-19'\n" +
                "\t) AS '购买VIP金额',\n" +
                "\t(\n" +
                "\t\tCASE\n" +
                "\t\tWHEN (\n" +
                "\t\t\tSELECT\n" +
                "\t\t\t\tcount(1)\n" +
                "\t\t\tFROM\n" +
                "\t\t\t\tt_order o\n" +
                "\t\t\tLEFT JOIN t_goods_info g ON g.id = o.goods_info_id\n" +
                "\t\t\tWHERE\n" +
                "\t\t\t\tuser_id = u.id\n" +
                "\t\t\tAND g.type = 7\n" +
                "\t\t\tAND `status` = 0\n" +
                "\t\t\tAND (pay_type = 0 OR pay_type = 1)\n" +
                "\t\t\tAND DATE_FORMAT(o.create_time, '%Y-%m-%d') > '2020-03-19'\n" +
                "\t\t) > 0 THEN\n" +
                "\t\t\t'是'\n" +
                "\t\tELSE\n" +
                "\t\t\t'否'\n" +
                "\t\tEND\n" +
                "\t) AS '3月19日后是否购买课堂',\n" +
                "\t(\n" +
                "\t\tSELECT\n" +
                "\t\t\tIFNULL(SUM(buy_price) / 100, 0)\n" +
                "\t\tFROM\n" +
                "\t\t\tt_order o\n" +
                "\t\tLEFT JOIN t_goods_info g ON g.id = o.goods_info_id\n" +
                "\t\tWHERE\n" +
                "\t\t\tuser_id = u.id\n" +
                "\t\tAND g.type = 7\n" +
                "\t\tAND `status` = 0\n" +
                "\t\tAND (pay_type = 0 OR pay_type = 1)\n" +
                "\t\tAND DATE_FORMAT(o.create_time, '%Y-%m-%d') > '2020-03-19'\n" +
                "\t) AS '购买课堂金额',\n" +
                "\t(\n" +
                "\t\tSELECT\n" +
                "\t\t\tIFNULL(SUM(buy_price) / 100, 0)\n" +
                "\t\tFROM\n" +
                "\t\t\tt_order o\n" +
                "\t\tWHERE\n" +
                "\t\t\tuser_id = u.id\n" +
                "\t\tAND `status` = 0\n" +
                "\t\tAND (pay_type = 0 OR pay_type = 1)\n" +
                "\t\tAND DATE_FORMAT(o.create_time, '%Y-%m-%d') > '2020-03-19'\n" +
                "\t) AS '3月19日后消费金额'\n" +
                "FROM\n" +
                "\tt_user u\n" +
                "WHERE\n" +
                "\tu.tel IN (" + tel + ")";
        return querySql;
    }

    public static class UserBuyInfo{
        private String tel;
        private String register;
        private String buyVip;
        private String buyVipMoney;
        private String buySubject;
        private String buySubjectMoney;
        private String buySSubject;
        private String buySSubjectMoney;
        private String buyVip1;
        private String buyVipMoney1;
        private String buySSubject1;
        private String buySSubjectMoney1;
        private String buySum;

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getRegister() {
            return register;
        }

        public void setRegister(String register) {
            this.register = register;
        }

        public String getBuyVip() {
            return buyVip;
        }

        public void setBuyVip(String buyVip) {
            this.buyVip = buyVip;
        }

        public String getBuyVipMoney() {
            return buyVipMoney;
        }

        public void setBuyVipMoney(String buyVipMoney) {
            this.buyVipMoney = buyVipMoney;
        }

        public String getBuySubject() {
            return buySubject;
        }

        public void setBuySubject(String buySubject) {
            this.buySubject = buySubject;
        }

        public String getBuySubjectMoney() {
            return buySubjectMoney;
        }

        public void setBuySubjectMoney(String buySubjectMoney) {
            this.buySubjectMoney = buySubjectMoney;
        }

        public String getBuySSubject() {
            return buySSubject;
        }

        public void setBuySSubject(String buySSubject) {
            this.buySSubject = buySSubject;
        }

        public String getBuySSubjectMoney() {
            return buySSubjectMoney;
        }

        public void setBuySSubjectMoney(String buySSubjectMoney) {
            this.buySSubjectMoney = buySSubjectMoney;
        }

        public String getBuyVip1() {
            return buyVip1;
        }

        public void setBuyVip1(String buyVip1) {
            this.buyVip1 = buyVip1;
        }

        public String getBuyVipMoney1() {
            return buyVipMoney1;
        }

        public void setBuyVipMoney1(String buyVipMoney1) {
            this.buyVipMoney1 = buyVipMoney1;
        }

        public String getBuySSubject1() {
            return buySSubject1;
        }

        public void setBuySSubject1(String buySSubject1) {
            this.buySSubject1 = buySSubject1;
        }

        public String getBuySSubjectMoney1() {
            return buySSubjectMoney1;
        }

        public void setBuySSubjectMoney1(String buySSubjectMoney1) {
            this.buySSubjectMoney1 = buySSubjectMoney1;
        }

        public String getBuySum() {
            return buySum;
        }

        public void setBuySum(String buySum) {
            this.buySum = buySum;
        }
    }
}
