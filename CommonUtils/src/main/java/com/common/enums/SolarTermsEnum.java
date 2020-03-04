/**
 * 
 */
package com.common.enums;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 二十四节气枚举
 * @author hangwu.lin@ttxn.com
 *
 */
public enum SolarTermsEnum
{
    /**节气：立春, 开始日期：02月03日, 结束日期：02月17日*/
    LICHUN("1-LICHUN", "02-03 00:00:00", "02-17 23:59:59", 0, 0),
    /**节气：雨水, 开始日期：02月18日, 结束日期：03月04日*/
    YUSHUI("2-YUSHUI", "02-18 00:00:00", "03-04 23:59:59", 0, 0),
    /**节气：惊蛰, 开始日期：03月05日, 结束日期：03月19日*/
    JINGZHE("3-JINGZHE", "03-05 00:00:00", "03-19 23:59:59", 0, 0),
    /**节气：春分,  开始日期：03月20日,  结束日期：04月03日*/
    CHUNFEN("4-CHUNFEN", "03-20 00:00:00", "04-03 23:59:59", 0, 0),
    /**节气：清明,  开始日期：04月04日,  结束日期：04月18日*/
    QINGMING("5-QINGMING", "04-04 00:00:00", "04-18 23:59:59", 0, 0),
    /**节气：谷雨,  开始日期：04月19日,  结束日期：05月04日*/
    GUYU("6-GUYU", "04-19 00:00:00", "05-04 23:59:59", 0, 0),
    /**节气：立夏,  开始日期：05月05日,  结束日期：05月19日*/
    LIXIA("7-LIXIA", "05-05 00:00:00", "05-19 23:59:59", 0, 0),
    /**节气：小满,  开始日期：05月20日,  结束日期：06月04日*/
    XIAOMAN("8-XIAOMAN", "05-20 00:00:00", "06-04 23:59:59", 0, 0),
    /**节气：芒种,  开始日期：06月05日,  结束日期：06月20日*/
    MANGZHONG("9-MANGZHONG", "06-05 00:00:00", "06-20 23:59:59", 0, 0),
    /**节气：夏至,  开始日期：06月21日,  结束日期：07月05日*/
    XIAZHI("10-XIAZHI", "06-21 00:00:00", "07-05 23:59:59", 0, 0),
    /**节气：小暑,  开始日期：07月06日,  结束日期：07月21日*/
    XIAOSHU("11-XIAOSHU", "07-06 00:00:00", "07-21 23:59:59", 0, 0),
    /**节气：大暑,  开始日期：07月22日,  结束日期：08月06日*/
    DASHU("12-DASHU", "07-22 00:00:00", "08-06 23:59:59", 0, 0),
    /**节气：立秋,  开始日期：08月07日,  结束日期：08月21日*/
    LIQIU("13-LIQIU", "08-07 00:00:00", "08-21 23:59:59", 0, 0),
    /**节气：处暑,  开始日期：08月22日,  结束日期：09月06日*/
    CHUSHU("14-CHUSHU", "08-22 00:00:00", "09-06 23:59:59", 0, 0),
    /**节气：白露,  开始日期：09月07日,  结束日期：09月21日*/
    BAILU("15-BAILU", "09-07 00:00:00", "09-21 23:59:59", 0, 0),
    /**节气：秋分,  开始日期：09月22日,  结束日期：10月07日*/
    QIUFEN("16-QIUFEN", "09-22 00:00:00", "10-07 23:59:59", 0, 0),
    /**节气：寒露,  开始日期：10月08日,  结束日期：10月22日*/
    HANLU("17-HANLU", "10-08 00:00:00", "10-22 23:59:59", 0, 0),
    /**节气：霜降,  开始日期：10月23日,  结束日期：11月06日*/
    SHUANGJIANG("18-SHUANGJIANG", "10-23 00:00:00", "11-06 23:59:59", 0, 0),
    /**节气：立冬,  开始日期：11月07日,  结束日期：11月21日*/
    LIDONG("19-LIDONG", "11-07 00:00:00", "11-21 23:59:59", 0, 0),
    /**节气：小雪,  开始日期：11月22日,  结束日期：12月06日*/
    XIAOXUE("20-XIAOXUE", "11-22 00:00:00", "12-06 23:59:59", 0, 0),
    /**节气：大雪,  开始日期：12月06日,  结束日期：12月20日*/
    DAXUE("21-DAXUE", "12-06 00:00:00", "12-20 23:59:59", 0, 0),
    /**节气：冬至,  开始日期：12月21日,  结束日期：01月04日*/
    DONGZHI("22-DONGZHI", "12-21 00:00:00", "12-31 23:59:59", 0, 0),
    DONGZHI_1("22-DONGZHI", "01-01 00:00:00", "01-04 23:59:59", 0, 0),
    /**节气：小寒,  开始日期：01月05日,  结束日期：01月19日*/
    XIAOHAN("23-XIAOHAN", "01-05 00:00:00", "01-19 23:59:59", 0, 0),
    /**节气：大寒,  开始日期：01月20日,  结束日期：02月02日*/
    DAHAN("24-DAHAN", "01-20 00:00:00", "02-02 23:59:59", 0, 0);

    static {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    DateStyle.MM_DD_HH_MM_SS.getValue());

            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);

            for (SolarTermsEnum solarTerm : SolarTermsEnum.values()) {
                String startTimeTxt = solarTerm.getStartTimeTxt();
                String endTimeTxt = solarTerm.getEndTimeTxt();

                solarTerm.setStartTime(dateFormat.parse(startTimeTxt).getTime());
                solarTerm.setEndTime(dateFormat.parse(endTimeTxt).getTime());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 图片地址
     */
    private String url;

    /**
     * 开始日期
     */
    private String startTimeTxt;

    /**
     * 结束日期
     */
    private String endTimeTxt;

    /**
     * 开始时间
     */
    private long startTime;

    /**
     * 结束时间
     */
    private long endTime;


    private SolarTermsEnum(String url, String startTimeTxt,String endTimeTxt,long startTime,long endTime) {
        this.url = url;
        this.startTimeTxt = startTimeTxt;
        this.endTimeTxt = endTimeTxt;
        this.startTime = startTime;
        this.endTime = endTime;
    }


    public String getUrl()
    {
        return url;
    }


    public void setUrl(String url)
    {
        this.url = url;
    }


    public String getStartTimeTxt()
    {
        return startTimeTxt;
    }


    public void setStartTimeTxt(String startTimeTxt)
    {
        this.startTimeTxt = startTimeTxt;
    }


    public String getEndTimeTxt()
    {
        return endTimeTxt;
    }


    public void setEndTimeTxt(String endTimeTxt)
    {
        this.endTimeTxt = endTimeTxt;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }}

