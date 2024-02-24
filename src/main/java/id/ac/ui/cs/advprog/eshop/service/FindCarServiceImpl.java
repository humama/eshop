package id.ac.ui.cs.advprog.eshop.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.FindCar;

public class FindCarServiceImpl implements FindCarService {
    @Autowired
    private FindCar findCar;
    @Override
    public List<Car> findAll() {
        // TODO Auto-generated method stub
        Iterator<Car> carIterator = findCar.findAll();
        List<Car> allCar = new ArrayList<>();
        carIterator.forEachRemaining(allCar::add);
        return allCar;
    }
    @Override
    public Car findById(String carId) {
        // TODO Auto-generated method stub
        Car car = findCar.findById(carId);
        return car;
    }
}
