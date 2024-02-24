package id.ac.ui.cs.advprog.eshop.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import id.ac.ui.cs.advprog.eshop.model.Car;

public class EditCar {
    @Autowired
    private List<Car> carData;
    public Car update(String id, Car updatedCar){
        for(int i = 0; i < carData.size(); i++){
            Car car = carData.get(i);
            if(car.getCarId().equals(id)){
                car.setCarName(updatedCar.getCarName());
                car.setCarColor(updatedCar.getCarColor());
                car.setCarQuantity(updatedCar.getCarQuantity());
                return car;
            }
        }
        return null;
    }
    public void delete(String id){ carData.removeIf(car -> car.getCarId().equals(id)); }

}
