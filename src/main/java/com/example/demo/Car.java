package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class Car {

    @Id
    @GeneratedValue
    private int id;

    @NotBlank(message = "Name is mandatory")
    private String carType;

    @NotBlank(message = "Novelty is mandatory")
    private boolean isNew;

    @NotBlank(message = "Driver's id is mandatory")
    private int driverId;

    public Car(int id, String carType, boolean isNew, int driverId){

        setId(id);

        setCarType(carType);

        setNew(isNew);

        setDriverId(driverId);

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
