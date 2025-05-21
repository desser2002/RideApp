package domen.rideapp.domain.service;
import domen.rideapp.domain.model.Ride;
import domen.rideapp.domain.model.RideStatus;
import domen.rideapp.domain.repository.DriverRepository;
import domen.rideapp.domain.repository.RideRepository;

public class RideService {
    private final PricingService pricingService;
    private final RideRepository rideRepository;
    private final DriverRepository driverRepository;

    public RideService(PricingService pricingService, RideRepository rideRepository, DriverRepository driverRepository) {
        this.pricingService = pricingService;
        this.rideRepository = rideRepository;
        this.driverRepository = driverRepository;
    }

    public void rideInitiation(String customer, String from, String to) {
        double cost = pricingService.getCost(from, to);
        Ride ride = new Ride(customer, new Localization(from, to), RideStatus.PENDING, cost);
        rideRepository.save(ride);
    }
}
