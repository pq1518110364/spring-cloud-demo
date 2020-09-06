//package utils;
//
//import com.google.common.collect.BiMap;
//import com.google.common.collect.HashBiMap;
//import com.google.common.collect.Maps;
//import com.hypersmart.base.exception.BaseException;
//import com.hypersmart.base.util.BeanUtils;
//import com.hypersmart.system.integration.callcenter.bo.DictBO;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//
//import java.util.Map;
//
///**
// * @author wei.xiang
// * @email wei.xiang@einyun.com
// * @date 2020/7/13 10:10
// * @Description:
// */
//@Slf4j
//public class DictionaryUtil {
//
//    public static Map<String, DictBO> dictionaryBOMap= Maps.newConcurrentMap();
//    public static BiMap<String, String> convertMap= HashBiMap.create();
//    /**
//     * 通过GUID或key 获取数据字典
//     * @author : wei.xiang@einyun.com
//     * @date :  2020/7/13 10:12
//     *
//     * @param str s
//     * @return com.hypersmart.system.integration.callcenter.bo.DictionaryBO
//     */
//    public static DictBO getByGUIDorKey(String str){
//        log.info("数据字典转换");
//        log.info("str:{}",str);
//        if (!IDConvertUtil.isGUID(str)){
//          String s=convertMap.inverse().get(str);
//          if (BeanUtils.isNotEmpty(s)){
//              log.info("key({})-->GUID:({}) success:",str,s);
//              str=s;
//          }
//        }
//        //TODO 需要去除,调用方没有空指针判断
//        DictBO dictBO = null;
//        str=str.toUpperCase();
//        if (dictionaryBOMap.containsKey(str)){
//            dictBO= dictionaryBOMap.get(str);
//        }
//        if (BeanUtils.isEmpty(dictBO)){
//            throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR,
//                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
//                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()+":找不到对应数据字典");
//        }
//        return dictBO;
//    }
//    public static boolean containByGUIDorKey(String str){
//        log.info("数据字典是否包含id或key");
//        log.info("str:{}",str);
//        if (!IDConvertUtil.isGUID(str)){
//            String s=convertMap.inverse().get(str);
//            if (BeanUtils.isNotEmpty(s)){
//                log.info("key({})-->GUID:({}) success:",str,s);
//                str=s;
//            }
//        }
//
//        DictBO dictBO = null;
//        str=str.toUpperCase();
//        return dictionaryBOMap.containsKey(str);
//
//    }
//
//}
