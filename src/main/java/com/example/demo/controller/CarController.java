package com.example.demo.controller;

import com.example.demo.Car;
import com.example.demo.CarAddition;
import com.example.demo.repositories.CarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(path = "/mainApplication")
public class CarController implements CarAddition {

    @Autowired
    private CarRepository carRepository;

    private static Logger log = LoggerFactory.getLogger(com.example.demo.controller.CarController.class);

    @Override
    public Car createCar(int id, String carType, boolean isNew, int driverId) {

        return new Car(id, carType, isNew, driverId);
    }

    @Override
    @GetMapping("/getCars")
    public List<Car> getCar() {

        return carRepository.findAll();
    }

    @Override
    @PostMapping("/createCar")
    public ResponseEntity addCar(@Valid @RequestBody Car car) {

        log.info("We are adding " + car.getCarType() + " and this car " + (car.isNew() ? "is new" : "is not new"));

        carRepository.save(car);

        return ResponseEntity.ok("A new car was added");

    }

    @Override
    @PutMapping(path = "/edit")
    public ResponseEntity editCar(@Valid @RequestBody Car car) {

        log.info("We have edited the car with id: " + car.getId());

        Car currentCar = carRepository.findById(car.getId());

        if (currentCar.getId() == car.getId()) {

            carRepository.save(car);

            return ResponseEntity.ok("This car was modified");

        } else {

            return ResponseEntity.notFound().build();
        }

    }

    @Override
    @PatchMapping(path = "/patchType")
    public ResponseEntity patchCar(@Valid @RequestBody Car car) {

        Car currentCar = carRepository.findById(car.getId());

        if (currentCar == null) {
            return ResponseEntity.notFound().build();
        }

        if (currentCar.getId() == car.getId()) {
            if (car.getCarType() != null && !currentCar.getCarType().equals(car.getCarType())) {

                currentCar.setCarType(car.getCarType());

            }

            if (currentCar.isNew() != car.isNew()) {

                currentCar.setNew(car.isNew());

            }

            if (currentCar.getDriverId() != car.getDriverId()) {

                currentCar.setDriverId(car.getDriverId());

            }

            carRepository.save(currentCar);

            return ResponseEntity.ok("This car was modified");
        }

        return ResponseEntity.notFound().build();
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteCar(@PathVariable int id) {

        Car currentCar = carRepository.findById(id);

        if (currentCar.getId() == id) {

            carRepository.deleteById(id);

            return ResponseEntity.ok("successfully deleted requested item");

        } else {

            return ResponseEntity.notFound().build();

        }

    }
}
