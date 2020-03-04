package com.common.polyv;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.Base64.Encoder;

public class ChannelOperationTest {



    public static void main(String[] args) throws UnsupportedEncodingException {
       System.out.println("外部授权："+externalAuth());
        System.out.println("独立授权："+independentAuth());
    }

    public static String independentAuth() throws UnsupportedEncodingException {
        long ts = System.currentTimeMillis();
        String userId = BaseConfig.getUuid();
        //userId = "20191226";
        String tsStr = Long.toString(ts);
        String sercetKey = "CfYfT1VXoX";
        // secretkey + userid + secretkey + ts
        String signSource = sercetKey.concat(userId).concat(sercetKey).concat(tsStr);
        System.out.println(signSource);
//        signSource = sercetKey.concat(" + ").concat(userId).concat(" + ").concat(sercetKey).concat(" + ").concat(tsStr);
//        System.out.println(signSource);
        BASE64Encoder encoder = new BASE64Encoder();
        String nickname = encoder.encode("张三".getBytes("UTF-8"));
        //nickname = "viewTest";
        String avatar = "http://livestatic.videocc.net/assets/wimages/missing_face.png";
        String sign = org.apache.commons.codec.digest.DigestUtils.md5Hex(signSource);
        return "https://live.polyv.cn/watch/443602?userid=" + userId + "&ts=" + tsStr + "&sign=" + sign + "&nickname=" + nickname + "&avatar=" + avatar;
        // https://live.polyv.cn/watch/125527?userid=6b3a43&ts=1498547407000&sign=dd9dc9e42ad7c0204398e925a4ee0f46&nickname=viewerTests&avatar=
    }

    public static String externalAuth(){
        long ts = System.currentTimeMillis();
        String userId = BaseConfig.getUuid();
        //userId = "20191226";
        String tsStr = Long.toString(ts);
        String sercetKey = "Wd9IH6aKbP";
        // secretkey + userid + secretkey + ts
        String signSource = sercetKey.concat(userId).concat(sercetKey).concat(tsStr);
        System.out.println(signSource);
//        signSource = sercetKey.concat(" + ").concat(userId).concat(" + ").concat(sercetKey).concat(" + ").concat(tsStr);
//        System.out.println(signSource);

        String sign = org.apache.commons.codec.digest.DigestUtils.md5Hex(signSource);
        return "https://live.polyv.cn/watch/443426?userid=" + userId + "&ts=" + tsStr + "&sign=" + sign;
        // https://live.polyv.cn/watch/125527?userid=6b3a43&ts=1498547407000&sign=dd9dc9e42ad7c0204398e925a4ee0f46
    }

    public static void channelsTest(){
        long ts = System.currentTimeMillis();
        // 创建参数表 （创建接口需要传递的所有参数表）
        Map<String, String> paramMap = new HashMap<String, String>();

        paramMap.put("page", "1");
        paramMap.put("pageSize", "20");
        paramMap.put("appId", BaseConfig.POLYV_APP_ID);
        paramMap.put("appId", BaseConfig.POLYV_APP_ID);
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
