package domen.rideapp.domain.repository;

import domen.rideapp.domain.model.PricingConfig;

import java.util.Optional;

public interface PricingRepository {
    Optional<PricingConfig> getDefaultConfig();
}
