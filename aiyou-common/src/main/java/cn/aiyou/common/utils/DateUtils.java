package cn.aiyou.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    //日期转换 成字符串
    public static String date2String(Date date, String  patt){
        SimpleDateFormat sdf=new SimpleDateFormat(patt);
        String format = sdf.format(date);
        return  format;
    }

    //字符串转换成日期
    public static Date string2Date(String string,String patt) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat(patt);
        Date parse = sdf.parse(string);
        return parse;
    }
}
