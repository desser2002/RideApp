package domen.rideapp.infrastructure.repository;

import domen.rideapp.domain.model.Ride;
import domen.rideapp.domain.repository.RideTemporaryRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryRideTemporaryRepository implements RideTemporaryRepository {
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
            rides.forEach(rides::remove);
        }
    }

    @Override
    public void delete(String assignedRideId) {
        rides.remove(assignedRideId);
    }

    @Override
    public void clear() {
        rides.clear();
    }
}