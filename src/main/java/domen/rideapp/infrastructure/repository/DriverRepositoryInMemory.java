package domen.rideapp.infrastructure.repository;

import domen.rideapp.domain.model.Driver;
import domen.rideapp.domain.model.DriverStatus;
import domen.rideapp.domain.repository.DriverRepository;

import java.util.*;
import java.util.stream.Collectors;

public class DriverRepositoryInMemory implements DriverRepository {
    private final Map<UUID, Driver> drivers = new HashMap<>();

    @Override
    public List<Driver> getAvailableDrivers() {
        return drivers.values().stream()
                .filter(driver -> driver.status() == DriverStatus.AVAILABLE)
                .collect(Collectors.toList());
    }

    @Override
    public void save(Driver driver) {
        drivers.put(UUID.fromString(driver.id()), driver);
    }

    @Override
    public void remove(String id) {
        drivers.remove(UUID.fromString(id));
    }

    @Override
    public List<Driver> getAll() {
        return drivers.values().stream().toList();
    }

    @Override
    public Optional<Driver> getDriverById(String id) {
        try {
            return Optional.ofNullable(drivers.get(UUID.fromString(id)));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    @Override
    public void clear() {
        drivers.clear();
    }
}
