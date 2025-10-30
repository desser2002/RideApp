package domen.rideapp.infrastructure.repository.inmemory;

import domen.rideapp.domain.model.Ride;
import domen.rideapp.domain.repository.RideCacheRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryRideCacheRepository implements RideCacheRepository {
    private final Map<String, Ride> rides = new HashMap<>();

    @Override
    public void save(Ride ride) {
        if (ride != null) {
            rides.put(ride.getId(), ride);
        }
    }

    @Override
    public List<Ride> getPendingRides() {
        return new ArrayList<>(rides.values());
    }

    @Override
    public void deleteBatch(List<String> assignedRidesIds) {
        if (assignedRidesIds == null) {
            return;
        }
        assignedRidesIds.forEach(rides::remove);
    }

    @Override
    public void clear() {
        rides.clear();
    }

    @Override
    public void saveBatch(List<Ride> rides) {
        if (rides == null || rides.isEmpty()) {
            return;
        }
        rides.forEach(ride -> this.rides.put(ride.getId(), ride));
    }
}