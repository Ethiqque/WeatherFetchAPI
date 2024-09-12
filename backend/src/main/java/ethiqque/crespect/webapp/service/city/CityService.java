package ethiqque.crespect.webapp.service.city;

import ethiqque.crespect.webapp.model.City;
import ethiqque.crespect.webapp.repository.CityRepository;
import ethiqque.crespect.webapp.service.weather.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CityService {

    private final CityRepository cityRepository;
    private final CityDataLoader cityDataLoader;
    private final WeatherService weatherService;

    public CityService(CityRepository cityRepository, CityDataLoader cityDataLoader, WeatherService weatherService) {
        this.cityRepository = cityRepository;
        this.cityDataLoader = cityDataLoader;
        this.weatherService = weatherService;
    }

    @Transactional
    public void refreshCityWeather() {
        cityRepository.deleteAll();
        saveCitiesWithUpdatedWeather();
    }

    @Cacheable(value = "cityListCache")
    @Transactional(readOnly = true)
    public List<City> getAllCities() {
        List<City> cities = cityRepository.findAll();
        if (cities.isEmpty()) {
            saveCitiesWithUpdatedWeather();
            return cityRepository.findAll();
        }
        return cities;
    }

    @Transactional
    public void saveCitiesWithUpdatedWeather() {
        List<City> cities = cityDataLoader.loadCitiesFromFile();
        cities.forEach(city -> city.setTemperature(weatherService.fetchTemperatureForCity(city.getCityId())));
        cityRepository.saveAll(cities);
    }
}
