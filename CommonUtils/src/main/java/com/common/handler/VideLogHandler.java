package com.common.handler;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VideLogHandler {

    public static void main(String[] args) throws IOException {

        File xlsFile = new File("C:\\Users\\Administrator\\Desktop\\视频\\视频点播异常请求记录1.xlsx");

        // 获得工作簿
        XSSFWorkbook workbook1 = new XSSFWorkbook(new FileInputStream(xlsFile));

        String filePath = "C:\\Users\\Administrator\\Desktop\\视频\\log";
        File file = new File(filePath);
        File[] tempList = file.listFiles();
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        for(File f : tempList){
            if(f.getName().contains(".gz")){
                continue;
            }

            Map<String,Object> resultMap = handlerText(f);
            list.add(resultMap);
        }

        // 获得工作表
        XSSFSheet sheet1 = workbook1.getSheetAt(0);
        XSSFRow sheetRow = sheet1.createRow(0);
        XSSFCell cell = sheetRow.createCell(0);
        cell.setCellValue("日志文件");
        cell = sheetRow.createCell(1);
        cell.setCellValue("请求ip");
        cell = sheetRow.createCell(2);
        cell.setCellValue("请求次数");
        cell = sheetRow.createCell(3);
        cell.setCellValue("请求Code");
        cell = sheetRow.createCell(4);
        cell.setCellValue("请求资源id");
        cell = sheetRow.createCell(5);
        cell.setCellValue("请求来源");
        cell = sheetRow.createCell(6);
        cell.setCellValue("请求链接");

        int rowNum = 1;
        for(Map<String,Object> map : list){

            String reqestTime = (String) map.get("时间段");
            Map<String,Integer> requestMap = (Map<String, Integer>) map.get("请求内容");

            for (Map.Entry<String, Integer> entry : requestMap.entrySet()) {

                int count = entry.getValue();
                if(count > 20){
                    XSSFRow row = sheet1.createRow(rowNum);
                    XSSFCell cell1 =row.createCell(0);
                    cell1.setCellValue(reqestTime);

                    String[] keys = entry.getKey().split("###");

                    cell1 =row.createCell(1);
                    cell1.setCellValue(keys[0]);

                    cell1 =row.createCell(2);
                    cell1.setCellValue(entry.getValue());

                    cell1 =row.createCell(6);
                    cell1 =row.createCell(6);
                    String httpLink = keys[1];
                    cell1.setCellValue(httpLink);

                    cell1 =row.createCell(4);
                    String videoId = httpLink.replace("http://video.ixuenong.com/","");
                    videoId = videoId.substring(0,videoId.indexOf("/"));
                    cell1.setCellValue(videoId);

                    if(keys.length > 2){
                        cell1 =row.createCell(5);
                        cell1.setCellValue(keys[2]);

                        cell1 =row.createCell(3);
                        cell1.setCellValue(keys[3]);
                    }
                    rowNum = rowNum + 1;

                    System.out.println("第"+rowNum+"行");
                }
            }
        }

        FileOutputStream os = new FileOutputStream(xlsFile);
        workbook1.write(os);
        workbook1.close();
        os.close();

//        String filePath = "C:\\Users\\Administrator\\Desktop\\视频\\log";
//        File tempFile = new File(filePath + "\\video.ixuenong.com_2019_11_25_010000_020000");
//        handlerText1(tempFile);
    }

    public static Map<String, Object> handlerText(File file) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String encoding = "UTF-8";

            String dataPath = "C:\\Users\\Administrator\\Desktop\\视频\\log.txt";
            String generPath = "C:\\Users\\Administrator\\Desktop\\symphony_article_oId.sql";

            Map<String, Integer> requestSize = new HashMap<String, Integer>();
            //File file = new File(dataPath);
            if (file.isFile() && file.exists()) { // 判断文件是否存在
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                StringBuffer buffer = new StringBuffer();
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    //String[] lineTxts = lineTxt.split("#");
                    //System.out.println(lineTxts.length+"   "+lineTxt);
                    //if (lineTxt.contains(" 403 ")) {

                        String ipText = lineTxt.substring(lineTxt.indexOf("]") + 1, lineTxt.indexOf(" - ")).trim();

                        String httpText = lineTxt.substring(lineTxt.indexOf("GET") + 4, lineTxt.length());
                        httpText = httpText.substring(0, httpText.indexOf("\""));

                        String httpCode = lineTxt.substring(lineTxt.indexOf(httpText), lineTxt.length());
                        httpCode = httpCode.substring(httpCode.indexOf("\" ") + 2, httpCode.length());
                        httpCode = httpCode.substring(0, httpCode.indexOf(" "));

                        String httpLink = lineTxt.substring(lineTxt.lastIndexOf(httpCode) + 4, lineTxt.length());

                        buffer.append(httpCode).append("\n");

                        String key = ipText + "###" + httpText + "###" + httpLink + "###" + httpCode;
                        if (requestSize.containsKey(key)) {
                            int count = requestSize.get(key);
                            requestSize.put(key, count + 1);
                        } else {
                            requestSize.put(key, 1);
                        }
                    //}
                }

                // System.out.println(buffer.toString());
//                System.out.println("时间段："+file.getName());
//                for (Map.Entry<String, Integer> entry : requestSize.entrySet()) {
//                    int count = entry.getValue();
//                    if(count > 20){
//                        String[] keys = entry.getKey().split("###");
//
//                        System.out.println( "请求ip："+keys[0] +"  请求次数：" + entry.getValue() +  " 请求资源链接："+keys[1]);
//                        if(keys.length > 2){
//                            System.out.println(" 请求来源："+keys[2]);
//                        }
//                        System.out.println("");
//                    }
//                }


                resultMap.put("时间段", file.getName());
                resultMap.put("请求内容", requestSize);

                read.close();
            } else {
                System.out.println("找不到指定的文件");
            }
            return resultMap;

        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
            return resultMap;
        }
    }

    public static Map<String, Object> handlerText1(File file) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String encoding = "UTF-8";

            String dataPath = "C:\\Users\\Administrator\\Desktop\\视频\\log.txt";
            String generPath = "C:\\Users\\Administrator\\Desktop\\symphony_article_oId.sql";

            Map<String, Integer> requestSize = new HashMap<String, Integer>();
            //File file = new File(dataPath);
            if (file.isFile() && file.exists()) { // 判断文件是否存在
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                StringBuffer buffer = new StringBuffer();
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    //String[] lineTxts = lineTxt.split("#");
                    //System.out.println(lineTxts.length+"   "+lineTxt);
//                    if(lineTxt.contains(" 403 ")){

                    String ipText = lineTxt.substring(lineTxt.indexOf("]") + 1, lineTxt.indexOf(" - ")).trim();

                    String httpText = lineTxt.substring(lineTxt.indexOf("GET") + 4, lineTxt.length());
                    httpText = httpText.substring(0, httpText.indexOf("\""));

                    String httpLink = lineTxt.substring(lineTxt.lastIndexOf(" \"-\" ") + 4, lineTxt.length());

                    String httpCode = lineTxt.substring(lineTxt.indexOf(httpText), lineTxt.length());
                    httpCode = httpCode.substring(httpCode.indexOf("\" ") + 2, httpCode.length());
                    httpCode = httpCode.substring(0, httpCode.indexOf(" "));

                    buffer.append(httpCode).append("\n");

                    String key = ipText + "###" + httpText + "###" + httpLink + "###" + httpCode;
                    if (requestSize.containsKey(key)) {
                        int count = requestSize.get(key);
                        requestSize.put(key, count + 1);
                    } else {
                        requestSize.put(key, 1);
                    }
//                    }
                }

                System.out.println(buffer.toString());
//                System.out.println("时间段："+file.getName());
//                for (Map.Entry<String, Integer> entry : requestSize.entrySet()) {
//                    int count = entry.getValue();
//                    if(count > 20){
//                        String[] keys = entry.getKey().split("###");
//
//                        System.out.println( "请求ip："+keys[0] +"  请求次数：" + entry.getValue() +  " 请求资源链接："+keys[1]);
//                        if(keys.length > 2){
//                            System.out.println(" 请求来源："+keys[2]);
//                        }
//                        System.out.println("");
//                    }
//                }


                resultMap.put("时间段", file.getName());
                resultMap.put("请求内容", requestSize);

                read.close();
            } else {
                System.out.println("找不到指定的文件");
            }
            return resultMap;

        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
            return resultMap;
        }
    }
}
