package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;

public interface EditCarService {
    public void update(String carId, Car car);
    public void deleteCarById(String carId);
}
