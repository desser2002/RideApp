package domen.rideapp.infrastructure;

import domen.rideapp.domain.model.PricingConfig;
import domen.rideapp.domain.model.Ride;
import domen.rideapp.domain.repository.DriverRepository;
import domen.rideapp.domain.repository.RideRepository;
import domen.rideapp.domain.repository.RideCacheRepository;
import domen.rideapp.domain.service.DriverService;
import domen.rideapp.domain.service.PricingService;
import domen.rideapp.domain.service.RideService;
import domen.rideapp.infrastructure.mapping.MapService;
import domen.rideapp.infrastructure.pricing.CustomPricingService;
import domen.rideapp.infrastructure.repository.DriverRepositoryInMemory;
import domen.rideapp.infrastructure.repository.InMemoryRideCacheRepository;
import domen.rideapp.infrastructure.repository.RedisRideCacheRepository;
import domen.rideapp.infrastructure.repository.RideRepositoryInMemory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class AppConfig {
    @Bean
    PricingService pricingService(MapService mapService, PricingConfig pricingConfig) {
        return new CustomPricingService(mapService, pricingConfig);
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
                            RideRepository rideRepository,
                            RideCacheRepository rideCacheRepository) {
        return new RideService(pricingService, rideRepository, driverRepository, rideCacheRepository);
    }

    @Bean
    DriverService driverService(DriverRepository repository) {
        return new DriverService(repository);
    }

    @Bean
    @ConditionalOnProperty(name = "feature.redis.enabled", havingValue = "true")
    RedisRideCacheRepository redisRideCacheRepository(RedisTemplate<String, Ride> rideRedisTemplate) {
        return new RedisRideCacheRepository(rideRedisTemplate);
    }

    @Bean
    @ConditionalOnProperty(name = "feature.redis.enabled", havingValue = "false", matchIfMissing = true)
    InMemoryRideCacheRepository inMemoryRideCacheRepository() {
        return new InMemoryRideCacheRepository();
    }
}
