package domen.rideapp.infrastructure.repository.inmemory;

import domen.rideapp.domain.model.Ride;
import domen.rideapp.domain.model.RideStatus;
import domen.rideapp.domain.repository.RideDatabaseRepository;

import java.util.*;

public class RideRepositoryInMemory implements RideDatabaseRepository {
    private final Map<UUID, Ride> rides = new HashMap<>();

    @Override
    public void save(Ride ride) {
        rides.put(UUID.fromString(ride.getId()), ride);
    }

    @Override
    public List<Ride> getPendingRides() {
        return rides.values().stream()
                .filter(ride -> ride.getStatus() == RideStatus.PENDING)
                .toList();
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
