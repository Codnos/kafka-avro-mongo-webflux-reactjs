package com.codnos;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.bson.types.Binary;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;

public class UserConverter extends JsonSerializer<MongoUser> {

    @Override
    public void serialize(MongoUser mongoUser, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeFieldName("id");
        jsonGenerator.writeString(mongoUser.getId());
        jsonGenerator.writeFieldName("name");
        jsonGenerator.writeString(mongoUser.getName());
        if (mongoUser.getSalaryStructure() != null) {
            jsonGenerator.writeArrayFieldStart("salaryStructure");
            for (String salaryItem : mongoUser.getSalaryStructure()) {
                jsonGenerator.writeString(salaryItem);
            }
            jsonGenerator.writeEndArray();
        }
        Integer precision = mongoUser.getSalaryPrecision();
        if (precision != null && mongoUser.getSalaries() != null) {
            jsonGenerator.writeFieldName("salaries");
            jsonGenerator.writeStartObject();
            for (Map.Entry<String, List<Binary>> item : mongoUser.getSalaries().entrySet()) {
                jsonGenerator.writeFieldName(item.getKey());
                jsonGenerator.writeStartArray();
                for (Binary value : item.getValue()) {
                    jsonGenerator.writeNumber(new BigDecimal(new BigInteger(value.getData()), precision));
                }
                jsonGenerator.writeEndArray();
            }
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndObject();
    }
}
