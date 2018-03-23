package com.codnos;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface UserRepository extends ReactiveMongoRepository<MongoUser, String> {

    @Query(value="{}", fields="{salaries : 0, salaryStructure: 0, salaryPrecision: 0}")
    Flux<MongoUser> findAllExcludingSalaries();
}