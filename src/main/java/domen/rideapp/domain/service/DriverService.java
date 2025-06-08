package domen.rideapp.domain.service;

import domen.rideapp.domain.model.Driver;
import domen.rideapp.domain.model.DriverStatus;
import domen.rideapp.domain.repository.DriverRepository;

import java.util.List;
import java.util.Optional;

public class DriverService {
    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    DriverRepository driverRepository;

    public List<Driver> getAllDrivers() {
        return driverRepository.getAll();
    }

    public Optional<Driver> getDriverById(String id) {
        return driverRepository.getDriverById(id);
    }

    public Driver add(String firstName, String lastName) {
        Driver driver = new Driver(firstName, lastName);
        driverRepository.save(driver);
        return driver;
    }

    public void remove(String id) {
        driverRepository.remove(id);
    }

    public void update(String id, String firstName, String lastName, String status) {
        Driver existingDriver = driverRepository.findDriverById(id);
        if (existingDriver != null) {
            driverRepository.save(existingDriver.copyWith(firstName, lastName, DriverStatus.valueOf(status)));
        }
    }

    public void updateStatus(String id, DriverStatus status) {
        Driver existingDriver = driverRepository.findDriverById(id);
        if (existingDriver != null) {
            driverRepository.save(existingDriver.copyWith(status));
        }
    }
}
