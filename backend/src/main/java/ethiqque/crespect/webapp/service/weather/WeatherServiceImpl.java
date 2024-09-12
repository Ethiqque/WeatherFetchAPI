package ethiqque.crespect.webapp.service.weather;

import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Log4j2
@Service
public class WeatherServiceImpl implements WeatherService {

    private static final Logger logger = LoggerFactory.getLogger(WeatherServiceImpl.class);

    @Value("${openweathermap.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public WeatherServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Double fetchTemperatureForCity(Long cityId) {
        String apiUrl = String.format("https://api.openweathermap.org/data/2.5/weather?id=%d&appid=%s&units=metric", cityId, apiKey);
        try {
            Map response = restTemplate.getForObject(apiUrl, Map.class);
            if (response != null && response.containsKey("main")) {
                Map<String, Object> mainData = (Map<String, Object>) response.get("main");
                return mainData != null && mainData.containsKey("temp") ? ((Number) mainData.get("temp")).doubleValue() : null;
            }
        } catch (Exception e) {
            logger.error("Error fetching temperature for city {}: {}", cityId, e.getMessage());
        }
        return 0.0;
    }
}
