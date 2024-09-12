package ethiqque.crespect.webapp.service.city;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ethiqque.crespect.webapp.model.City;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class CityDataLoader {

    private static final Logger log = LoggerFactory.getLogger(CityDataLoader.class);

    public List<City> loadCitiesFromFile() {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<Map<String, Object>>> typeReference = new TypeReference<List<Map<String, Object>>>() {};
        InputStream inputStream = getClass().getResourceAsStream("/cities.json");

        if (inputStream == null) {
            log.error("Cannot find cities.json");
            return new ArrayList<>();
        }

        try {
            List<Map<String, Object>> citiesData = objectMapper.readValue(inputStream, typeReference);
            List<City> cityList = new ArrayList<>();

            for (Map<String, Object> cityData : citiesData) {
                String cityName = (String) cityData.get("name");
                Long cityId = ((Number) cityData.get("id")).longValue();
                cityList.add(new City(cityId, cityName, 0.0));
            }

            return cityList;

        } catch (IOException e) {
            log.error("Error during cities.json reading", e);
            return new ArrayList<>();
        }
    }
}
