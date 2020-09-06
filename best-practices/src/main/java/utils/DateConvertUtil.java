//package utils;
//
//import cn.hutool.core.date.DateTime;
//import cn.hutool.core.date.DateUtil;
//import com.hypersmart.base.exception.BaseException;
//import com.hypersmart.base.util.BeanUtils;
//import org.springframework.http.HttpStatus;
//
//import java.text.SimpleDateFormat;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.Date;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
///**
// * @author wei.xiang
// * @email wei.xiang@einyun.com
// * @date 2020/7/20 11:33
// * @Description:
// */
//public class DateConvertUtil {
//
//    public static String getTransTime(String timeStr) {
//        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        if (isTimeLegal(timeStr)){
//            DateTimeFormatter inputFormat2= DateTimeFormatter.ofPattern("yyyy/M/d HH:mm:ss");
//            return LocalDateTime.parse(timeStr, inputFormat2).format(outputFormat);
//        }
//        DateTimeFormatter inputFormat1 = DateTimeFormatter.ofPattern("yyyy-M-d HH:mm:ss");
//        return LocalDateTime.parse(timeStr, inputFormat1).format(outputFormat);
//    }
//
//    /**
//     * 判断输入的字符串是否满足时间格式 ： yyyy-MM-dd HH:mm:ss
//     * @param patternString 需要验证的字符串
//     * @return 合法返回 true ; 不合法返回false
//     */
//    public static boolean isTimeLegal(String patternString) {
//               Pattern a=Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))[/\\s]?((((0?[13578])|(1[02]))[/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[/\\s]?((((0?[13578])|(1[02]))[/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s((([0-1][0-9])|(2?[0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
//        //        Pattern a=Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s((([0-1][0-9])|(2?[0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
//        Matcher b=a.matcher(patternString);
//        if(b.matches()) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    ////工具类调试
////    public static void main(String[] args)  {
////        String timeStr = "2020/7/24 21:40:50";
////        String transTime = getTransTime(timeStr);
////        System.out.println(transTime);
////    }
//
//    public static String getTimeDuration(String start,String end) {
//        if (BeanUtils.isEmpty(end) || BeanUtils.isEmpty(start)){
//            return "";
//        }
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date now = DateUtil.parse(end,df);//当前时间
//        Date date =  DateUtil.parse(start,df);//过去
//        return getTimeDuration(now,date);
//    }
//
//    public static String getTimeDuration(Date now,Date date) {
//        if (BeanUtils.isEmpty(now) || BeanUtils.isEmpty(date)){
//            return "";
//        }
//        long l = now.getTime() - date.getTime();
//        long day = l / (24 * 60 * 60 * 1000);
//        long hour = (l / (60 * 60 * 1000) - day * 24);
//        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
//        long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
//        return "" + day + "天" + hour + "小时" + min + "分" + s + "秒";
//    }
//
//    public static String getDayAndMinute(Date date){
//        if (date == null){
//            return "";
//        }
//        String format = DateUtil.format(date, "yyyy-MM-dd HH:mm:ss");
//       return format.substring(10,16);
//    }
//
//    public static String getDayAndMinute(String date){
//        if (BeanUtils.isEmpty(date) || date.length()<17) {
//            return "";
//        }
//        Pattern a=Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s((([0-1][0-9])|(2?[0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
//        Matcher b=a.matcher(date);
//        if(b.matches()) {
//            return date.substring(10,16);
//        } else {
//            return "";
//        }
//    }
//
//        public static Date dateToTime(String date){
//        if (BeanUtils.isEmpty(date)){
//            throw new BaseException(HttpStatus.BAD_REQUEST,HttpStatus.BAD_REQUEST.value(),"date错误");
//        }
//        Pattern a=Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))?$");
//        Matcher b=a.matcher(date);
//        if(b.matches()) {
//            return new Date(DateUtil.parse(date+" 00:00:00","yyyy-MM-dd HH:mm:ss").getTime()) ;
//        } else {
//            return null;
//        }
//    }
//
//    public static String timeToDate(String date){
//        Pattern a=Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s((([0-1][0-9])|(2?[0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
//        Matcher b=a.matcher(date);
//        if(b.matches()) {
//            return date.substring(0,10);
//        } else {
//            return date;
//        }
//    }
//
//    public static String timeToDate(Date date){
//        String format = DateUtil.format(date, "yyyy-MM-dd HH:mm:ss");
//        Pattern a=Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s((([0-1][0-9])|(2?[0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
//        Matcher b=a.matcher(format);
//        if(b.matches()) {
//            return format.substring(0,10);
//        } else {
//            return format;
//        }
//    }
//
//    public static void main(String[] args) {
//        Date date = DateConvertUtil.dateToTime("2019-12-11");
//        System.out.println(date);
//
//    }
//}
