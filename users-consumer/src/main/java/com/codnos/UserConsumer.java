package com.codnos;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.InsertManyOptions;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.bson.Document;
import org.bson.types.Binary;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class UserConsumer {
    public static void main(String[] args) {
        String groupId = "mongo-storage";
        String inputTopic = "send-user";
        String schemaRegistryUrl = "http://localhost:8081";
        String brokers = "localhost:9092";
        KafkaConsumer<String, User> consumer = new KafkaConsumer<>(createConsumerConfig(brokers, groupId, schemaRegistryUrl));

        MongoClient mongo = new MongoClient("localhost", 27017);
        MongoDatabase db = mongo.getDatabase("myDb");
        MongoCollection<Document> collection = db.getCollection("users");

        consumer.subscribe(Collections.singletonList(inputTopic));
        System.out.println("Reading topic:" + inputTopic);

        while (true) {
            ConsumerRecords<String, User> records = consumer.poll(1000);
            List<Document> toInsert = new ArrayList<>();
            for (ConsumerRecord<String, User> record : records) {
                User value = record.value();
                Document document = new Document();
                document.put("name", value.getName().toString());
                document.put("salary_precision", value.getSalaryPrecision());
                document.put("salary_structure", toList(value.getSalaryStructure()));
                document.put("salaries", toMapOfBinaries(value.getSalaries()));
                toInsert.add(document);
                System.out.println("Inserting " + document);
//                collection.insertOne(document);
            }
            if (!toInsert.isEmpty()) {
                collection.insertMany(toInsert, new InsertManyOptions().ordered(false));
                System.out.println("Inserted " + toInsert.size() + " documents");
            }
        }
    }

    private static Map<String, List<Binary>> toMapOfBinaries(Map<CharSequence, List<ByteBuffer>> salaries) {
        return salaries.entrySet().stream().collect(Collectors.toMap(e -> e.getKey().toString(), e -> getBinaries(e.getValue())));
    }

    private static List<Binary> getBinaries(List<ByteBuffer> value) {
        return value.stream().map(b -> new Binary(b.array())).collect(Collectors.toList());
    }

    private static List<String> toList(List<CharSequence> salaryStructure) {
        return salaryStructure.stream().map(CharSequence::toString).collect(Collectors.toList());
    }

    private static Properties createConsumerConfig(String brokers, String groupId, String schemaRegistryUrl) {
        Properties props = new Properties();
        props.put("bootstrap.servers", brokers);
        props.put("group.id", groupId);
        props.put("auto.commit.enable", "true");
        props.put("auto.offset.reset", "latest");
        props.put("schema.registry.url", schemaRegistryUrl);
        props.put("specific.avro.reader", true);
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "io.confluent.kafka.serializers.KafkaAvroDeserializer");

        return props;
    }

}
