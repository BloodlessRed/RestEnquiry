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
public class MainClass implements CarController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static Logger log = LoggerFactory.getLogger(MainClass.class);

    @Override
    public Car extractionFromDataBase(@Valid Car car) {

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

    @Override
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
    @Override
    public Car createCar(int id, String carType, boolean isNew, int driverId) {

        return new Car(id, carType, isNew, driverId);
    }

    public static void main(String[] args) {

        SpringApplication.run(MainClass.class, args);

    }
    @Override
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
    @Override
    @PostMapping("/createCar")
    public ResponseEntity addCar(@Valid @RequestBody Car car) {

        log.info(car.getCarType(), car);

        jdbcTemplate.update("insert into cars (carType, isNew) values (?,?)", car.getCarType(), car.isNew());

        return ResponseEntity.ok("A new car was added");

    }
    @Override
    @PutMapping(path = "/edit")
    public ResponseEntity editCar(@Valid @RequestBody Car car) {

        Car currentCar = extractionFromDataBase(car);

        if (currentCar.getId() == car.getId()) {

            jdbcTemplate.update("update cars set carType = ?, isNew = ? where id = ?", car.getCarType(), car.isNew(), car.getId());
            return ResponseEntity.ok("This car was modified");

        } else {

            return ResponseEntity.notFound().build();
        }

    }

    @Override
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
    @Override
    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteCar(@PathVariable int id) {

        Car currentCar = extractionFromDataBase(id);

        if (currentCar.getId() == id){

            jdbcTemplate.update("delete cars where id = ?", id);

            return ResponseEntity.ok("successfully deleted requested item");

        }else {

            return ResponseEntity.notFound().build();

        }

    }

}
