package com.codnos;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.Map;

@Document(collection = "users")
public class MongoUser {
    @Id
    private String id;

    private String name;

    @Field("salary_structure")
    private List<String> salaryStructure;


    private Map<String, List<Long>> salaries;

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

    public Map<String, List<Long>> getSalaries() {
        return salaries;
    }

    public void setSalaries(Map<String, List<Long>> salaries) {
        this.salaries = salaries;
    }
}
