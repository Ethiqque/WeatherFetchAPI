package ethiqque.crespect.webapp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "city_id")
    private Long cityId;

    @Column(name = "name")
    private String name;

    @Column(name = "temperature")
    private double temperature;

    public City(Long cityId, String name, double temperature) {
        this.cityId = cityId;
        this.name = name;
        this.temperature = temperature;
    }

    public City() {
    }

    public Long getCityId() {
        return cityId;
    }

    public String getName() {
        return name;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}
