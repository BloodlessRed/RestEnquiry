package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

@SpringBootApplication
@RestController
@RequestMapping(path = "/mainApplication")
public class MainClass {

    private static Logger log = LoggerFactory.getLogger(MainClass.class);

    ArrayList<Car> listOfCars = new ArrayList<Car>();

    public Car[] listOfCarsToArray(){
        Car[] arrayOfCars = listOfCars.toArray(new Car[listOfCars.size()]);
        return arrayOfCars;
    }

    public static void main(String[] args) {
        SpringApplication.run(MainClass.class, args);
    }

    @GetMapping("/getCars")
    public ArrayList<Car> getCar(){

        return listOfCars;

    }

    @PostMapping(path = "/createCar", consumes = "application/json", produces = "application/json")
    public ResponseEntity addCar(@RequestBody Car car){

        int counter = 0;

        for (Car currentCar : listOfCarsToArray()){
            if(currentCar.getId() == car.getId()){
                counter++;
            }
        }
        if (counter == 0){
            listOfCars.add(car);
            return ResponseEntity.ok("A new car was added");
        }else{
            return ResponseEntity.status(409).build();
        }
    }

    @PutMapping(path = "/edit")
    public ResponseEntity editCar(@RequestBody Car car){

        for(Car currentCar : listOfCarsToArray()){
            if(currentCar.getId() == car.getId()){
                listOfCars.remove(currentCar);
                listOfCars.add(car);
                return ResponseEntity.ok("This car was modified");
            }
        }
        return ResponseEntity.notFound().build();
    }


    @PatchMapping(path = "/patchType")
    public ResponseEntity patchCar(@RequestBody Car car){

        for(Car currentCar : listOfCarsToArray()){
            if(currentCar.getId() == car.getId()){
                if(!currentCar.getCarType().equals(car.getCarType()) && car.getCarType() != null){

                    currentCar.setCarType(car.getCarType());

                    return ResponseEntity.ok("This car was modified");
                }
                if (currentCar.isNew() != car.isNew()){

                    currentCar.setNew(car.isNew());

                    return ResponseEntity.ok("This car was modified");
                }
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteCar(@PathVariable int id){

        for(Car currentCar : listOfCarsToArray()){
            if(currentCar.getId() == id){
                listOfCars.remove(currentCar);
                return ResponseEntity.ok("successfully deleted requested item");
            }
        }
        return ResponseEntity.notFound().build();
    }

}
