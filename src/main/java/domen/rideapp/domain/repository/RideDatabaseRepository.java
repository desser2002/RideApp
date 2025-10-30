package domen.rideapp.domain.repository;

import domen.rideapp.domain.model.Ride;

import java.util.List;

public interface RideDatabaseRepository {
    void save(Ride ride);

    List<Ride> getPendingRides();

    List<Ride> getAllRides();

    void clear();
}