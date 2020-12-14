package com.example.demo;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CarAddition {

    Car createCar(int id, String carType, boolean isNew, int driverId);

    List<Car> getCar();

    ResponseEntity addCar(Car car);

    ResponseEntity editCar(Car car);

    ResponseEntity patchCar(Car car);

    ResponseEntity deleteCar(int id);


}
