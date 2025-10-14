package domen.rideapp.infrastructure;

import domen.rideapp.infrastructure.jpa.adapter.DriverRepositoryJpaAdapter;
import domen.rideapp.infrastructure.jpa.adapter.RideRepositoryJpaAdapter;
import domen.rideapp.infrastructure.jpa.repository.DriverRepositoryJpa;
import domen.rideapp.infrastructure.jpa.repository.RideRepositoryJpa;
import domen.rideapp.infrastructure.repository.inmemory.DriverRepositoryInMemory;
import domen.rideapp.infrastructure.repository.inmemory.RideRepositoryInMemory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig {

    @Bean
    @ConditionalOnProperty(name = "feature.database.in-memory", havingValue = "false")
    DriverRepositoryJpaAdapter driverRepositoryJpaAdapter(DriverRepositoryJpa repositoryJpa) {
        return new DriverRepositoryJpaAdapter(repositoryJpa);
    }

    @Bean
    @ConditionalOnProperty(name = "feature.database.in-memory", havingValue = "false")
    RideRepositoryJpaAdapter rideRepositoryJpaAdapter(RideRepositoryJpa repositoryJpa) {
        return new RideRepositoryJpaAdapter(repositoryJpa);
    }

    @Bean
    @ConditionalOnProperty(name = "feature.database.in-memory", havingValue = "true", matchIfMissing = true)
    DriverRepositoryInMemory driverRepositoryInMemory() {
        return new DriverRepositoryInMemory();
    }

    @Bean
    @ConditionalOnProperty(name = "feature.database.in-memory", havingValue = "true", matchIfMissing = true)
    RideRepositoryInMemory rideRepositoryInMemory() {
        return new RideRepositoryInMemory();
    }
}