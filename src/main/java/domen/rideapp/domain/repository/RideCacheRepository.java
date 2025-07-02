package domen.rideapp.domain.repository;

import domen.rideapp.domain.model.Ride;

import java.util.List;

public interface RideCacheRepository {
    void save(Ride ride);

    List<Ride> getPendingRides();

    void deleteBatch(List<String> assignedRides);

    void delete(String assignedRideId);

    void clear();
}