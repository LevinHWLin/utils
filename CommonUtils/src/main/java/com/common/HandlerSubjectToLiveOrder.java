package com.common;

import java.io.*;
import java.util.*;

public class HandlerSubjectToLiveOrder {

    private static final String ENCODING_UTF_8 = "UTF-8";

    public static void main(String[] args) {
        System.out.println();


        try {
            Map<String, String> subjectAndLiveMaps = getDataInfo(new File("F:\\客情数据\\课程订单转直播订单\\直播与课程关系.txt"));
            Map<String, String> subjectGoodMaps = getDataInfo(new File("F:\\客情数据\\课程订单转直播订单\\课程商品id.txt"));
            Map<String, String> liveGoodMaps = getDataInfo(new File("F:\\客情数据\\课程订单转直播订单\\直播回顾商品id.txt"));

            System.out.println("课程与直播数量：" + subjectAndLiveMaps.size());
            System.out.println("课程商品id数量：" + subjectGoodMaps.size());
            System.out.println("直播回顾商品id数量：" + liveGoodMaps.size());

            List<String> orderDataList = getOrderDataInfo(new File("F:\\客情数据\\课程订单转直播订单\\处理过的课程订单.txt"));
            System.out.println("课程订单数量：" + orderDataList.size());

            Set<String> orderIdSet = new HashSet<>();
            for (Map.Entry<String, String> entry : subjectAndLiveMaps.entrySet()) {
                String subjectName = entry.getValue();
                String liveName = entry.getKey();


                String subjectGoodId = subjectGoodMaps.get(subjectName);
                String liveGoodId = liveGoodMaps.get(liveName);
                //System.out.println(liveName + "-直播商品ID：" + liveGoodId + "    " + subjectName + "-课程商品ID：" + subjectGoodId);
                if(subjectGoodId != null ){
                    for (String order : orderDataList) {
                        if (order.indexOf(subjectGoodId) > 0){
                            String orderId = CommonUtil.getUuid();
                            orderIdSet.add(orderId);
                            System.out.println(order.replace(subjectGoodId, liveGoodId).replace("12310c10730311ea8ca27cd30aeb7792",orderId));
                        }
                    }
                }
            }
            System.out.println("新增订单ID：\r\n"+orderIdSet.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, String> getDataInfo(File file) throws IOException {
        Map<String, String> dataMaps = new HashMap<>();
        if (file.isFile() && file.exists()) { // 判断文件是否存在
            InputStreamReader read = new InputStreamReader(new FileInputStream(file), ENCODING_UTF_8);// 考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            StringBuffer buffer = new StringBuffer();
            String lineTxt = null;

            while ((lineTxt = bufferedReader.readLine()) != null) {
                String[] lineTxts = lineTxt.split("##");
                dataMaps.put(lineTxts[0], lineTxts[1]);
            }

            read.close();
        } else {
            System.out.println("找不到指定的文件");
        }

        return dataMaps;
    }

    public static List<String> getOrderDataInfo(File file) throws IOException {
        List<String> dataList = new ArrayList<>();
        if (file.isFile() && file.exists()) { // 判断文件是否存在
            InputStreamReader read = new InputStreamReader(new FileInputStream(file), ENCODING_UTF_8);// 考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            StringBuffer buffer = new StringBuffer();
            String lineTxt = null;

            while ((lineTxt = bufferedReader.readLine()) != null) {
                dataList.add(lineTxt);
            }
            read.close();
        } else {
            System.out.println("找不到指定的文件");
        }
        return dataList;
    }
}
