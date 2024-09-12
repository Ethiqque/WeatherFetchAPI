package ethiqque.crespect.webapp.scheduler;

import ethiqque.crespect.webapp.service.city.CityService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TempRefreshScheduler {

    private final CityService cityService;

    public TempRefreshScheduler(CityService cityService) {
        this.cityService = cityService;
    }

    @Scheduled(cron = "${scheduler.fetch.cron}") // ones per dat at 3.00 AM
    public void refreshCityTemperatures() {
        cityService.refreshCityWeather();
    }
}