package domen.rideapp;

import domen.rideapp.domain.repository.RideTemporaryRepository;
import domen.rideapp.infrastructure.repository.InMemoryRideTemporaryRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class TemporaryRideStoreTestConfig {
    @Bean
    @Primary
    public RideTemporaryRepository inMemoryTemporaryRideStore() {
        return new InMemoryRideTemporaryRepository();
    }
}
