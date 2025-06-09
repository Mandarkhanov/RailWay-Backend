package com.mandarkhanov.controller;

import com.mandarkhanov.dto.CarDto;
import com.mandarkhanov.exception.ResourceNotFoundException;
import com.mandarkhanov.model.Car;
import com.mandarkhanov.model.Train;
import com.mandarkhanov.repository.CarRepository;
import com.mandarkhanov.repository.TrainRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cars")
@CrossOrigin(origins = "http://localhost:5173")
public class CarController {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private TrainRepository trainRepository;

    @GetMapping
    public Iterable<Car> getAll() {
        return carRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getById(@PathVariable Integer id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Вагон с ID " + id + " не найден"));
        return ResponseEntity.ok(car);
    }

    @PostMapping
    public Car create(@Valid @RequestBody CarDto carDto) {
        Car car = new Car();
        car.setCarNumber(carDto.getCarNumber());
        car.setCarType(carDto.getCarType());
        car.setCapacity(carDto.getCapacity());
        car.setBuildDate(carDto.getBuildDate());
        car.setStatus(carDto.getStatus());

        if (carDto.getTrainId() != null) {
            Train train = trainRepository.findById(carDto.getTrainId())
                    .orElseThrow(() -> new ResourceNotFoundException("Поезд с ID " + carDto.getTrainId() + " не найден"));
            car.setTrain(train);
        }

        return carRepository.save(car);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Car> update(@PathVariable Integer id, @Valid @RequestBody CarDto carDetails) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Вагон с ID " + id + " не найден"));

        car.setCarNumber(carDetails.getCarNumber());
        car.setCarType(carDetails.getCarType());
        car.setCapacity(carDetails.getCapacity());
        car.setBuildDate(carDetails.getBuildDate());
        car.setStatus(carDetails.getStatus());

        if (carDetails.getTrainId() != null) {
            Train train = trainRepository.findById(carDetails.getTrainId())
                    .orElseThrow(() -> new ResourceNotFoundException("Поезд с ID " + carDetails.getTrainId() + " не найден"));
            car.setTrain(train);
        } else {
            car.setTrain(null);
        }

        final Car updatedCar = carRepository.save(car);
        return ResponseEntity.ok(updatedCar);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Вагон с ID " + id + " не найден"));
        carRepository.delete(car);
        return ResponseEntity.noContent().build();
    }
}