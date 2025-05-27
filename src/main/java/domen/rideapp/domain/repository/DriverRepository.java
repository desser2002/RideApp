package domen.rideapp.domain.repository;

import domen.rideapp.domain.model.Driver;

import java.util.List;

public interface DriverRepository {
    List<Driver> getAvailableDrivers();

    void save(Driver driver);
}
