package com.common.enums;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class SolarTermsTest
{
    
    
    public static void main(String[] args) throws ParseException
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DateStyle.MM_DD_HH_MM_SS.getValue());

        Date nowDate = new Date();
        String time = dateFormat.format(nowDate);
        //long timeL = dateFormat.parse(time).getTime();
        long timeL = dateFormat.parse("01-02 16:18:00").getTime();
        for (SolarTermsEnum solarTerm : SolarTermsEnum.values()) {
            //System.out.println(solarTerm.name()+" StartTime: "+solarTerm.getStartTime()+"  EndTime:"+solarTerm.getEndTime()+" time:"+timeL);
            if(solarTerm.getStartTime() <= timeL && solarTerm.getEndTime() >= timeL){
                System.out.println(solarTerm);
            }
        }
        
        System.out.println(dateFormat.parse("01-02 16:18:00").getTime());
        System.out.println(dateFormat.parse("01-04 23:59:59").getTime());

        Integer i = new Integer(2);
        Integer a = new Integer(1);
        Integer b = new Integer(2);
        System.out.println(i.compareTo(a));
        System.out.println(i.compareTo(b));
    }

}
