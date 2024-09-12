package ethiqque.crespect.webapp.controller;

import ethiqque.crespect.webapp.model.City;
import ethiqque.crespect.webapp.service.city.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/weather")
@CrossOrigin(origins = "http://localhost:3000")
public class WeatherController {

    @Autowired
    private CityService cityService;

    @GetMapping("/cities")
    public List<City> getCities() {
        return cityService.getAllCities();
    }
}
