package com.common;

import com.common.enums.DateUtil;

import java.util.Date;

public class Test
{

    public static void main(String[] args)
    {
        // TODO Auto-generated method stub
        System.out.println(new Date(1570865438000L));

        try{
            String v = null;
            if (v.equals("a")){
                System.out.println("hahah");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("成了");

        System.out.println(new Date(1575363559115L));
        System.out.println(new Date(1575446818523L));
        System.out.println(new Date(1575791780216L));

        Date date = DateUtil.StringToDate("2019-12-25 11:00:00");
        System.out.println(date.getTime());
        date = DateUtil.StringToDate("2020-01-15 00:00:00");
        System.out.println(date.getTime());
    }

}
