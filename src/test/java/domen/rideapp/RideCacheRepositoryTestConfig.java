package domen.rideapp;

import domen.rideapp.domain.repository.RideCacheRepository;
import domen.rideapp.infrastructure.repository.InMemoryRideCacheRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class RideCacheRepositoryTestConfig {
    @Bean
    @Primary
    public RideCacheRepository inMemoryTemporaryRideStore() {
        return new InMemoryRideCacheRepository();
    }
}
