package domen.rideapp.infrastructure.jpa.adapter;

import domen.rideapp.domain.model.Ride;
import domen.rideapp.domain.model.RideStatus;
import domen.rideapp.domain.repository.RideRepository;
import domen.rideapp.infrastructure.jpa.mapper.RideMapper;
import domen.rideapp.infrastructure.jpa.repository.RideRepositoryJpa;

import java.util.List;

public class RideRepositoryJpaAdapter implements RideRepository {
    private final RideRepositoryJpa repository;

    public RideRepositoryJpaAdapter(RideRepositoryJpa repository) {
        this.repository = repository;
    }

    @Override
    public void save(Ride ride) {
        repository.save(RideMapper.toEntity(ride));
    }

    @Override
    public List<Ride> getPendingRides() {
        return repository.findByStatus(RideStatus.PENDING).stream()
                .map(RideMapper::toDomain)
                .toList();
    }

    @Override
    public List<Ride> getAllRides() {
        return repository.findAll().stream()
                .map(RideMapper::toDomain)
                .toList();
    }

    @Override
    public void clear() {
        repository.deleteAll();
    }
}
