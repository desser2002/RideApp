package domen.rideapp.domain.model;

public record PricingConfig(double pricePerKm,
                            double pricePerMinute,
                            double pickupFee) {
}
