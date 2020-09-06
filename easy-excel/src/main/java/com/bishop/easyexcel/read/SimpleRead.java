package com.bishop.easyexcel.read;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.enums.CellExtraTypeEnum;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.bishop.easyexcel.download.EasyExcelDemo;

import java.io.File;

/**
 * @author wei.xiang
 * @email wei.xiang@einyun.com
 * @date 2020/3/25 13:49
 * @Description:
 */
public class SimpleRead {

    public static void main(String[] args) {
        // 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
        // 写法1：
        String fileName ="/Users/xiangwei/Downloads/数据字典(主数据字典已匹配).xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(fileName, new NoModelDataListener())
                .extraRead(CellExtraTypeEnum.MERGE).sheet().doRead();
        // 写法2：
//        fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
//        ExcelReader excelReader = EasyExcel.read(fileName, Object.class, new DemoDataListener()).build();
//        ReadSheet readSheet = EasyExcel.readSheet(0).build();
//        excelReader.read(readSheet);
        // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
//        excelReader.finish();
    }
}
