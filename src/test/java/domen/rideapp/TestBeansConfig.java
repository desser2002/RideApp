package domen.rideapp;

import domen.rideapp.domain.model.PricingConfig;
import domen.rideapp.domain.model.RouteEstimate;
import domen.rideapp.domain.repository.PricingRepository;
import domen.rideapp.domain.service.MapService;
import domen.rideapp.domain.service.PricingService;
import domen.rideapp.infrastructure.pricing.CustomPricingService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

@TestConfiguration
public class TestBeansConfig {
    @Bean
    public MapService mapService() {
        return loc -> Optional.of(new RouteEstimate(10.0, 15)); // Stub
    }

    @Bean
    public PricingRepository pricingRepository() {
        return () -> Optional.of(new PricingConfig(2.0, 1.0, 5.0)); // Stub config
    }

    @Bean
    public PricingService pricingService(MapService mapService, PricingRepository pricingRepository) {
        return new CustomPricingService(mapService, pricingRepository);
    }
}
