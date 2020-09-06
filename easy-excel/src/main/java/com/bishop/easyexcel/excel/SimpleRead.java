//package com.bishop.easyexcel.excel;
//
//import com.alibaba.excel.EasyExcel;
//import com.alibaba.fastjson.JSON;
//import com.hypersmart.base.util.BeanUtils;
//import com.hypersmart.base.util.FileUtil;
//import com.hypersmart.system.integration.callcenter.base.DataDict;
//import com.hypersmart.system.integration.callcenter.bo.DictBO;
//import com.hypersmart.system.integration.callcenter.utils.DataDictTreeUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.util.ClassUtils;
//import org.springframework.util.ResourceUtils;
//
//import javax.annotation.PostConstruct;
//import java.io.IOException;
//import java.util.Objects;
//
///**
// * @author wei.xiang
// * @email wei.xiang@einyun.com
// * @date 2020/7/13 10:54
// * @Description:
// */
//@Component
//@Slf4j
//public class SimpleRead {
//
//    @Value("${dict.convert.filePath}")
//    private String dictConvertFilePath;
//
//    @Value("${dict.base.filePath}")
//    private String dictBaseFilePath;
//
//    public static void main(String[] args) throws IOException {
//        // 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
//        // 写法1：
//        String filePath = ResourceUtils.getFile("").getAbsolutePath();
//        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
//        filePath+="\\system-integration\\src\\main\\resources\\数据字典.xlsx";
//        EasyExcel.read(filePath, DictBO.class, new ConvertDictDataListener()).sheet().doRead();
//        String filePath2 = ResourceUtils.getFile("").getAbsolutePath();
//
//        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
//        filePath2 +="\\system-integration\\src\\main\\resources\\DataDictionaryInfo.xls";
//        EasyExcel.read(filePath2, DataDict.class, new CallDictDataListener()).sheet().doRead();
//        System.out.println(DataDictTreeUtil.getByCode("Zero"));
//        System.out.println(DataDictTreeUtil.getById("F3EE59A3-D3B1-465D-88DB-99B61176F1F7"));
//        System.out.println(JSON.toJSONString(DataDictTreeUtil.getChildByCode("Gender",false)));
//    }
//
//    @PostConstruct
//    public void initDict() throws Exception {
//        log.info("初始化加载数据字典的内容到内存里来...");
//        // 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
//        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
//        log.info("filePath:{}",dictConvertFilePath);
//        if ( BeanUtils.isEmpty(dictConvertFilePath) || !FileUtil.isExistFile(dictConvertFilePath)){
//            String path = Objects.requireNonNull(Objects.requireNonNull(ClassUtils.getDefaultClassLoader()).getResource("数据字典.xlsx")).getPath();
//            String urlPath=java.net.URLDecoder.decode(path,"utf-8");
//            log.info("urlPath:{}",urlPath);
//            dictConvertFilePath=urlPath;
//        }
//        EasyExcel.read(dictConvertFilePath, DictBO.class, new ConvertDictDataListener()).sheet().doRead();
//
//        // 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
//        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
//        log.info("filePath:{}",dictBaseFilePath);
//        if ( BeanUtils.isEmpty(dictBaseFilePath) || !FileUtil.isExistFile(dictBaseFilePath)){
//            String path = Objects.requireNonNull(Objects.requireNonNull(ClassUtils.getDefaultClassLoader()).getResource("DataDictionaryInfo.xls")).getPath();
//            String urlPath=java.net.URLDecoder.decode(path,"utf-8");
//            log.info("urlPath:{}",urlPath);
//            dictBaseFilePath=urlPath;
//        }
//        EasyExcel.read(dictBaseFilePath, DataDict.class, new CallDictDataListener()).sheet().doRead();
//    }
//}
