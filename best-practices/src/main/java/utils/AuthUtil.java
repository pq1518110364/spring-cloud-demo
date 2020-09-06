package utils;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author wei.xiang
 * @email wei.xiang@einyun.com
 * @date 2020/7/24 14:28
 * @Description:
 */
@Slf4j
public class AuthUtil {

    /**          admin+CCPG
     * token加密 name + businessId
     * @author : wei.xiang@einyun.com
     * @date :  2020/6/23 14:39
     * @param encryptStr 待加密字符串
     * @return java.lang.String
     */
    public static String encryteToken(String encryptStr)  {
        StringBuilder buf = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(encryptStr.getBytes());
            byte[] b = md.digest();
            int i;
            for (byte value : b) {
                i = value;
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i).toUpperCase());
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            log.error("加密失败:",e);
        }
        return buf.toString();
    }
}
