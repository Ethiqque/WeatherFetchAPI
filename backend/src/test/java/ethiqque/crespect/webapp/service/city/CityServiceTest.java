package ethiqque.crespect.webapp.service.city;

import static org.junit.jupiter.api.Assertions.*;

import ethiqque.crespect.webapp.model.City;
import ethiqque.crespect.webapp.repository.CityRepository;
import ethiqque.crespect.webapp.service.weather.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

class CityServiceTest {

    @Mock
    private CityRepository cityRepository;

    @Mock
    private CityDataLoader cityDataLoader;

    @Mock
    private WeatherService weatherService;

    @InjectMocks
    private CityService cityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCities_WhenCitiesExist() {
        City city1 = new City(1L, "City1", 25.0);
        City city2 = new City(2L, "City2", 18.0);
        List<City> mockCities = Arrays.asList(city1, city2);

        when(cityRepository.findAll()).thenReturn(mockCities);

        List<City> cities = cityService.getAllCities();

        assertNotNull(cities);
        assertEquals(2, cities.size());
        verify(cityRepository, times(1)).findAll();
        verify(cityDataLoader, never()).loadCitiesFromFile();
    }

    @Test
    void testGetAllCities_WhenNoCitiesExist() {
        when(cityRepository.findAll()).thenReturn(new ArrayList<>());

        City city1 = new City(1L, "City1", 0.0);
        City city2 = new City(2L, "City2", 0.0);
        List<City> mockLoadedCities = Arrays.asList(city1, city2);

        when(cityDataLoader.loadCitiesFromFile()).thenReturn(mockLoadedCities);
        when(weatherService.fetchTemperatureForCity(1L)).thenReturn(25.0);
        when(weatherService.fetchTemperatureForCity(2L)).thenReturn(18.0);

        List<City> cities = cityService.getAllCities();

        assertNotNull(cities);

        verify(cityRepository, times(2)).findAll();
        verify(cityRepository, times(1)).saveAll(anyList());
        verify(cityDataLoader, times(1)).loadCitiesFromFile();
        verify(weatherService, times(1)).fetchTemperatureForCity(1L);
        verify(weatherService, times(1)).fetchTemperatureForCity(2L);
    }

    @Test
    void testRefreshCityWeather() {
        City city1 = new City(1L, "City1", 0.0);
        City city2 = new City(2L, "City2", 0.0);
        List<City> mockLoadedCities = Arrays.asList(city1, city2);

        when(cityDataLoader.loadCitiesFromFile()).thenReturn(mockLoadedCities);
        when(weatherService.fetchTemperatureForCity(1L)).thenReturn(25.0);
        when(weatherService.fetchTemperatureForCity(2L)).thenReturn(18.0);

        cityService.refreshCityWeather();

        verify(cityRepository, times(1)).deleteAll();
        verify(cityRepository, times(1)).saveAll(anyList());
        verify(cityDataLoader, times(1)).loadCitiesFromFile();
        verify(weatherService, times(1)).fetchTemperatureForCity(1L);
        verify(weatherService, times(1)).fetchTemperatureForCity(2L);
    }

    @Test
    void testSaveCitiesWithUpdatedWeather() {
        City city1 = new City(1L, "City1", 0.0);
        City city2 = new City(2L, "City2", 0.0);
        List<City> mockLoadedCities = Arrays.asList(city1, city2);

        when(cityDataLoader.loadCitiesFromFile()).thenReturn(mockLoadedCities);
        when(weatherService.fetchTemperatureForCity(1L)).thenReturn(25.0);
        when(weatherService.fetchTemperatureForCity(2L)).thenReturn(18.0);

        cityService.saveCitiesWithUpdatedWeather();

        verify(cityRepository, times(1)).saveAll(anyList());
        verify(weatherService, times(1)).fetchTemperatureForCity(1L);
        verify(weatherService, times(1)).fetchTemperatureForCity(2L);
    }
}
