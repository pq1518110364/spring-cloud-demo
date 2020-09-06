package utils;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.bishop.common.utils.BeanUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * UUID 是由一组32位数的16进制数字所构成，以连字号分隔的五组来显示，形式为 8-4-4-4-12，总共有 36个字符（即三十二个英数字母和四个连字号）
 * @author wei.xiang
 * @email wei.xiang@einyun.com
 * @date 2020/7/9 16:44
 * @Description:
 */
@Slf4j
public class IDConvertUtil {
    //[0-9a-f]{8}(-[0-9a-f]{4}){3}-[0-9a-f]{12}
    public static final String pattern="\\w{8}(-\\w{4}){3}-\\w{12}";
    // 创建 Pattern 对象
    public static Pattern r = Pattern.compile(pattern);

    public static final String ADMIN_ID="00000000-0000-0000-0000-000000000000";

    public static final String defaultFillChar = "f";


    /**
     * 补齐函数  雪花算法的原则是生成64bit的唯一编码,2进制转10进制,18~19,long类型,全数字
     * @param orlStr   源字符串
     * @return java.lang.String
     * @author : wei.xiang@einyun.com
     * @date :  2020/7/9 17:07
     */
    public static String rJust(String orlStr) {
        log.info("补齐算法:{}",orlStr);
        if (BeanUtils.isEmpty(orlStr)) {
            log.warn("原字符串为null oldStr:{}", orlStr);
            return ADMIN_ID;
        }
        Matcher m = r.matcher(orlStr);
        if (m.find()){
            log.info("UUId无需转换:{}",orlStr);
            return orlStr;
        }
        if (!NumberUtil.isNumber(orlStr)){
            log.info("非数字类型无法转换:{}",orlStr);
            return orlStr;
        }
        if (orlStr.length() <= 0 || orlStr.length() > 36) {
            log.error("原字符串长度限制 oldStr:{}", orlStr);
            return orlStr;
        }
        StringBuilder stringBuilder = new StringBuilder();
        char[] chars = orlStr.toCharArray();
        int j=0;
        for (int i = 0; i <36; i++) {
            if (stringBuilder.length()>=36){
                break;
            }
            switch (i){
                case 8:
                case 13:
                case 18:
                case 23: stringBuilder.append("-");break;
                default:
                    if (j<chars.length){
                        if (Character.isDigit(chars[j])){
                            stringBuilder.append(chars[j]);
                        }else {
                            return orlStr;
                        }
                    }else {
                        stringBuilder.append(defaultFillChar);
                    }
                    j++;
            }
        }
        String newStr = stringBuilder.toString();
        log.info("newStr:{}",newStr);
        // 现在创建 matcher 对象
         m = r.matcher(newStr);
        if (m.find()) {
            log.info("Found value: {}" ,m.group(0));
            return m.group(0);
        } else {
            log.error("NO MATCH");
            return orlStr;
        }
    }

    private static StringBuilder getStringBuilder(int i1) {
        StringBuilder appendStr = new StringBuilder();
        if (i1 > 0) {
            for (int i = 0; i < i1; i++) {
                appendStr.append(defaultFillChar);
            }
        }
        return appendStr;
    }


    /**
     * 还原函数
     * @author : wei.xiang@einyun.com
     * @date :  2020/7/9 17:19
     *
     * @param oldStr 源字符串
     * @return java.lang.String
     */
    public static String reduction(String oldStr){
        log.info("还原算法:{}",oldStr);
        if (BeanUtils.isEmpty(oldStr)) {
            log.warn("原字符串为null oldStr:{}", oldStr);
            return oldStr;
        }
        if (ADMIN_ID.equals(oldStr)){
            log.info("无效字符 oldStr:{}", oldStr);
            return "";
        }
        if (!oldStr.contains(defaultFillChar)){
            log.info("不包含指定字符,无需转换 oldStr:{}", oldStr);
            return oldStr;
        }
        String s1 = oldStr.substring(oldStr.indexOf(defaultFillChar));
        if (s1.contains("-")){
            s1= s1.replace("-","");
        }
        if (StrUtil.containsOnly(s1,defaultFillChar.charAt(0))) {
            String s2 =oldStr.substring(0,oldStr.indexOf(defaultFillChar));
            if (s2.contains("-")){
                s2= s2.replace("-","");
            }
            boolean number = NumberUtil.isNumber(s2);
            if (number){
                log.info("还原成功 oldStr:{}", s2);
                return s2;
            }
        }
        log.info("无需还原 oldStr:{}", oldStr);
        return oldStr;
    }

    public static boolean isGUID(String str){
        if (BeanUtils.isNotEmpty(str)){
            Matcher m = r.matcher(str);
            return m.find();
        }
        return false;
    }
    public static void main(String[] args) {
        String s = rJust("79636449305034758");
        System.out.println(s);

        String cpId2 = IDConvertUtil.rJust("0517328e-34ff-42ec-9b9a-c7663d55637e");
        String cpId = IDConvertUtil.reduction("79636449-3050-3475-8fff-ffffffffffff");
        System.out.println(cpId);
        System.out.printf(cpId2);
    }
}
