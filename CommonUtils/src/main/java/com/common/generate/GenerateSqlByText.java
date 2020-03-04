package com.common.generate;

import java.io.*;

public class GenerateSqlByText {

    public static void main(String[] args)
    {
        generateText();
    }

    public static void generateText(){
        try {
            String encoding = "UTF-8";

            String dataPath = "C:\\Users\\Administrator\\Desktop\\symphony_article_oId.txt";
            String generPath = "C:\\Users\\Administrator\\Desktop\\symphony_article_oId.sql";

            FileOutputStream fs = new FileOutputStream(new File(generPath));
            PrintStream p = new PrintStream(fs, true, encoding);

            File file = new File(dataPath);
            if (file.isFile() && file.exists()) { // 判断文件是否存在
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                StringBuffer buffer = new StringBuffer();
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    //String[] lineTxts = lineTxt.split("#");
                    //System.out.println(lineTxts.length+"   "+lineTxt);
                    buffer.append("'").append(lineTxt).append("',").append("\n");
                }
                p.println(buffer);
                read.close();

                p.close();
                fs.close();
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
    }

}
