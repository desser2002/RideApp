package domen.rideapp.infrastructure;

import domen.rideapp.domain.repository.DriverRepository;
import domen.rideapp.domain.repository.RideDatabaseRepository;
import domen.rideapp.infrastructure.jpa.adapter.DriverRepositoryJpaAdapter;
import domen.rideapp.infrastructure.jpa.adapter.RideRepositoryJpaAdapter;
import domen.rideapp.infrastructure.jpa.repository.DriverRepositoryJpa;
import domen.rideapp.infrastructure.jpa.repository.RideRepositoryJpa;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig {

    @Bean
    DriverRepository driverRepositoryJpaAdapter(DriverRepositoryJpa repositoryJpa) {
        return new DriverRepositoryJpaAdapter(repositoryJpa);
    }

    @Bean
    RideDatabaseRepository rideRepositoryJpaAdapter(RideRepositoryJpa repositoryJpa) {
        return new RideRepositoryJpaAdapter(repositoryJpa);
    }
}