package domen.rideapp.domain.service;

import domen.rideapp.domain.model.Driver;
import domen.rideapp.domain.model.DriverStatus;
import domen.rideapp.domain.model.Ride;
import domen.rideapp.domain.model.RideStatus;
import domen.rideapp.domain.repository.DriverRepository;
import domen.rideapp.domain.repository.RideRepository;

import java.util.List;

public class RideService {
    private final PricingService pricingService;
    private final RideRepository rideRepository;
    private final DriverRepository driverRepository;

    public RideService(PricingService pricingService,
                       RideRepository rideRepository, DriverRepository driverRepository) {
        this.pricingService = pricingService;
        this.rideRepository = rideRepository;
        this.driverRepository = driverRepository;
    }

    public List<Ride> getAllRides() {
        return rideRepository.getAllRides();
    }

    public void rideInitiation(String customer, String from, String to) {
        double cost = pricingService.getCost(from, to);
        Ride ride = new Ride(customer, new Localization(from, to), RideStatus.PENDING, cost);
        rideRepository.save(ride);
    }

    public int assignDriversToRides() {
        List<Ride> pendingRides = rideRepository.getPendingRides();
        List<Driver> availableDrivers = driverRepository.getAvailableDrivers();
        int numberOfRidesToFound = Math.min(pendingRides.size(), availableDrivers.size());
        for (int i = 0; i < numberOfRidesToFound; i++) {
            Ride ride = pendingRides.get(i);
            Driver driver = availableDrivers.get(i);
            ride.setStatus(RideStatus.FOUND);
            driver = driver.copyWith(DriverStatus.IN_RIDE);
            driverRepository.save(driver);
            ride.setDriver(driver);
            rideRepository.save(ride);
        }
        return numberOfRidesToFound;
    }
}
