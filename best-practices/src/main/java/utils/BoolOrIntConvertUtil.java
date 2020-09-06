package utils;

/**
 * @author wei.xiang
 * @email wei.xiang@einyun.com
 * @date 2020/7/10 17:15
 * @Description:
 */
public class BoolOrIntConvertUtil {

    /**
     *
     * @author : wei.xiang@einyun.com
     * @date :  2020/7/10 17:17
     * @param b b
     * @return java.lang.Integer
     */
    public static Integer booleanToInt(Boolean b){
        if (b==null){
            return null;
        }else {
            return b?1:0;
        }
    }

    /**
     *
     * @author : wei.xiang@einyun.com
     * @date :  2020/7/10 17:19
     *
     * @param i i
     * @return java.lang.Boolean
     */
    public static Boolean intToBoolean(Integer i){
        if (i==null){
            return false;
        }else {
            return i == 1;
        }
    }
}


