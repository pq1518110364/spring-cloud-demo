package com.producer.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.Random;

/**
 * @author wei.xiang
 * @email wei.xiang@einyun.com
 * @date 2020/3/11 15:56
 * @Description:
 */
public class Producer {

    public static void main(String[] args) throws InterruptedException {
        Properties p = new Properties();
        p.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.16.0.103:9092");//kafka地址，多个地址用逗号分割
        p.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        p.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(p);
        int i = 0;
        try {
            while (true) {
                if (i>100){
                    break;
                }
                i++;
                String msg = "Hello," + new Random().nextInt(100);
                ProducerRecord<String, String> record = new ProducerRecord<>("test", msg);
                kafkaProducer.send(record);
                System.out.println("消息发送成功:" + msg);
            }
        } finally {
            kafkaProducer.close();
        }

    }
}
