package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/car")
class CarController{
    
    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/create")
    public String createCarPage(Model model){
        model.addAttribute("car", new Car());
        return "createCar";
    }

    @PostMapping("/create")
    public String createCarPost(@ModelAttribute Car car){
        carService.create(car);
        return "redirect:/car/list";
    }

    @GetMapping("/list")
    public String carListPage(Model model){
        model.addAttribute("cars", carService.findAll());
        return "carList";
    }

    @GetMapping("/edit/{id}")
    public String editCarPage(@PathVariable String id, Model model) {
        Car car = carService.findById(id);
        model.addAttribute("car", car);
        return "editCar";
    }

    @PostMapping("/update")
    public String editCarPost(@ModelAttribute Car car){
        carService.update(car);
        return "redirect:/car/list";
    }

    @PostMapping("/delete")
    public String deleteCar(@RequestParam String id){
        carService.delete(id);
        return "redirect:/car/list";
    }
}