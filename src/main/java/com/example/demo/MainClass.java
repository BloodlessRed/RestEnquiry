package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
@RestController
@RequestMapping(path = "/mainApplication")
public class MainClass {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static Logger log = LoggerFactory.getLogger(MainClass.class);

    ArrayList<Car> listOfCars = new ArrayList<Car>();

//    public Car[] listOfCarsToArray() {
//        Car[] arrayOfCars = listOfCars.toArray(new Car[listOfCars.size()]);
//        return arrayOfCars;
//    }

    public Car extractionFromDataBase(Car car) {

        return jdbcTemplate.queryForObject(
                "select * from cars where id = ?",
                new Object[]{car.getId()},
                (rs, rowNum) ->

                        createCar(
                                rs.getInt("id"),
                                rs.getString("carType"),
                                rs.getBoolean("isNew"),
                                rs.getInt("driverId")
                        )

        );

    }

    public Car extractionFromDataBase(int id) {

        return jdbcTemplate.queryForObject(
                "select * from cars where id = ?",
                new Object[]{id},
                (rs, rowNum) ->
                        createCar(
                                rs.getInt("id"),
                                rs.getString("carType"),
                                rs.getBoolean("isNew"),
                                rs.getInt("driverId")
                        )
        );

    }

    Car createCar(int id, String carType, boolean isNew, int driverId) {

        return new Car(id, carType, isNew, driverId);
    }

    public static void main(String[] args) {

        SpringApplication.run(MainClass.class, args);

    }

    @GetMapping("/getCars")
    public List<Car> getCar() {

        return jdbcTemplate.query(
                "select * from cars",
                (rs, rowNum) ->
                        createCar(
                                rs.getInt("id"),
                                rs.getString("carType"),
                                rs.getBoolean("isNew"),
                                rs.getInt("driverId")
                        )
        );

    }

    @PostMapping("/createCar")
    public ResponseEntity addCar(@Valid @RequestBody Car car) {

        log.info(car.getCarType(), car);

        jdbcTemplate.update("insert into cars (carType, isNew) values (?,?)", car.getCarType(), car.isNew());

//            listOfCars.add(car);

        return ResponseEntity.ok("A new car was added");


//            return ResponseEntity.status(409).build();

    }

    @PutMapping(path = "/edit")
    public ResponseEntity editCar(@Valid @RequestBody Car car) {

//        for (Car currentCar : listOfCarsToArray()) {
//            if (currentCar.getId() == car.getId()) {
//                listOfCars.remove(currentCar);
//                listOfCars.add(car);
//                return ResponseEntity.ok("This car was modified");
//            }
//        }
        Car currentCar = extractionFromDataBase(car);

        if(currentCar.getId() == car.getId()){

            jdbcTemplate.update("update cars set carType = ?, isNew = ? where id = ?", car.getCarType(), car.isNew(), car.getId());
            return ResponseEntity.ok("This car was modified");

        }else {

            return ResponseEntity.notFound().build();
        }

    }


    @PatchMapping(path = "/patchType")
    public ResponseEntity patchCar(@Valid @RequestBody Car car) {

        Car currentCar = extractionFromDataBase(car);

        if (currentCar.getId() == car.getId()) {
            if (!currentCar.getCarType().equals(car.getCarType()) && car.getCarType() != null) {

                jdbcTemplate.update("update cars set carType = ? where id = ?", car.getCarType(), car.getId());

                return ResponseEntity.ok("This car was modified");
            }
            if (currentCar.isNew() != car.isNew()) {

                jdbcTemplate.update("update cars set isNew = ? where id = ?", car.isNew(), car.getId());

                return ResponseEntity.ok("This car was modified");
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteCar(@PathVariable int id) {

//        for (Car currentCar : listOfCarsToArray()) {
//            if (currentCar.getId() == id) {
//                listOfCars.remove(currentCar);
//                return ResponseEntity.ok("successfully deleted requested item");
//            }
//        }
//        return ResponseEntity.notFound().build();
        jdbcTemplate.update("delete cars where id = ?", id);
        return ResponseEntity.ok("successfully deleted requested item");
    }

}
