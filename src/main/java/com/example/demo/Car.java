package com.example.demo;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Car {

    @Id
    @GeneratedValue
    private int id;

    @Size(min = 1, max = 16)
    private String carType;

    @NotNull
    private boolean isNew;

    @Min(value = 1)
    private int driverId;

    public Car(int id, String carType, boolean isNew, int driverId){

        setId(id);

        setCarType(carType);

        setNew(isNew);

        setDriverId(driverId);

    }

    public Car(){}

    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

}
