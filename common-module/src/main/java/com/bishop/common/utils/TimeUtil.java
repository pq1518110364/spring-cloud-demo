package com.bishop.common.utils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * @author wei.xiang
 * @email wei.xiang@einyun.com
 * @date 2019/11/7 10:53
 * @Description:
 */
public class TimeUtil {

    public static final int SECOND = 0;
    public static final int MINUTE = 1;
    public static final int HOUR = 2;
    public static final int DAY = 3;
    public static final int MONTH = 4;

    public TimeUtil() {
    }

    public static LocalDateTime convertString(String value, String format) {
        DateTimeFormatter sdf = DateTimeFormatter.ofPattern(format);
        if (value == null) {
            return null;
        } else {
            try {
                return LocalDateTime.parse(value, sdf);
            } catch (Exception var4) {
                return null;
            }
        }
    }

    public static LocalDateTime convertString(String value) {
        return convertString(value, "yyyy-MM-dd HH:mm:ss");
    }

    public static String getCurrentTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }

    public static String getDateTimeString(long millseconds) {
        return getDate(millseconds, "yyyy-MM-dd HH:mm:ss");
    }

    public static String getDayDate(long time) {
        return getDate(time, "yyyyMMdd");
    }

    public static String getDate(long time, String format) {
        SimpleDateFormat formater = new SimpleDateFormat(format);
        return formater.format(new Date(time));
    }

    public static String getDateTimeString(LocalDateTime date) {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static String getDateTimeString(LocalDateTime date, String format) {
        return date.format(DateTimeFormatter.ofPattern(format));
    }

    public static boolean isTimeLarge(String startTime, String endTime) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            ParsePosition pos = new ParsePosition(0);
            ParsePosition pos2 = new ParsePosition(0);
            Date dt1 = formatter.parse(startTime, pos);
            Date dt2 = formatter.parse(endTime, pos2);
            long lDiff = dt2.getTime() - dt1.getTime();
            return lDiff > 0L;
        } catch (Exception var9) {
            return false;
        }
    }

    public static long getTime(LocalDateTime endTime, LocalDateTime startTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant sinstant = startTime.atZone(zone).toInstant();
        Instant einstant = endTime.atZone(zone).toInstant();
        return einstant.toEpochMilli() - sinstant.toEpochMilli();
    }

    public static String getTimeDiff(String startTime, String endTime) {
        try {
            String tmp = "";
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            ParsePosition pos = new ParsePosition(0);
            ParsePosition pos2 = new ParsePosition(0);
            Date dt1 = formatter.parse(startTime, pos);
            Date dt2 = formatter.parse(endTime, pos2);
            long lDiff = dt2.getTime() - dt1.getTime();
            int days = (int)(lDiff / 86400000L);
            if (days > 0) {
                lDiff -= (long)(days * 1000 * 60 * 60 * 24);
                tmp = tmp + days + "天";
            }

            int hours = (int)(lDiff / 3600000L);
            if (hours > 0) {
                lDiff -= (long)(hours * 1000 * 60 * 60);
            }

            tmp = tmp + hours + "小时";
            int minute = (int)(lDiff / 60000L);
            tmp = tmp + minute + "分钟";
            return tmp;
        } catch (Exception var13) {
            var13.printStackTrace();
            return "-1";
        }
    }

    public static String getTime(Long millseconds) {
        String time = "";
        if (millseconds == null) {
            return "";
        } else {
            Long tmp =millseconds/1000/60/60/24;
            int days = tmp.intValue();
            if (days > 0) {
                time = time + days + "天";
            }

            long hourMillseconds = millseconds - (long)(days * 1000 * 60 * 60 * 24);
            int hours = (int)hourMillseconds / 1000 / 60 / 60;
            if (hours > 0) {
                time = time + hours + "小时";
            }

            long minuteMillseconds = millseconds - (long)(days * 1000 * 60 * 60 * 24) - (long)(hours * 1000 * 60 * 60);
            int minutes = (int)minuteMillseconds / 1000 / 60;
            if (minutes > 0) {
                time = time + minutes + "分钟";
            }

            return time;
        }
    }

    public static String getDateString(LocalDateTime date) {
        return date != null ? date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : "";
    }

    public static String getDateString(LocalDateTime date, String format) {
        return date != null ? date.format(DateTimeFormatter.ofPattern(format)) : "";
    }

    public static String getCurrentDate(String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.now().format(formatter);
    }

    public static String getCurrentDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDateTime.now().format(formatter);
    }

    public static String getDateString(long millseconds) {
        Instant instant = Instant.ofEpochMilli(millseconds);
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static String getDateString(String formater) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static long getMillsByToday() {
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nowDate = LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 0, 0, 0);
        Instant instant = nowDate.atZone(zone).toInstant();
        return instant.toEpochMilli();
    }

    public static long getNextDays(int days) {
        LocalDateTime nextDate = LocalDateTime.now().plusDays((long)days);
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = nextDate.atZone(zone).toInstant();
        return instant.toEpochMilli();
    }

    public static LocalDateTime getNextDays(LocalDateTime date, int days) {
        return date.plusDays((long)days);
    }

    public static long getMillsByDateString(String strDate) {
        Calendar cal = Calendar.getInstance();
        if (strDate != null && strDate.trim().length() > 9) {
            strDate = strDate.trim();

            try {
                int year = Integer.parseInt(strDate.substring(0, 4));
                int month = Integer.parseInt(strDate.substring(5, 7)) - 1;
                int date = Integer.parseInt(strDate.substring(8, 10));
                cal.set(year, month, date, 0, 0, 0);
                String str = String.valueOf(cal.getTimeInMillis());
                return Long.parseLong(str.substring(0, str.length() - 3) + "000");
            } catch (Exception var6) {
            }
        }

        return 0L;
    }

    public static long getMillsByDateTimeString(String strDateTime) {
        Calendar cal = Calendar.getInstance();
        if (strDateTime != null && strDateTime.trim().length() > 18) {
            strDateTime = strDateTime.trim();

            try {
                int year = Integer.parseInt(strDateTime.substring(0, 4));
                int month = Integer.parseInt(strDateTime.substring(5, 7)) - 1;
                int date = Integer.parseInt(strDateTime.substring(8, 10));
                int hour = Integer.parseInt(strDateTime.substring(11, 13));
                int minute = Integer.parseInt(strDateTime.substring(14, 16));
                int second = Integer.parseInt(strDateTime.substring(17, 19));
                cal.set(year, month, date, hour, minute, second);
                return cal.getTimeInMillis();
            } catch (Exception var8) {
            }
        }

        return 0L;
    }

    public static String getFormatString(long millseconds, String format) {
        if (format == null || format.trim().length() == 0) {
            format = "yyyy-MM-dd";
        }

        format = format.trim();
        return (new SimpleDateFormat(format)).format(new Date(millseconds));
    }

    public static long getCurrentTimeMillis() {
        Calendar c = Calendar.getInstance();
        return c.getTimeInMillis();
    }

    public static long getTimeMillis(LocalDateTime date) {
        if (BeanUtils.isEmpty(date)) {
            return 0L;
        } else {
            ZoneId zone = ZoneId.systemDefault();
            Instant instant = date.atZone(zone).toInstant();
            return instant.toEpochMilli();
        }
    }

    public static LocalDateTime getLocalDateTimeByMills(long mills) throws Exception {
        try {
            if (mills == 0L) {
                return null;
            } else {
                Instant instant = Instant.ofEpochMilli(mills);
                ZoneId zone = ZoneId.systemDefault();
                return LocalDateTime.ofInstant(instant, zone);
            }
        } catch (Exception var4) {
            return null;
        }
    }

    public static String getTimeByMills(long mills) throws Exception {
        try {
            if (mills == 0L) {
                return "-";
            } else {
                Date date = null;
                Calendar ca = Calendar.getInstance();
                ca.setTimeInMillis(mills);
                date = ca.getTime();
                SimpleDateFormat myformat;
                if (ca.get(11) == 0 && ca.get(12) == 0 && ca.get(13) == 0) {
                    myformat = new SimpleDateFormat("yyyy-MM-dd");
                } else {
                    myformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                }

                return myformat.format(date);
            }
        } catch (Exception var5) {
            return "-";
        }
    }

    public static String formatDate(long mills) throws Exception {
        try {
            if (mills == 0L) {
                return "-";
            } else {
                Date date = null;
                Calendar ca = Calendar.getInstance();
                ca.setTimeInMillis(mills);
                date = ca.getTime();
                SimpleDateFormat myformat = new SimpleDateFormat("yyyy-MM-dd");
                return myformat.format(date);
            }
        } catch (Exception var5) {
            return "-";
        }
    }

    public static String formatTime(long mills) throws Exception {
        try {
            if (mills == 0L) {
                return "-";
            } else {
                Instant instant = Instant.ofEpochMilli(mills);
                ZoneId zone = ZoneId.systemDefault();
                LocalDateTime date = LocalDateTime.ofInstant(instant, zone);
                return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            }
        } catch (Exception var5) {
            return "-";
        }
    }

    public static long getMillsByTime(String strTime) throws Exception {
        try {
            if (strTime.length() != 19 && strTime.length() != 10) {
                throw new Exception("the time string is wrong.");
            } else {
                int year = Integer.parseInt(strTime.substring(0, 4));
                int month = Integer.parseInt(strTime.substring(5, 7)) - 1;
                int day = Integer.parseInt(strTime.substring(8, 10));
                if (year >= 1000 && year <= 3000) {
                    if (month >= 0 && month <= 12) {
                        if (day >= 1 && day <= 31) {
                            Calendar ca = Calendar.getInstance();
                            if (strTime.length() == 19) {
                                int hour = Integer.parseInt(strTime.substring(11, 13));
                                int minute = Integer.parseInt(strTime.substring(14, 16));
                                int second = Integer.parseInt(strTime.substring(17, 19));
                                if (hour < 0 || hour > 24) {
                                    throw new Exception("the hour is wrong.");
                                }

                                if (minute < 0 || minute > 60) {
                                    throw new Exception("the minute is wrong.");
                                }

                                if (second < 0 || second > 60) {
                                    throw new Exception("the second is wrong.");
                                }

                                ca.set(year, month, day, hour, minute, second);
                            } else if (strTime.length() == 10) {
                                ca.set(year, month, day, 0, 0, 0);
                            }

                            return ca.getTimeInMillis();
                        } else {
                            throw new Exception("the day is wrong");
                        }
                    } else {
                        throw new Exception("the month is wrong.");
                    }
                } else {
                    throw new Exception("the year is wrong.");
                }
            }
        } catch (Exception var8) {
            var8.printStackTrace();
            return -1L;
        }
    }

    public static long getNextTime(int timeUnit, int interval, long timeMill) {
        Calendar ca = Calendar.getInstance();
        ca.setTimeInMillis(timeMill);
        switch(timeUnit) {
            case 0:
                ca.add(13, interval);
                break;
            case 1:
                ca.add(12, interval);
                break;
            case 2:
                ca.add(10, interval);
                break;
            case 3:
                ca.add(5, interval);
                break;
            case 4:
                ca.add(2, interval);
                break;
            default:
                return 0L;
        }

        return ca.getTimeInMillis();
    }

    public static LocalDateTime getDateTimeByTimeString(String timeString) {
        LocalDateTime date = LocalDateTime.now();

        try {
            date = DateFormatUtil.parse(timeString, "yyyy-MM-dd HH:mm:ss");
        } catch (ParseException var3) {
            var3.printStackTrace();
        }

        return date;
    }

    public static LocalDateTime getDateByDateString(String timeString) {
        LocalDateTime date = LocalDateTime.now();

        try {
            date = DateFormatUtil.parse(timeString, "yyyy-MM-dd");
        } catch (ParseException var3) {
            var3.printStackTrace();
        }

        return date;
    }

    public static LocalDate getDate(int year, int month, int date) {
        return LocalDate.of(year, month, date);
    }

    public static LocalDateTime getDate(int year, int month, int date, int hourOfDay, int minute, int second) {
        return LocalDateTime.of(year, month, date, hourOfDay, minute, second);
    }

    public static int getSecondDiff(LocalDateTime endTime, LocalDateTime startTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant sinstant = startTime.atZone(zone).toInstant();
        Instant einstant = endTime.atZone(zone).toInstant();
        long start = sinstant.toEpochMilli();
        long end = einstant.toEpochMilli();
        return (int)((end - start) / 1000L);
    }

    public static int getDaysOfMonth(int year, int mon) {
        Calendar cal = Calendar.getInstance();
        cal.set(1, year);
        cal.set(2, mon - 1);
        return cal.getActualMaximum(5);
    }

    public static int getWeekDayOfMonth(int year, int mon) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, mon - 1, 1);
        return cal.get(7);
    }

    public static String getDurationTime(LocalDateTime time) {
        if (time == null) {
            return "";
        } else {
            Long millseconds = getTime(time, LocalDateTime.now());
            return getTime(millseconds);
        }
    }
}
