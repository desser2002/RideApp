package domen.rideapp.infrastructure.jpa.repository;

import domen.rideapp.domain.model.DriverStatus;
import domen.rideapp.infrastructure.jpa.model.DriverJpa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DriverRepositoryJpa extends JpaRepository<DriverJpa,String> {
    List<DriverJpa> findByStatus(DriverStatus driverStatus);
}
