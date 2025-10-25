package domen.rideapp.infrastructure.repository;

import domen.rideapp.domain.model.Ride;
import domen.rideapp.domain.repository.RideCacheRepository;
import domen.rideapp.domain.repository.RideRepository;

import java.util.List;

public class CachedRideRepository implements RideRepository {
    private final RideRepository database;
    private final RideCacheRepository cache;

    public CachedRideRepository(RideRepository database, RideCacheRepository cache) {
        this.database = database;
        this.cache = cache;
    }


    @Override
    public void save(Ride ride) {
        database.save(ride);
        cache.save(ride);
    }

    @Override
    public List<Ride> getPendingRides() {
        List<Ride> rides = cache.getPendingRides();
        if (!rides.isEmpty()) {
            return rides;
        }

        rides = database.getPendingRides();
        cache.saveBatch(rides);
        return rides;
    }

    @Override
    public List<Ride> getAllRides() {
        return database.getAllRides();
    }

    @Override
    public void clear() {
        cache.clear();
        database.clear();
    }

    public void invalidateCacheBatch(List<String> rideIds) {
        cache.deleteBatch(rideIds);
    }
}
