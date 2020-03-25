package com.bishop.common.utils;

import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * @author wei.xiang
 * @email wei.xiang@einyun.com
 * @date 2019/11/7 10:45
 * @Description:
 */
public class StringUtil {

    public StringUtil() {
    }

    public static String convertPointToSpace(String str) {
        String space = "";
        if (StringUtils.isEmpty(str)) {
            return space;
        } else {
            String[] path = str.split("\\.");

            for(int i = 0; i < path.length - 1; ++i) {
                space = space + "&nbsp;&emsp;";
            }

            return space;
        }
    }

    public static String InputStreamToString(InputStream is) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];

        int length;
        while((length = is.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }

        String str = result.toString(StandardCharsets.UTF_8.name());
        result.close();
        return str;
    }

    public static String convertArrayToString(String[] arr) {
        return convertArrayToString(arr, ",");
    }

    public static String convertArrayToString(String[] arr, String split) {
        if (arr != null && arr.length != 0) {
            StringBuffer sb = new StringBuffer();

            for(int i = 0; i < arr.length; ++i) {
                sb.append(arr[i]);
                sb.append(split);
            }

            return sb.substring(0, sb.length() - split.length());
        } else {
            return "";
        }
    }

    public static String convertCollectionAsString(Collection<String> strs) {
        return convertCollectionAsString(strs, ",");
    }

    public static String convertCollectionAsString(Collection<String> strs, String split) {
        if (strs != null && !strs.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            Iterator it = strs.iterator();

            while(it.hasNext()) {
                sb.append(((String)it.next()).toString());
                sb.append(split);
            }

            return sb.substring(0, sb.length() - split.length());
        } else {
            return "";
        }
    }

    public static String convertToChineseNumeral(double amount) {
        char[] hunit = new char[]{'拾', '佰', '仟'};
        char[] vunit = new char[]{'万', '亿'};
        char[] digit = new char[]{'零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'};
        long midVal = (long)(amount * 100.0D);
        String valStr = String.valueOf(midVal);
        String head = valStr.substring(0, valStr.length() - 2);
        String rail = valStr.substring(valStr.length() - 2);
        String prefix = "";
        String suffix = "";
        if (rail.equals("00")) {
            suffix = "整";
        } else {
            suffix = digit[rail.charAt(0) - 48] + "角" + digit[rail.charAt(1) - 48] + "分";
        }

        char[] chDig = head.toCharArray();
        char zero = '0';
        byte zeroSerNum = 0;

        for(int i = 0; i < chDig.length; ++i) {
            int idx = (chDig.length - i - 1) % 4;
            int vidx = (chDig.length - i - 1) / 4;
            if (chDig[i] == '0') {
                ++zeroSerNum;
                if (zero == '0') {
                    zero = digit[0];
                } else if (idx == 0 && vidx > 0 && zeroSerNum < 4) {
                    prefix = prefix + vunit[vidx - 1];
                    zero = '0';
                }
            } else {
                zeroSerNum = 0;
                if (zero != '0') {
                    prefix = prefix + zero;
                    zero = '0';
                }

                prefix = prefix + digit[chDig[i] - 48];
                if (idx > 0) {
                    prefix = prefix + hunit[idx - 1];
                }

                if (idx == 0 && vidx > 0) {
                    prefix = prefix + vunit[vidx - 1];
                }
            }
        }

        if (prefix.length() > 0) {
            prefix = prefix + '圆';
        }

        return prefix + suffix;
    }

    public static String convertCharEntityToHtml(String content) {
        content = content.replace("&apos;", "'").replace("&quot;", "\"").replace("&gt;", ">").replace("&lt;", "<").replace("&amp;", "&");
        int start = 0;
        StringBuilder buffer = new StringBuilder();

        while(start > -1) {
            int system = 10;
            if (start == 0) {
                int t = content.indexOf("&#");
                if (start != t) {
                    start = t;
                }

                if (start > 0) {
                    buffer.append(content.substring(0, start));
                }
            }

            int end = content.indexOf(";", start + 2);
            String charStr = "";
            char letter;
            if (end != -1) {
                charStr = content.substring(start + 2, end);
                letter = charStr.charAt(0);
                if (letter == 'x' || letter == 'X') {
                    system = 16;
                    charStr = charStr.substring(1);
                }
            }

            try {
                letter = (char)Integer.parseInt(charStr, system);
                buffer.append((new Character(letter)).toString());
            } catch (NumberFormatException var7) {
                var7.printStackTrace();
            }

            start = content.indexOf("&#", end);
            if (start - end > 1) {
                buffer.append(content.substring(end + 1, start));
            }

            if (start == -1) {
                int length = content.length();
                if (end + 1 != length) {
                    buffer.append(content.substring(end + 1, length));
                }
            }
        }

        return buffer.toString();
    }

    public static String convertHtmlToCharEntity(String content) {
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < content.length(); ++i) {
            char c = content.charAt(i);
            switch(c) {
                case '\n':
                    sb.append(c);
                    break;
                case '"':
                    sb.append("&quot;");
                    break;
                case '&':
                    sb.append("&amp;");
                    break;
                case '\'':
                    sb.append("&apos;");
                    break;
                case '<':
                    sb.append("&lt;");
                    break;
                case '>':
                    sb.append("&gt;");
                    break;
                default:
                    if (c >= ' ' && c <= '~') {
                        sb.append(c);
                    } else {
                        sb.append("&#x");
                        sb.append(Integer.toString(c, 16));
                        sb.append(';');
                    }
            }
        }

        return sb.toString();
    }

    public static String format(String message, Object... args) {
        for(int i = 0; i < args.length; ++i) {
            message = message.replace("${" + i + "}", args[i].toString());
        }

        return message;
    }

    public static String format(String message, Map<String, Object> params) {
        String result = message;
        if (params != null && !params.isEmpty()) {
            Iterator keyIts = params.keySet().iterator();

            while(keyIts.hasNext()) {
                String key = (String)keyIts.next();
                Object value = params.get(key);
                if (value != null) {
                    result = result.replace("${" + key + "}", value.toString());
                }
            }

            return result;
        } else {
            return message;
        }
    }

    public static StringBuilder format(CharSequence msgWithFormat, boolean autoQuote, Object... args) {
        int argsLen = args.length;
        boolean markFound = false;
        StringBuilder sb = new StringBuilder(msgWithFormat);
        if (argsLen > 0) {
            for(int i = 0; i < argsLen; ++i) {
                String flag = "%" + (i + 1);

                for(int idx = sb.indexOf(flag); idx >= 0; idx = sb.indexOf(flag)) {
                    markFound = true;
                    sb.replace(idx, idx + 2, toString(args[i], autoQuote));
                }
            }

            if (args[argsLen - 1] instanceof Throwable) {
                StringWriter sw = new StringWriter();
                ((Throwable)args[argsLen - 1]).printStackTrace(new PrintWriter(sw));
                sb.append("\n").append(sw.toString());
            } else if (argsLen == 1 && !markFound) {
                sb.append(args[argsLen - 1].toString());
            }
        }

        return sb;
    }

    public static boolean isExist(String content, String beginStr, String endStr) {
        boolean isExist = true;
        String lowContent = content.toLowerCase();
        String lowBeginStr = beginStr.toLowerCase();
        String lowEndStr = endStr.toLowerCase();
        int beginIndex = lowContent.indexOf(lowBeginStr);
        int endIndex = lowContent.indexOf(lowEndStr);
        return beginIndex != -1 && endIndex != -1 && beginIndex < endIndex;
    }

    public static String trimPrefix(String content, String prefix) {
        String resultStr;
        for(resultStr = content; resultStr.startsWith(prefix); resultStr = resultStr.substring(prefix.length())) {
        }

        return resultStr;
    }

    public static String trimSuffix(String content, String suffix) {
        String resultStr;
        for(resultStr = content; resultStr.endsWith(suffix); resultStr = resultStr.substring(0, resultStr.length() - suffix.length())) {
        }

        return resultStr;
    }

    public static String trim(String content, String trimStr) {
        return trimSuffix(trimPrefix(content, trimStr), trimStr);
    }

    public static String upperFirst(String str) {
        return toFirst(str, true);
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isEmpty(String str) {
        if (str == null) {
            return true;
        } else {
            return str.trim().equals("");
        }
    }

    public static boolean isEmpty(Long value) {
        if (value == null) {
            return true;
        } else {
            return value == 0L;
        }
    }

    public static boolean isZeroEmpty(String tmp) {
        boolean isEmpty = isEmpty(tmp);
        return isEmpty ? true : "0".equals(tmp);
    }

    public static boolean isNotZeroEmpty(String tmp) {
        return !isZeroEmpty(tmp);
    }

    public static String lowerFirst(String str) {
        return toFirst(str, false);
    }

    public static String toFirst(String str, boolean isUpper) {
        if (StringUtils.isEmpty(str)) {
            return "";
        } else {
            char first = str.charAt(0);
            String firstChar = new String(new char[]{first});
            firstChar = isUpper ? firstChar.toUpperCase() : firstChar.toLowerCase();
            return firstChar + str.substring(1);
        }
    }

    public static String replaceVariable(String content, String replace) {
        return replaceVariable(content, replace, "\\{(.*?)\\}");
    }

    public static String replaceVariable(String content, String replace, String regular) {
        Pattern regex = Pattern.compile(regular);
        String result = content;

        for(Matcher regexMatcher = regex.matcher(content); regexMatcher.find(); regexMatcher = regex.matcher(result)) {
            String toReplace = regexMatcher.group(0);
            result = result.replace(toReplace, replace);
        }

        return result;
    }

    public static String replaceVariableMap(String content, Map<String, Object> map) throws Exception {
        return replaceVariableMap(content, map, "\\{(.*?)\\}");
    }

    public static String replaceVariableMap(String template, Map<String, Object> map, String regular) throws Exception {
        Pattern regex = Pattern.compile(regular);

        String toReplace;
        String value;
        for(Matcher regexMatcher = regex.matcher(template); regexMatcher.find(); template = template.replace(toReplace, value)) {
            String key = regexMatcher.group(1);
            toReplace = regexMatcher.group(0);
            value = (String)map.get(key);
            if (value == null) {
                throw new Exception("没有找到[" + key + "]对应的变量值，请检查表变量配置!");
            }
        }

        return template;
    }

    public static String removeSpecial(String str) throws PatternSyntaxException {
        return removeByRegEx(str, "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\"]");
    }

    public static String removeByRegEx(String str, String regEx) throws PatternSyntaxException {
        Pattern p = Pattern.compile("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\"]");
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    public static byte[] stringToBytes(String str) {
        byte[] digest = new byte[str.length() / 2];

        for(int i = 0; i < digest.length; ++i) {
            String byteString = str.substring(2 * i, 2 * i + 2);
            int byteValue = Integer.parseInt(byteString, 16);
            digest[i] = (byte)byteValue;
        }

        return digest;
    }

    public static String bytesToString(byte[] b) {
        StringBuffer hexString = new StringBuffer();

        for(int i = 0; i < b.length; ++i) {
            String plainText = Integer.toHexString(255 & b[i]);
            if (plainText.length() < 2) {
                plainText = "0" + plainText;
            }

            hexString.append(plainText);
        }

        return hexString.toString();
    }

    public static String toString(Object obj, boolean autoQuote) {
        StringBuilder sb = new StringBuilder();
        if (obj == null) {
            sb.append("NULL");
        } else if (obj instanceof Object[]) {
            for(int i = 0; i < ((Object[])((Object[])obj)).length; ++i) {
                sb.append(((Object[])((Object[])((Object[])obj)))[i]).append(", ");
            }

            if (sb.length() > 0) {
                sb.delete(sb.length() - 2, sb.length());
            }
        } else {
            sb.append(obj.toString());
        }

        if (autoQuote && sb.length() > 0 && (sb.charAt(0) != '[' || sb.charAt(sb.length() - 1) != ']') && (sb.charAt(0) != '{' || sb.charAt(sb.length() - 1) != '}')) {
            sb.insert(0, "[").append("]");
        }

        return sb.toString();
    }

    public static String encodingString(String str, String from, String to) {
        String result;
        try {
            result = new String(str.getBytes(from), to);
        } catch (Exception var5) {
            result = str;
        }

        return result;
    }

    public static String substringAfterLast(String str, String separator) {
        return StringUtils.substringAfterLast(str, separator);
    }

    public static String substringBeforeLast(String str, String separator) {
        return StringUtils.substringBeforeLast(str, separator);
    }

    public static String trimSufffix(String toTrim, String trimStr) {
        while(toTrim.endsWith(trimStr)) {
            toTrim = toTrim.substring(0, toTrim.length() - trimStr.length());
        }

        return toTrim;
    }

    public static String convertDbFieldToField(String dbField) {
        return convertDbFieldToField(dbField, "_", true);
    }

    public static String convertDbFieldToField(String dbField, String symbol, boolean isIgnoreFirst) {
        String result = "";
        if (dbField.startsWith(symbol)) {
            dbField = dbField.substring(1);
        }

        if (dbField.endsWith(symbol)) {
            dbField = dbField.substring(0, dbField.length() - 1);
        }

        String[] arr = dbField.split(symbol);

        for(int i = 0; i < arr.length; ++i) {
            String str = arr[i];
            if (isIgnoreFirst && i != 0) {
                char oldChar = str.charAt(0);
                char newChar = (oldChar + "").toUpperCase().charAt(0);
                str = newChar + str.substring(1);
            }

            result = result + str;
        }

        return result;
    }

    public static String join(String[] vals, String separator) {
        if (vals != null && vals.length != 0) {
            String val = "";

            for(int i = 0; i < vals.length; ++i) {
                if (i == 0) {
                    val = val + vals[i];
                } else {
                    val = val + separator + vals[i];
                }
            }

            return val;
        } else {
            return "";
        }
    }

    public static String join(List<String> vals, String separator) {
        if (BeanUtils.isEmpty(vals)) {
            return "";
        } else {
            String val = "";

            for(int i = 0; i < vals.size(); ++i) {
                if (i == 0) {
                    val = val + (String)vals.get(i);
                } else {
                    val = val + separator + (String)vals.get(i);
                }
            }

            return val;
        }
    }

    public static Object parserObject(Object obj, String type) {
        if (obj == null) {
            return null;
        } else {
            Object val = obj;

            try {
                String str = obj.toString();
                if (type.equalsIgnoreCase("string")) {
                    val = str;
                } else if (type.equalsIgnoreCase("int")) {
                    val = Integer.parseInt(str);
                } else if (type.equalsIgnoreCase("float")) {
                    val = Float.parseFloat(str);
                } else if (type.equalsIgnoreCase("double")) {
                    val = Double.parseDouble(str);
                } else if (type.equalsIgnoreCase("byte")) {
                    val = Byte.parseByte(str);
                } else if (type.equalsIgnoreCase("short")) {
                    val = Short.parseShort(str);
                } else if (type.equalsIgnoreCase("long")) {
                    val = Long.parseLong(str);
                } else if (type.equalsIgnoreCase("boolean")) {
                    if (StringUtils.isNumeric(str)) {
                        Boolean var5 = Integer.parseInt(str) == 1;
                    }

                    val = Boolean.parseBoolean(str);
                } else if (type.equalsIgnoreCase("date")) {
                    val = DateFormatUtil.parse(str);
                } else {
                    val = str;
                }
            } catch (Exception var4) {
            }

            return val;
        }
    }

    public static String[] getStringAryByStr(String str) {
        if (isEmpty(str)) {
            return null;
        } else {
            String[] aryId = str.split(",");
            String[] lAryId = new String[aryId.length];

            for(int i = 0; i < aryId.length; ++i) {
                lAryId[i] = aryId[i];
            }

            return lAryId;
        }
    }

    public static Long[] getLongAryByStr(String str) {
        if (isEmpty(str)) {
            return null;
        } else {
            str = str.replace("[", "");
            str = str.replace("]", "");
            String[] aryId = str.split(",");
            Long[] lAryId = new Long[aryId.length];

            for(int i = 0; i < aryId.length; ++i) {
                lAryId[i] = Long.parseLong(aryId[i]);
            }

            return lAryId;
        }
    }

    public static Object getMapValue(String param) {
        Map map = new HashMap();
        String str = "";
        String key = "";
        Object value = "";
        char[] charList = param.toCharArray();
        boolean valueBegin = false;

        for(int i = 0; i < charList.length; ++i) {
            char c = charList[i];
            if (c == '{') {
                if (valueBegin) {
                    value = getMapValue(param.substring(i, param.length()));
                    i = param.indexOf(125, i) + 1;
                    map.put(key, value);
                }
            } else if (c == '=') {
                valueBegin = true;
                key = str;
                str = "";
            } else if (c == ',') {
                valueBegin = false;
                value = str;
                str = "";
                map.put(key, value);
            } else {
                if (c == '}') {
                    if (str != "") {
                        value = str;
                    }

                    map.put(key, value);
                    return map;
                }

                if (c != ' ') {
                    str = str + c;
                }
            }
        }

        return map;
    }

    public static String convertToVarcharFormat(String strWithoutQuote) {
        if (strWithoutQuote != null && !"".equals(strWithoutQuote)) {
            String[] productNoArray = strWithoutQuote.split(",");
            StringBuffer newStrWithQuoteStringBuffer = new StringBuffer();
            String[] var3 = productNoArray;
            int var4 = productNoArray.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                String str = var3[var5];
                newStrWithQuoteStringBuffer.append("'").append(str).append("',");
            }

            String strWithQuote = newStrWithQuoteStringBuffer.toString();
            strWithQuote = strWithQuote.substring(0, strWithQuote.length() - 1);
            return strWithQuote;
        } else {
            return "";
        }
    }
}
