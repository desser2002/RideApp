package domen.rideapp.infrastructure.pricing;

import domen.rideapp.domain.model.Localization;
import domen.rideapp.domain.model.PricingConfig;
import domen.rideapp.domain.model.RouteEstimate;
import domen.rideapp.domain.repository.PricingRepository;
import domen.rideapp.domain.service.MapService;
import domen.rideapp.domain.service.PricingService;

public class CustomPricingService implements PricingService {
    private final MapService mapService;
    private final PricingRepository pricingRepository;

    public CustomPricingService(MapService mapService, PricingRepository pricingRepository) {
        this.mapService = mapService;
        this.pricingRepository = pricingRepository;
    }

    private RouteEstimate getEstimateOrThrow(Localization localization) {
        return mapService
                .getRouteEstimate(localization)
                .orElseThrow(() -> new IllegalStateException("Failed to get route estimate from Google Maps"));
    }

    @Override
    public double getCost(Localization localization) {
        RouteEstimate estimate = getEstimateOrThrow(localization);

        PricingConfig config = pricingRepository.getDefaultConfig()
                .orElseThrow(() -> new IllegalStateException("Pricing config not found"));

        double cost = estimate.distanceKm() * config.pricePerKm()
                + estimate.durationMinutes() * config.pricePerMinute()
                + config.pickupFee();

        return Math.round(cost * 100.0) / 100.0;
    }
}
