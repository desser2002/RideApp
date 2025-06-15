package domen.rideapp.infrastructure;

import domen.rideapp.domain.repository.DriverRepository;
import domen.rideapp.domain.repository.RideRepository;
import domen.rideapp.domain.service.DriverService;
import domen.rideapp.domain.service.MapService;
import domen.rideapp.domain.service.PricingService;
import domen.rideapp.domain.service.RideService;
import domen.rideapp.infrastructure.map.GoogleMapsClient;
import domen.rideapp.infrastructure.map.GoogleMapsService;
import domen.rideapp.infrastructure.map.GoogleMapsUrlBuilder;
import domen.rideapp.infrastructure.pricing.CustomPricingService;
import domen.rideapp.infrastructure.repository.DriverRepositoryInMemory;
import domen.rideapp.infrastructure.repository.RideRepositoryInMemory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;

import java.net.http.HttpClient;

@Configuration
public class AppConfig {
    @Bean
    PricingService pricingService(MapService mapService) {
        return new CustomPricingService(mapService);
    }

    @Bean
    DriverRepositoryInMemory driverRepository() {
        return new DriverRepositoryInMemory();
    }

    @Bean
    RideRepositoryInMemory rideRepository() {
        return new RideRepositoryInMemory();
    }

    @Bean
    RideService rideService(PricingService pricingService,
                            DriverRepository driverRepository,
                            RideRepository rideRepository) {
        return new RideService(pricingService, rideRepository, driverRepository);
    }

    @Bean
    DriverService driverService(DriverRepository repository) {
        return new DriverService(repository);
    }

    @Bean
    @ConfigurationProperties(prefix = "google.maps")
    public GoogleMapsConfig googleMapsConfig() {
        return new GoogleMapsConfig();
    }

    @Bean
    GoogleMapsService googleMapsService(GoogleMapsClient client, ConversionService conversionService) {
        return new GoogleMapsService(client, conversionService);
    }

    @Bean
    public HttpClient httpClient() {
        return HttpClient.newHttpClient();
    }

    @Bean
    public GoogleMapsClient googleMapsClient(GoogleMapsUrlBuilder urlBuilder, HttpClient httpClient) {
        return new GoogleMapsClient(urlBuilder, httpClient);
    }

    @Bean
    public GoogleMapsUrlBuilder googleMapsUrlBuilder(GoogleMapsConfig config) {
        return new GoogleMapsUrlBuilder(config);
    }
}
