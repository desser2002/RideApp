package domen.rideapp.domain.service;

import domen.rideapp.domain.model.*;
import domen.rideapp.domain.repository.DriverRepository;
import domen.rideapp.domain.repository.RideRepository;

import java.util.ArrayList;
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
        return new ArrayList<>(rideRepository.getAllRides());
    }

    public String rideInitiation(String customer, Localization localization) {
        Price cost = pricingService.getCost(localization);
        Ride ride = new Ride(customer, localization, RideStatus.PENDING, cost);
        rideRepository.save(ride);
        return ride.getId();
    }

    public int assignDriversToRides() {
        List<Ride> pendingRides = rideRepository.getPendingRides();
        List<Driver> availableDrivers = driverRepository.getAvailableDrivers();
        int numberOfRidesToFound = Math.min(pendingRides.size(), availableDrivers.size());
        List<Ride> ridesToAssign = pendingRides.subList(0, numberOfRidesToFound);
        for (int i = 0; i < numberOfRidesToFound; i++) {
            Ride ride = ridesToAssign.get(i);
            Driver driver = availableDrivers.get(i);
            ride.setStatus(RideStatus.FOUND);
            driver = driver.copyWith(DriverStatus.IN_RIDE);
            driverRepository.save(driver);
            ride.setDriver(driver);
            rideRepository.save(ride);


            rideRepository.invalidateCacheBatch(
                    ridesToAssign.stream().map(Ride::getId).toList()
            );

        }
        return numberOfRidesToFound;
    }
}
