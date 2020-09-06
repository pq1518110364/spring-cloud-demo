//package com.bishop.easyexcel.excel;
//
//import com.alibaba.excel.context.AnalysisContext;
//import com.alibaba.excel.event.AnalysisEventListener;
//import com.alibaba.fastjson.JSON;
//import com.google.common.collect.BiMap;
//import com.hypersmart.base.util.BeanUtils;
//import com.hypersmart.system.integration.callcenter.bo.DictBO;
//import com.hypersmart.system.integration.callcenter.utils.DictionaryUtil;
//import lombok.extern.slf4j.Slf4j;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * @author wei.xiang
// * @email wei.xiang@einyun.com
// * @date 2020/7/13 10:51
// * @Description:
// */
//@Slf4j
//public class ConvertDictDataListener extends AnalysisEventListener<DictBO> {
//    /**
//     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
//     */
//    private static final int BATCH_COUNT = 5;
//    List<DictBO> list = new ArrayList<>();
//
//    /**
//     * 这个每一条数据解析都会来调用
//     *
//     * @param data
//     *            one row value. Is is same as {@link AnalysisContext#readRowHolder()}
//     * @param context
//     */
//    @Override
//    public void invoke(DictBO data, AnalysisContext context) {
//        log.info("解析到一条数据:{}", JSON.toJSONString(data));
//        if (BeanUtils.isNotEmpty(data.getCallGUID()) && BeanUtils.isNotEmpty(data.getMDMKey())){
//            list.add(data);
//        }
//        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
//        if (list.size() >= BATCH_COUNT) {
//            initDict();
//            // 存储完成清理
//            list.clear();
//        }
//    }
//    /**
//     * 数据字典初始化
//     * @author : wei.xiang@einyun.com
//     * @date :  2020/7/13 11:46
//     */
//    private void initDict() {
//        if (BeanUtils.isNotEmpty(list)){
//            BiMap<String, String> map = DictionaryUtil.convertMap;
//            for (DictBO dictBO : list) {
//                if (!map.containsKey(dictBO.getCallGUID()) && !map.containsValue(dictBO.getMDMKey())){
//                    map.put(dictBO.getCallGUID().toUpperCase(), dictBO.getMDMKey());
//                    DictionaryUtil.dictionaryBOMap.put(dictBO.getCallGUID().toUpperCase(), dictBO);
//                }else {
//                    log.error("数据重复:{}",JSON.toJSONString(dictBO));
//                }
//            }
//           // DictionaryUtil.dictionaryBOMap.putAll(list.stream().collect(Collectors.toMap(DictBO::getCallGUID, a -> a, (k1, k2) -> k1)));
//        }
//    }
//
//    /**
//     * 所有数据解析完成了 都会来调用
//     *
//     * @param context
//     */
//    @Override
//    public void doAfterAllAnalysed(AnalysisContext context) {
//        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
//        log.info("所有数据解析完成！");
//        initDict();
//        DictBO female = DictionaryUtil.getByGUIDorKey("Female");
//        if (BeanUtils.isNotEmpty(female)){
//            log.info("female:{}",JSON.toJSONString(female));
//        }
//    }
//}
