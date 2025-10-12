package domen.rideapp.infrastructure.jpa.repository;

import domen.rideapp.domain.model.RideStatus;
import domen.rideapp.infrastructure.jpa.model.RideJpa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RideRepositoryJpa extends JpaRepository<RideJpa, String> {
    List<RideJpa> findByStatus(RideStatus rideStatus);
}
