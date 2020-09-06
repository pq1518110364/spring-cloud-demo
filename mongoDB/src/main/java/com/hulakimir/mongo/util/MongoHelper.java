package com.hulakimir.mongo.util;


import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author xiangwei
 * @date 2020-09-03 2:47 下午
 */
public class MongoHelper {

    private static final Logger logger = LoggerFactory.getLogger(MongoHelper.class);

    static final String DBName = "testdb";

    private MongoHelper() {
    }

    private static MongoClient getMongoClient(){

        //连接到MongoDB服务 如果是远程连接可以替换“localhost”为服务器所在IP地址
        //ServerAddress()两个参数分别为 服务器地址 和 端口
        ServerAddress serverAddress = new ServerAddress("101.133.166.26",27017);
        List<ServerAddress> addrs = new ArrayList<>();
        addrs.add(serverAddress);

        //MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码
        MongoCredential credential = MongoCredential.createScramSha1Credential("user", "admin", "123456".toCharArray());
        List<MongoCredential> credentials = new ArrayList<>();
        credentials.add(credential);

        //通过连接认证获取MongoDB连接
        MongoClient mongoClient = new MongoClient(addrs,credentials);
        return mongoClient;
    }// = new MongoClient(com.mongodb.ServerAddress, PORT);

    // 模拟连接池(阻塞队列)
    private static LinkedBlockingQueue<MongoDatabase> mongoDatabases = new LinkedBlockingQueue<MongoDatabase>(5);

    static {
        initMongoDatabases();
    }

    private static void initMongoDatabases() {
        for (int i = 0; i < 5; i++) {
            MongoDatabase mDatabase = getMongoClient().getDatabase(DBName);
            mongoDatabases.add(mDatabase);
        }
    }

    public static void closeMongoClient(MongoDatabase mongoDatabase) {
        mongoDatabases.add(mongoDatabase);
        logger.debug("CloseMongoClient successfully");
    }

    public static MongoDatabase getMongoDataBase() {
        try {
            MongoDatabase mDatabase = mongoDatabases.take();
            return mDatabase;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

}
