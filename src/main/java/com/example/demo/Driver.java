package com.example.demo;

import org.hibernate.validator.constraints.Range;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Entity
public class Driver {

    @Id
    @GeneratedValue
    private int id;

    @Size(min = 2)
    private String name;

    @Size(min = 1)
    private char type;

    @Range(min = 1)
    private int carId;

    public Driver(int id, String name, char type, int carId){

        setId(id);

        setName(name);

        setType(type);

        setCarId(carId);
    }

    public Driver(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

}
