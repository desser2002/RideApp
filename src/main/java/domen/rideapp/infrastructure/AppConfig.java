package domen.rideapp.infrastructure;

import domen.rideapp.domain.repository.DriverRepository;
import domen.rideapp.domain.repository.RideRepository;
import domen.rideapp.domain.service.PricingService;
import domen.rideapp.domain.service.RideService;
import domen.rideapp.infrastructure.repository.DriverRepositoryInMemory;
import domen.rideapp.infrastructure.repository.RideRepositoryInMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    PricingService pricingService() {
        return new PricingService();
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
}
