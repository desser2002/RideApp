package domen.rideapp.domain.repository;

import domen.rideapp.domain.model.Ride;

import java.util.List;

public interface RideRepository {
    void save(Ride ride);

    List<Ride> getPendingRides();
}
