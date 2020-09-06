//package com.bishop.easyexcel.excel;
//
//import com.alibaba.excel.context.AnalysisContext;
//import com.alibaba.excel.event.AnalysisEventListener;
//import com.alibaba.fastjson.JSON;
//import com.google.common.collect.BiMap;
//import com.google.common.collect.Lists;
//import com.hypersmart.base.util.BeanUtils;
//import com.hypersmart.system.integration.callcenter.base.DataDict;
//import com.hypersmart.system.integration.callcenter.bo.DictBO;
//import com.hypersmart.system.integration.callcenter.utils.DataDictTreeUtil;
//import com.hypersmart.system.integration.callcenter.utils.DictionaryUtil;
//import lombok.extern.slf4j.Slf4j;
//
//import java.util.ArrayList;
//import java.util.LinkedList;
//import java.util.List;
//
///**
// * @author wei.xiang
// * @email wei.xiang@einyun.com
// * @date 2020/7/13 10:51
// * @Description:
// */
//@Slf4j
//public class CallDictDataListener extends AnalysisEventListener<DataDict> {
//    /**
//     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
//     */
//    private static final int BATCH_COUNT = 5;
//    List<DataDict> list = new ArrayList<>();
//    List<DataDict> dicts = Lists.newLinkedList();
//
//    /**
//     * 这个每一条数据解析都会来调用
//     *
//     * @param data
//     *            one row value. Is is same as {@link AnalysisContext#readRowHolder()}
//     * @param context
//     */
//    @Override
//    public void invoke(DataDict data, AnalysisContext context) {
//        log.info("解析到一条数据:{}", JSON.toJSONString(data));
//        if (BeanUtils.isNotEmpty(data.getId()) && !data.getGcFlag()){
//            list.add(data);
//        }
//        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
//        if (list.size() >= BATCH_COUNT) {
//            dicts.addAll(new LinkedList<>(list));
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
//        if (BeanUtils.isNotEmpty(dicts)){
//            DataDictTreeUtil.buildTree(dicts);
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
//    }
//}
