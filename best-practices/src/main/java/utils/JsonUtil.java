package utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author wei.xiang
 * @email wei.xiang@einyun.com
 * @date 2020/7/1 17:17
 * @Description:
 */
@Slf4j
public class JsonUtil {

    /**
     * 判断是否为JsonObject
     * @author : wei.xiang@einyun.com
     * @date :  2020/7/1 17:18
     * @param content str
     * @return boolean
     */
    public static boolean isJsonObject(String content) {
        // 此处应该注意，不要使用StringUtils.isEmpty(),因为当content为"  "空格字符串时，JSONObject.parseObject可以解析成功，
        // 实际上，这是没有什么意义的。所以content应该是非空白字符串且不为空，判断是否是JSON数组也是相同的情况。
        if(StringUtils.isBlank(content))
            return false;
        try {
            JSONObject jsonStr = JSONObject.parseObject(content);
            log.info("jsonStr:{}",jsonStr.toJSONString());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断是否为JsonArray
     * @author : wei.xiang@einyun.com
     * @date :  2020/7/1 17:19
     *
     * @param content str
     * @return boolean
     */
    public static boolean isJsonArray(String content) {
        if(StringUtils.isBlank(content))
            return false;
        StringUtils.isEmpty(content);
        try {
            JSONArray jsonStr = JSONArray.parseArray(content);
            log.info("jsonStr:{}",jsonStr.toJSONString());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
