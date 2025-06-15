package domen.rideapp.infrastructure.pricing;

import domen.rideapp.domain.model.Localization;
import domen.rideapp.domain.model.RouteEstimate;
import domen.rideapp.domain.service.MapService;
import domen.rideapp.domain.service.PricingService;

import java.sql.Time;

public class CustomPricingService implements PricingService {
    private double distance;
    private Time time;
    private double surgeMultiplier;
    private double pickupFee;
    private double destinationFee;
    private final MapService mapService;

    public CustomPricingService(MapService mapService) {
        this.mapService = mapService;
    }

    private double getDistance(Localization localization) {
        return getEstimateOrThrow(localization).distanceKm();
    }

    private RouteEstimate getEstimateOrThrow(Localization localization) {
        return mapService
                .getRouteEstimate(localization)
                .orElseThrow(() -> new IllegalStateException("Failed to get route estimate from Google Maps"));
    }

    @Override
    public double getCost(Localization localization) {
        return 0;
    }
}
