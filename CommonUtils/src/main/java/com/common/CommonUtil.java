package com.common;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Permissions;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 公共工具类
 * 
 * @see CommonUtil
 * @date 2018年5月18日
 * @author bodong.liu@ttxn.com
 */
public class CommonUtil
{
	
    private static DateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    private static Random random = new Random();

    /**
     * 获得uuid
     */
    public static String getUuid()
    {
        UUID uuid = UUID.randomUUID();
        String uuidStr = uuid.toString();
        uuidStr = uuidStr.replace("-", "");
        return uuidStr;
    }

    
}
