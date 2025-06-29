package domen.rideapp.domain.model;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Currency;

@ConfigurationProperties(prefix = "pricing")
public record PricingConfig(double pricePerKm,
                            double pricePerMinute,
                            double pickupFee,
                            Currency currency) {
}
