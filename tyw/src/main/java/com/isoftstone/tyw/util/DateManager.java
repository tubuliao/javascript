package com.isoftstone.tyw.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;

import org.apache.commons.lang.StringUtils;

/**
 * @author liuyulong
 */
public class DateManager {

    /**
     * 将日期转换为string
     * 
     * @param date
     * @return
     */
    public static String dateFormat(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    /**
     * 计算两个日期之间的差异天数
     * 
     * @param startDate
     * @param endDate
     * @return
     */
    public static int getDays(String startDate, String endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int days = 0;
        try {
            Date date_start = sdf.parse(startDate);
            Date date_end = sdf.parse(endDate);
            Calendar cal_start = Calendar.getInstance();
            Calendar cal_end = Calendar.getInstance();
            cal_start.setTime(date_start);
            cal_end.setTime(date_end);
            days = DateManager.getDaysBetween(cal_start, cal_end);
            return days;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return days;
    }

    /**
     * 将日期转换为string 20111123
     * 
     * @param date
     * @return
     */

    public static String dateFormates(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(date);
    }

    // 精确到秒
    public static String dateFormatesSend(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        return sdf.format(date);
    }

    /**
     * 将日期使时间转换为string
     * 
     * @param date
     * @return
     */
    public static String dateFormatTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public static String dateFormatTimes(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(date);
    }

    // 毫秒数转为日期
    public static String ssToDate(Long ss) {
        Date dat = new Date(ss);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(dat);
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sb = format.format(gc.getTime());
        return sb;

    }

    /**
     * 将string转换为日期
     * 
     * @param date yyyy-MM-dd
     * @return
     * @throws ParseException
     */
    public static Date stringFormat(String date) {
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return format.parse(date.trim());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将string转换为日期
     * 
     * @param date yyyy/MM/dd
     * @return
     * @throws ParseException
     */
    public static String stringfanFormat(String date) {
        if (StringUtils.isNotEmpty(date)) {
            String datef = date.replaceAll("/", "-");

            Date dates = stringFormat(datef);
            return dateFormatTime(dates);
        } else {
            return null;
        }

    }

    public static Date stringFormats(String date) {
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            return format.parse(date.trim());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将string转换为日期
     * 
     * @param date yyyy-MM-dd
     * @return
     * @throws ParseException
     */
    public static Date stringFormatTime(String date) {
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return format.parse(date.trim());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 返回前多少天和后多少天日期
     * 
     * @param days 天数，5前5天日期，-5后五天日期
     * @param format 返回日期字符格式
     * @return
     */
    public static String DateBefAft() {
        SimpleDateFormat ssd = new SimpleDateFormat("yyyy-MM-dd");
        Calendar curr = Calendar.getInstance();
        curr.set(Calendar.DAY_OF_MONTH, curr.get(Calendar.DAY_OF_MONTH) - 5);
        Date date = curr.getTime();
        return ssd.format(date);
    }

    public static String getBackDate(int i) {
        SimpleDateFormat ssd = new SimpleDateFormat("yyyy-MM-dd");
        Calendar curr = Calendar.getInstance();
        curr.set(Calendar.DAY_OF_MONTH, curr.get(Calendar.DAY_OF_MONTH) - i);
        Date date = curr.getTime();
        return ssd.format(date);
    }

    public static String getBackDates(int i) {
        SimpleDateFormat ssd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar curr = Calendar.getInstance();
        curr.set(Calendar.DAY_OF_MONTH, curr.get(Calendar.DAY_OF_MONTH) + i);
        Date date = curr.getTime();
        return ssd.format(date);
    }

    public static String getBackDateshour(int i) {
        SimpleDateFormat ssd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar curr = Calendar.getInstance();
        curr.set(Calendar.HOUR, curr.get(Calendar.HOUR) + i);
        Date date = curr.getTime();
        return ssd.format(date);
    }

    /**
     * 输入一个日期和天数，得到输入日期加上输入的天数以后的日期
     * 
     * @param str 输入的日期 格式为 yyyy-MM-dd
     * @param day 天数 如：10
     * @return
     */
    public static String DateBef(String str, Integer day) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d = sdf.parse(str);
            Calendar cd = Calendar.getInstance();
            cd.setTime(d);
            cd.add(Calendar.DAY_OF_YEAR, day);
            int year = cd.get(cd.YEAR);
            int month = cd.get(cd.MONTH) + 1;
            int day_of_month = cd.get(cd.DAY_OF_MONTH);
            int day_of_week = cd.get(cd.DAY_OF_WEEK) - 1;
            if (day_of_week == 0) {
                day_of_week += 7;
            }
            int hh = cd.get(cd.HOUR_OF_DAY);
            int mm = cd.get(cd.MINUTE);
            int ss = cd.get(cd.SECOND);
            return year + "-" + month + "-" + day_of_month + " " + hh + ":" + mm + ":" + ss;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 输入一个日期和小时，得到输入小时加上输入的天数以后的日期
     * 
     * @param str 输入的日期 格式为 yyyy-MM-dd
     * @param day 天数 如：10
     * @return
     */
    public static String DateAfterHours(String str, Integer hours) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = sdf.parse(str);
             Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.add(calendar.HOUR,hours);//把日期往后增加一天.整数往后推,负数往前移动
            date=calendar.getTime(); //这个时间就是日期往后推一天的结果        
             String dateString = sdf.format(date);
            return dateString;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
   
    public static String getHour(String day, int i) {
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = format.parse(day);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.HOUR_OF_DAY, i);
            // c.add(Calendar.MINUTE, 40);

            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getTime());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getDay(String day, int i) {
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = format.parse(day);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.DAY_OF_YEAR, i);
            // c.add(Calendar.MINUTE, 40);

            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getTime());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getDays(String day) {
        try {
            Date date = new Date();
            String today = date.toLocaleString();
            long startT = DateManager.fromDateStringToLong(today);
            long endT = DateManager.fromDateStringToLong(day);
            long ss = (endT - startT) / (1000); // 共计秒数
            int MM = (int) ss / 60; // 共计分钟数
            int hh = (int) ss / 3600; // 共计小时数
            int dd = (int) hh / 24; // 共计天数
            return dd;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 当前时间的
    public static int getfenzhong() {
        try {
            Date date = new Date();
            String today = date.toLocaleString();
            long startT = DateManager.fromDateStringToLong(today);
            long ss = startT / (1000); // 共计秒数
            int MM = (int) ss / 60; // 共计分钟数
            int hh = (int) ss / 3600; // 共计小时数
            int dd = (int) hh / 24; // 共计天数
            return MM;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getorderfenzhong(String day) {
        try {

            long endT = DateManager.fromDateStringToLong(day);
            long ss = endT / (1000); // 共计秒数
            int MM = (int) ss / 60; // 共计分钟数
            int hh = (int) ss / 3600; // 共计小时数
            int dd = (int) hh / 24; // 共计天数
            return MM;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long fromDateStringToLong(String inVal) { // 此方法计算时间毫秒
        Date date = null; // 定义时间类型
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = inputFormat.parse(inVal); // 将字符型转换成日期型
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date.getTime(); // 返回毫秒数
    }
  
    
    public static String afterHour(int inVal) { // 计算多少小时以后的时间，inVal 从2开始
        String oneHoursAfterTime = "";
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR, Calendar.HOUR + inVal);// 设置时间为当前时间-8小时
        oneHoursAfterTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime());
        return oneHoursAfterTime;
    }

    public static int getDaysBetween(java.util.Calendar d1, java.util.Calendar d2) {
        if (d1.after(d2)) { // swap dates so that d1 is start and d2 is end
            java.util.Calendar swap = d1;
            d1 = d2;
            d2 = swap;
        }
        int days = d2.get(java.util.Calendar.DAY_OF_YEAR) - d1.get(java.util.Calendar.DAY_OF_YEAR);
        int y2 = d2.get(java.util.Calendar.YEAR);
        if (d1.get(java.util.Calendar.YEAR) != y2) {
            d1 = (java.util.Calendar) d1.clone();
            do {
                days += d1.getActualMaximum(java.util.Calendar.DAY_OF_YEAR);
                d1.add(java.util.Calendar.YEAR, 1);
            } while (d1.get(java.util.Calendar.YEAR) != y2);
        }
        return days;
    }
}
