package domen.rideapp.infrastructure.pricing;

import domen.rideapp.domain.model.Localization;
import domen.rideapp.domain.model.Price;
import domen.rideapp.domain.model.PricingConfig;
import domen.rideapp.domain.model.RouteEstimate;
import domen.rideapp.domain.service.PricingService;
import domen.rideapp.infrastructure.mapping.MapService;

public class CustomPricingService implements PricingService {
    private final MapService mapService;
    private final PricingConfig pricingConfig;

    public CustomPricingService(MapService mapService, PricingConfig pricingConfig) {
        this.mapService = mapService;
        this.pricingConfig = pricingConfig;
    }

    @Override
    public Price getCost(Localization localization) {
        RouteEstimate estimate = mapService
                .getRouteEstimate(localization)
                .orElseThrow(() -> new IllegalStateException("Failed to get route estimate from Google Maps"));

        double cost = estimate.distanceInMeters() * pricingConfig.pricePerKm() / 1000 +
                estimate.durationInSeconds() * pricingConfig.pricePerMinute() / 60
                + pricingConfig.pickupFee();

        double roundedCost = Math.round(cost * 100.0) / 100.0;

        return new Price(roundedCost, pricingConfig.currency());
    }
}
