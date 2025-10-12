package domen.rideapp.infrastructure.jpa.adapter;

import domen.rideapp.domain.model.Driver;
import domen.rideapp.domain.model.DriverStatus;
import domen.rideapp.domain.repository.DriverRepository;
import domen.rideapp.infrastructure.jpa.mapper.DriverMapper;
import domen.rideapp.infrastructure.jpa.repository.DriverRepositoryJpa;

import java.util.List;
import java.util.Optional;

public class DriverRepositoryJpaAdapter implements DriverRepository {

    private final DriverRepositoryJpa repository;

    public DriverRepositoryJpaAdapter(DriverRepositoryJpa driverRepositoryJpa) {
        this.repository = driverRepositoryJpa;
    }

    @Override
    public List<Driver> getAvailableDrivers() {
        return repository.findByStatus(DriverStatus.AVAILABLE).stream()
                .map(DriverMapper::toDomain)
                .toList();
    }

    @Override
    public void save(Driver driver) {
        repository.save(DriverMapper.toEntity(driver));
    }

    @Override
    public void remove(String driver) {
        repository.deleteById(driver);
    }

    @Override
    public List<Driver> getAll() {
        return repository.findAll().stream()
                .map(DriverMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Driver> getDriverById(String id) {
        return repository.findById(id)
                .map(DriverMapper::toDomain);
    }

    @Override
    public void clear() {
        repository.deleteAll();
    }
}
