package com.bishop.easyexcel.read;

import lombok.Data;

/**
 * @author wei.xiang
 * @email wei.xiang@einyun.com
 * @date 2020/3/25 13:54
 * @Description:
 */
@Data
public class ReadData {
    private String key;
    private String value;

    public ReadData() {
    }

    public ReadData(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
