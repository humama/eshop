package id.ac.ui.cs.advprog.eshop.repository;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import id.ac.ui.cs.advprog.eshop.model.Car;

public class FindCar {
    @Autowired
    private List<Car> carData;
    public Iterator<Car> findAll(){
        return carData.iterator();
    }
    public Car findById(String carId){
        for(Car car : carData){
            if(car.getCarId().equals(carId)){
                return car;
            }
        }
        return null;
    }
}
