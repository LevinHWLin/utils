package com.common;

import net.sf.json.JSONObject;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.compress.utils.Sets;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ParseExcelUtils {
    public static void main(String[] args){
        try{
            String filePath = "E:\\GIT-Project\\ttxn\\cloud-webservice\\src\\main\\webapp\\tempData\\tempActivityData.xlsx";
            File xlsFile = new File(filePath);

            // 获得工作簿
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(xlsFile));
            // 获得工作表
            XSSFSheet sheet =  workbook.getSheet("直播课");
            if(null == sheet ){
                System.out.println("数据不存在");
            }

            //int rows = sheet.getPhysicalNumberOfRows();
            int rows = 10;
            int classIndex = 0;
            int classCodeIndex = 1;
            int idIndex = 2;
            int nameIndex = 3;
            int teacherIndex = 4;
            int teacherTitleIndex = 5;
            int liveStartIndex = 6;
            int liveEndIndex = 7;
            int liveLinkIndex = 8;
            int headImgIndex = 9;

            Set<String> classSets = Sets.newHashSet();
            List<JSONObject> dataList = Lists.newArrayList();
            for (int row = 0; row < rows; row++ ) {
                // 获取第i行数据
                XSSFRow sheetRow = sheet.getRow(row);
                if (null == sheetRow) {
                    break;
                }

                XSSFCell classCell = sheetRow.getCell(classIndex);
                XSSFCell classCodeCell = sheetRow.getCell(classCodeIndex);
                String className = classCell.getStringCellValue();
                if("作物分类".equals(className)){
                    continue;
                }
                String classCode = classCodeCell.getStringCellValue();
                classSets.add(className);

                XSSFCell idCell = sheetRow.getCell(idIndex);
                XSSFCell nameCell = sheetRow.getCell(nameIndex);
                XSSFCell teacherNameCell = sheetRow.getCell(teacherIndex);
                XSSFCell teacherTitleCell = sheetRow.getCell(teacherTitleIndex);
                XSSFCell liveStartTimeCell = sheetRow.getCell(liveStartIndex);
                XSSFCell liveEndTimeCell = sheetRow.getCell(liveEndIndex);
                XSSFCell liveLinkCell = sheetRow.getCell(liveLinkIndex);
                XSSFCell headImgCell = sheetRow.getCell(headImgIndex);

                JSONObject data = new JSONObject();
                data.put("id", idCell.getStringCellValue());
                data.put("name", nameCell.getStringCellValue());
                data.put("teacher_name", teacherNameCell.getStringCellValue());
                data.put("teacher_title", teacherTitleCell.getStringCellValue());
                data.put("live_start_time", liveStartTimeCell.getStringCellValue());
                data.put("live_end_time", liveEndTimeCell.getStringCellValue());
                data.put("live_link", liveLinkCell.getStringCellValue());
                data.put("head_img", headImgCell.getStringCellValue());
                data.put("class", className);
                dataList.add(data);
            }
            workbook.close();

            // 按时间进行排序
            Collections.sort(dataList, new Comparator<JSONObject>() {
                @Override
                public int compare(JSONObject o1, JSONObject o2) {
                    String liveStartTime1 = o1.getString("live_start_time");
                    String liveStartTime2 = o2.getString("live_start_time");
                    return compareDate(liveStartTime1, liveStartTime2, "yyyy-MM-dd HH:mm:ss");
                }
            });


//            System.out.println("分类：");
//            System.out.println(classSets.toString());
//
//            System.out.println("直播课列表：");
//            System.out.println(dataList.toString());
//
//            System.out.println("指定直播课");
            for(JSONObject data : dataList){
                System.out.println(data.toString());
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static int compareDate(String date1, String date2, String pattern) {
        DateFormat df = new SimpleDateFormat(pattern);
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }
}
