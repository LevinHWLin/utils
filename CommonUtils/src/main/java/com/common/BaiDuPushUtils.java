package com.common; /**
 * 
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

/**
 * 百度推送工具类
 * @author linhangwu
 *
 */
public class BaiDuPushUtils
{
    public static void main(String[] args) throws IOException, InterruptedException {
        String filePath = "C:\\Users\\Administrator\\Desktop\\sitemap_ask.txt";
        String encoding = "UTF-8";
        
        String url = "http://data.zz.baidu.com/urls?site=";//网站的服务器连接
        
        File file = new File(filePath);
        if (file.isFile() && file.exists()) { // 判断文件是否存在
            
            List<String> paramList = Lists.newArrayList();
            
            InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                paramList.add(lineTxt);
            }
            
            int paramSize = paramList.size();
            System.out.println("sumSize : "+paramSize);
            int defaultSize = 500;
            int count = (paramSize / defaultSize)+1;
            System.out.println("count : "+count);
            int fromIndex = 0;
            int toIndex = defaultSize;
            int nextSize = 0;
            
            List<List<String>> pushLists = new ArrayList<List<String>>();
            for(int i =0 ; i<count;i++) {
                System.out.println("fromIndex:"+fromIndex+" toIndex:"+toIndex);
                
                List<String> newList = paramList.subList(fromIndex, toIndex);
                nextSize = nextSize + newList.size();
                System.out.println("newList size :"+newList.size());
                
                if((paramSize-toIndex) >= defaultSize) {
                    fromIndex = toIndex;
                    toIndex = defaultSize + toIndex;
                }else {
                    fromIndex = toIndex;
                    toIndex = (paramSize-toIndex) + toIndex;
                }
                
                try {
                    String[] param = newList.toArray(new String[newList.size()]);
                    String json = Post(url, param);// 执行推送方法
                    System.out.println("结果是" + json); // 打印推送结果 
                    Thread.sleep(10000l);
                }catch(Exception ex) {
                    pushLists.add(newList);
                }
            }
            System.out.println("nextSize:"+nextSize);
            System.out.println("缺少："+(paramSize-nextSize));
            
            push(pushLists, url);
        }
        
        String[] param = {  
                "https://ask.ttxn.com/wenti/1571395584833.html"//需要推送的网址  
        };  
        //String json = Post(url, param);//执行推送方法  
        //System.out.println("结果是"+json);  //打印推送结果  
    }
    
    public static void push(List<List<String>> pushList,String url) {
        List<List<String>> tempList = new ArrayList<List<String>>();
        if(pushList.isEmpty()) {
            return;
        }
        for(List<String> list : pushList) {
            try {
                String[] param = list.toArray(new String[list.size()]);
                String json = Post(url, param);// 执行推送方法
                System.out.println("结果是" + json); // 打印推送结果 
                Thread.sleep(10000l);
            }catch(Exception ex) {
                tempList.add(list);
            }
        }
        
        push(tempList, url);
    }
      
    /** 
     * 百度链接实时推送 
     * @param PostUrl 
     * @param Parameters 
     * @return 
     */  
    public static String Post(String PostUrl,String[] Parameters){  
        if(null == PostUrl || null == Parameters || Parameters.length ==0){  
            return null;  
        }  
        String result="";  
        PrintWriter out=null;  
        BufferedReader in=null;  
        try {  
            //建立URL之间的连接  
            URLConnection conn=new URL(PostUrl).openConnection();  
            //设置通用的请求属性  
            conn.setRequestProperty("Host","data.zz.baidu.com");  
            conn.setRequestProperty("User-Agent", "curl/7.12.1");  
            conn.setRequestProperty("Content-Length", "83");  
            conn.setRequestProperty("Content-Type", "text/plain");  
               
            //发送POST请求必须设置如下两行  
            conn.setDoInput(true);  
            conn.setDoOutput(true);  
               
            //获取conn对应的输出流  
            out=new PrintWriter(conn.getOutputStream());  
            //发送请求参数  
            String param = "";  
            for(String s : Parameters){  
                param += s+"\n";  
            }  
            out.print(param.trim());  
            //进行输出流的缓冲  
            out.flush();  
            //通过BufferedReader输入流来读取Url的响应  
            in=new BufferedReader(new InputStreamReader(conn.getInputStream()));  
            String line;  
            while((line=in.readLine())!= null){  
                result += line;  
            }  
               
        } catch (Exception e) {  
            System.out.println("发送post请求出现异常！"+e);  
            e.printStackTrace();  
        } finally{  
            try{  
                if(out != null){  
                    out.close();  
                }  
                if(in!= null){  
                    in.close();  
                }  
                   
            }catch(IOException ex){  
                ex.printStackTrace();  
            }  
        }  
        return result;  
    }  
  
}  

