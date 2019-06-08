package com.codnos;

import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<MongoUser> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping(value = "/api/users", produces = "application/avro")
    public Mono<byte[]> getAllUsersAvro() {
        return userRepository.findAll().collectList()
                .map(users -> convertToAvro(users));
    }

    @GetMapping("/api/users-without-salaries")
    public Flux<MongoUser> getAllUsersWithoutSalaries() {
        return userRepository.findAllExcludingSalaries();
    }

    @GetMapping("/api/user/{userId}")
    public Mono<MongoUser> getUser(@PathVariable String userId) {
        return userRepository.findById(userId);
    }

    private byte[] convertToAvro(List<MongoUser> users) {
        List<User> items = new ArrayList<>(users.size());
        users.forEach(u -> {
            User user = User.newBuilder()
                    .setName(u.getName())
                    .setFavoriteColor(null)
                    .setSalaryPrecision(u.getSalaryPrecision())
                    .setSalaryStructure(new ArrayList<>(u.getSalaryStructure()))
                    .setSalaries(new HashMap<>())
                    .setSalaries(u.getSalaries().entrySet().stream()
                            .collect(Collectors.toMap(e -> e.getKey(), e -> toBuffer(e.getValue()))))
                    .build();

            items.add(user);
        });
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            SpecificDatumWriter<User> datumWriter = new SpecificDatumWriter<>(User.getClassSchema());
            BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(outputStream, null);
            datumWriter.write(items.get(0), encoder);
            encoder.flush();
            outputStream.close();
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<ByteBuffer> toBuffer(List<Binary> value) {
        return value.stream().map(b -> ByteBuffer.wrap(b.getData())).collect(Collectors.toList());
    }
}
