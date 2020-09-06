package com.hulakimir.mongo.util;

/**
 * @author xiangwei
 * @date 2020-09-03 3:01 下午
 */

import cn.hutool.json.JSON;
import cn.hutool.json.JSONNull;
import cn.hutool.json.JSONUtil;
import com.hulakimir.mongo.dao.MongoDao;
import com.hulakimir.mongo.dao.MongoDaoImpl;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.List;
import java.util.Map;


public class UtilsTest {
    public static void main(String[] args) throws Exception {
        // 增加文档
        MongoDao mongoDao = new MongoDaoImpl();
        MongoDatabase db = MongoHelper.getMongoDataBase();
        //insertDoc(db,mongoDao,"runoob");
        List<Map<String, Object>> queryAll= mongoDao.queryAll(db,"runoob");
        if (queryAll!=null){
            queryAll.forEach(
                    stringObjectMap -> {
                        System.out.println(JSONUtil.toJsonStr(stringObjectMap));
                    }
            );
        }
    }

    private static void insertDoc(MongoDatabase db, MongoDao mongoDao, String table) throws Exception {
        Document document = new Document("title222123", "MongoDB222123").append("description", "database")
                .append("likes", 100).append("by", "Fly");
        mongoDao.insert(db, table, document);
    }
}
