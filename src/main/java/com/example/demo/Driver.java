package com.example.demo;

public class Driver {

    private int id;

    private String name;

    private char type;

    private int carId;

    public Driver(int id, String name, char type, int carId){

        setId(id);

        setName(name);

        setType(type);

        setCarId(carId);
    }

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
