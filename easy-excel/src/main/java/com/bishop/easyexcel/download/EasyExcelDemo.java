package com.bishop.easyexcel.download;

import com.alibaba.excel.EasyExcel;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author wei.xiang
 * @date 2020/3/24 17:20
 * @Description:
 */
@RequestMapping("/easyExcel")
@Controller
public class EasyExcelDemo {

    @GetMapping("/download")
    public void download(HttpServletResponse response) throws IOException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("测试", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
         EasyExcel.write(response.getOutputStream(), Data.class).sheet("模板").doWrite(creatData());
    }

    private List<Data> creatData() {
        LinkedList<Data> arrayList = Lists.newLinkedList();
        for (int i = 0; i <100 ; i++) {
            arrayList.add(new Data(i+"",i+""));
        }
        return arrayList;
    }

    @lombok.Data
    public class Data{
        private String key;
        private String value;

        public Data() {
        }

        public Data(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }
}
