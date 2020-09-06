package configuration;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
/**
 * @author wei.xiang
 * @email wei.xiang@einyun.com
 * @date 2020/7/17 14:43
 * @Description:
 */

@Slf4j
public class DateConverter implements Converter<String, Date> {
    private static final List<String> formarts = Lists.newLinkedList();

    static {
        formarts.add("yyyy-MM");
        formarts.add("yyyy-MM-dd");
        formarts.add("yyyy-MM-dd HH:mm");
        formarts.add("yyyy-MM-dd HH:mm:ss");
    }

    @Override
    public Date convert(String source) {
        String value = source.trim();
        if ("".equals(value)) {
            return null;
        }
        if (source.matches("^\\d{4}-\\d{1,2}$")) {
            log.info("1 source:{}",source);
            return parseDate(source, formarts.get(0));
        } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")) {
            log.info("2 source:{}",source);
            return parseDate(source+" 00:00:00",formarts.get(3));
        } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}$")) {
            log.info("3 source:{}",source);
            return parseDate(source+":00", formarts.get(3));
        } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$")) {
            log.info("4 source:{}",source);
            return parseDate(source, formarts.get(3));
        } else if (isTimestamp(source)) {//long 时间戳转换
            log.info("5 source:{}",source);
            return parseDate(source, formarts.get(3));
        } else {
            throw new IllegalArgumentException("Invalid boolean value '" + source + "'");
        }
    }

    /**
     * 格式化日期
     *
     * @param dateStr String 字符型日期
     * @param format  String 格式
     * @return Date 日期
     */
    public Date parseDate(String dateStr, String format) {
        Date date = null;
        //long 时间戳转换
        if (isTimestamp(dateStr)) {
            long time = Long.parseLong(dateStr);
            date = new Date(time);
        }
        try {
            DateFormat dateFormat = new SimpleDateFormat(format);
            date = dateFormat.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    public static boolean isTimestamp(String str) {
        return str != null && str.length() == 13 && isNumeric(str);
    }

}
