package domen.rideapp.infrastructure.repository;

import domen.rideapp.domain.model.Ride;
import domen.rideapp.domain.model.RideStatus;
import domen.rideapp.domain.repository.RideRepository;

import java.util.*;
import java.util.stream.Collectors;

public class RideRepositoryInMemory implements RideRepository {
    private final Map<UUID, Ride> rides = new HashMap<>();

    @Override
    public void save(Ride ride) {
        rides.put(UUID.fromString(ride.getId()), ride);
    }

    @Override
    public List<Ride> getPendingRides() {
        return rides.values().stream()
                .filter(ride -> ride.getStatus() == RideStatus.PENDING)
                .collect(Collectors.toList());
    }

    @Override
    public List<Ride> getAllRides() {
        return new ArrayList<>(rides.values());
    }

    @Override
    public void clear() {
        rides.clear();
    }
}
