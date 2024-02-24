package id.ac.ui.cs.advprog.eshop.service;

import org.springframework.beans.factory.annotation.Autowired;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.EditCar;

public class EditCarServiceImpl implements EditCarService {
    @Autowired
    private EditCar editCar;
    @Override
    public void update(String carId, Car car) {
        // TODO Auto-generated method stub
        editCar.update(carId, car);
    }
    @Override
    public void deleteCarById(String carId) {
        // TODO Auto-generated method stub
        editCar.delete(carId);
    }
}
