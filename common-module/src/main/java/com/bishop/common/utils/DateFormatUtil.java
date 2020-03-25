package com.bishop.common.utils;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author wei.xiang
 * @email wei.xiang@einyun.com
 * @date 2019/11/7 10:46
 * @Description:
 */
public class DateFormatUtil {
    public static final DateTimeFormatter DATE_FORMAT_DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter DATE_FORMAT_DATETIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter DATE_FORMAT_DATETIME_NOSECOND = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static final DateTimeFormatter DATE_FORMAT_DATETIME_NOMINUTE = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");
    public static final DateTimeFormatter DATE_FORMAT_TIME = DateTimeFormatter.ofPattern("HH:mm:ss");
    public static final DateTimeFormatter DATE_FORMAT_TIME_NOSECOND = DateTimeFormatter.ofPattern("HH:mm");
    public static final DateTimeFormatter DATE_FORMAT_TIMESTAMP = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    private static final Log logger = LogFactory.getLog(DateFormatUtil.class);

    public DateFormatUtil() {
    }

    public static LocalDateTime parse(String dateString) throws ParseException {
        if (dateString.trim().indexOf(" ") > 0 && dateString.trim().indexOf(".") > 0) {
            return LocalDateTime.parse(dateString, DATE_FORMAT_TIMESTAMP);
        } else if (dateString.trim().indexOf(" ") > 0) {
            if (dateString.trim().indexOf(":") > 0) {
                return dateString.trim().indexOf(":") != dateString.trim().lastIndexOf(":") ? LocalDateTime.parse(dateString, DATE_FORMAT_DATETIME) : LocalDateTime.parse(dateString, DATE_FORMAT_DATETIME_NOSECOND);
            } else {
                return LocalDateTime.parse(dateString, DATE_FORMAT_DATETIME_NOMINUTE);
            }
        } else if (dateString.indexOf(":") > 0) {
            return dateString.trim().indexOf(":") != dateString.trim().lastIndexOf(":") ? LocalDateTime.parse(dateString, DATE_FORMAT_TIME) : LocalDateTime.parse(dateString, DATE_FORMAT_TIME_NOSECOND);
        } else {
            return LocalDateTime.parse(dateString, DATE_FORMAT_DATE);
        }
    }

    public static LocalDateTime parse(String dateString, String style) throws ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(style);
        if ("yyyy-MM-dd".equals(style)) {
            LocalDate date = LocalDate.parse(dateString, formatter);
            LocalDateTime localDateTime = LocalDateTime.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), 0, 0);
            return localDateTime;
        } else {
            return LocalDateTime.parse(dateString, formatter);
        }
    }

    public static LocalDate dateParse(String dateString, String style) throws ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(style);
        return LocalDate.parse(dateString, formatter);
    }

    public static LocalDateTime parse(String dateString, String... style) {
        LocalDateTime date = null;
        if (StringUtils.isEmpty(dateString)) {
            return date;
        } else {
            try {
                Date udate = DateUtils.parseDate(dateString, style);
                Instant instant = udate.toInstant();
                ZoneId zoneId = ZoneId.systemDefault();
                date = instant.atZone(zoneId).toLocalDateTime();
            } catch (Exception var6) {
                logger.error("Pase the Date(" + dateString + ") occur errors:" + var6.getMessage());
            }

            return date;
        }
    }

    public static LocalDateTime parse(Date udate) {
        LocalDateTime date = null;
        if (BeanUtils.isNotEmpty(udate)) {
            try {
                Instant instant = udate.toInstant();
                ZoneId zoneId = ZoneId.systemDefault();
                date = instant.atZone(zoneId).toLocalDateTime();
            } catch (Exception var6) {
                try {
                    String format = "yyyy-MM-dd HH:mm:ss";
                    String time = TimeUtil.getFormatString(udate.getTime(), format);
                    return parse(time, format);
                } catch (Exception var5) {
                    logger.error("Pase the Date(" + udate + ") occur errors:" + var6.getMessage());
                }
            }
        }

        return date;
    }

    public static Date parse(LocalDateTime date) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = date.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }

    public static String format(LocalDateTime date, String style) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(style);
        return date.format(dateFormat);
    }

    public static LocalDateTime parseDate(String dateString) throws ParseException {
        return LocalDateTime.parse(dateString, DATE_FORMAT_DATE);
    }

    public static String formatDate(LocalDateTime date) {
        return DATE_FORMAT_DATE.format(date);
    }

    public static LocalDateTime parseDateTime(String dateString) throws ParseException {
        return LocalDateTime.parse(dateString, DATE_FORMAT_DATETIME);
    }

    public static String formaDatetTime(LocalDateTime date) {
        return DATE_FORMAT_DATETIME.format(date);
    }

    public static String formatTimeNoSecond(LocalDateTime date) {
        return DATE_FORMAT_DATETIME_NOSECOND.format(date);
    }

    public static LocalDateTime parseTimeNoSecond(String dateString) throws ParseException {
        return LocalDateTime.parse(dateString, DATE_FORMAT_DATETIME_NOSECOND);
    }

    public static String format(long millisecond) {
        Instant instant = Instant.ofEpochMilli(millisecond);
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime date = LocalDateTime.ofInstant(instant, zone);
        return format(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String format(long millisecond, String style) {
        Instant instant = Instant.ofEpochMilli(millisecond);
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime date = LocalDateTime.ofInstant(instant, zone);
        return format(date, style);
    }

    public static String getNowByString(String style) {
        if (null == style || "".equals(style)) {
            style = "yyyy-MM-dd HH:mm:ss";
        }

        return format(LocalDateTime.now(), style);
    }

    public static String dateStringToString(String dateString, String style) throws ParseException {
        LocalDateTime newDate = parseDateTime(dateString);
        return format(newDate, "yyyyMMdd");
    }
}
