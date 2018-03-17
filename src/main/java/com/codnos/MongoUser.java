package com.codnos;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "users")
public class MongoUser {
    @Id
    private String id;

    private String name;

    private Integer salary;

    @Field("salary_precision")
    private Integer salaryPrecision;

    public MongoUser() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Integer getSalaryPrecision() {
        return salaryPrecision;
    }

    public void setSalaryPrecision(Integer salaryPrecision) {
        this.salaryPrecision = salaryPrecision;
    }
}
