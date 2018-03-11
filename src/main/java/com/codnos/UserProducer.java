package com.codnos;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class UserProducer {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                org.apache.kafka.common.serialization.StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                io.confluent.kafka.serializers.KafkaAvroSerializer.class);
        props.put("schema.registry.url", "http://localhost:8081");
        KafkaProducer<String, User> producer = new KafkaProducer<>(props);

        User user1 = new User();
        user1.setName("Alyssa");
        user1.setFavoriteColor("blue");
        user1.setSalary(256);
        user1.setSalaryPrecision(2);
        ProducerRecord<String, User> record = new ProducerRecord<String, User>("send-user", "Alyssa", user1);
        Future<RecordMetadata> resultFuture = producer.send(record);
        RecordMetadata recordMetadata = resultFuture.get();
        System.out.println(recordMetadata.toString());
    }
}
