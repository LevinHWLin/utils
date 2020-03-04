package com.common.polyv;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HttpUtils {

    public static void generateSign() {

        long ts = System.currentTimeMillis();
        // 创建参数表 （创建接口需要传递的所有参数表）
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("appId", BaseConfig.POLYV_APP_ID);
        paramMap.put("timestamp", Long.toString(ts));

        //对参数名进行字典排序
        String[] keyArray = paramMap.keySet().toArray(new String[0]);
        Arrays.sort(keyArray);

        //拼接有序的参数串
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(BaseConfig.POLYV_APP_SECRET);
        for (String key : keyArray) {
            stringBuilder.append(key).append(paramMap.get(key));
        }

        stringBuilder.append(BaseConfig.POLYV_APP_SECRET);
        String signSource = stringBuilder.toString();

        String sign = org.apache.commons.codec.digest.DigestUtils.md5Hex(signSource).toUpperCase();
        System.out.println("http://api.live.polyv.net/v1/users/" + BaseConfig.POLYV_USER_ID + "/channels?appId=" + BaseConfig.POLYV_APP_ID + "&timestamp=" + ts + "&sign=" + sign);
    }

}
