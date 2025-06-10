package domen.rideapp.domain.repository;

import domen.rideapp.domain.model.Driver;

import java.util.List;
import java.util.Optional;

public interface DriverRepository {
    List<Driver> getAvailableDrivers();

    void save(Driver driver);

    void remove(String driver);

    List<Driver> getAll();

    Optional<Driver> getDriverById(String id);

    void clear();
}
