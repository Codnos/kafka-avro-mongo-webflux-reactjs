package com.codnos;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.Map;

@Document(collection = "users")
@JsonSerialize(using = UserConverter.class)
public class MongoUser {
    @Id
    private String id;

    private String name;

    @Field("salary_structure")
    private List<String> salaryStructure;


    private Map<String, List<Binary>> salaries;

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

    public Integer getSalaryPrecision() {
        return salaryPrecision;
    }

    public void setSalaryPrecision(Integer salaryPrecision) {
        this.salaryPrecision = salaryPrecision;
    }

    public List<String> getSalaryStructure() {
        return salaryStructure;
    }

    public void setSalaryStructure(List<String> salaryStructure) {
        this.salaryStructure = salaryStructure;
    }

    public Map<String, List<Binary>> getSalaries() {
        return salaries;
    }

    public void setSalaries(Map<String, List<Binary>> salaries) {
        this.salaries = salaries;
    }
}
