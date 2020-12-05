package com.example.demo;

public class Car {

    private int id;

    private String carType;

    private boolean isNew;

    public Car(int id, String carType, boolean isNew){

        setId(id);

        setCarType(carType);

        setNew(isNew);

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }


    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

}
