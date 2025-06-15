package domen.rideapp.infrastructure.repository;

import domen.rideapp.domain.model.PricingConfig;
import domen.rideapp.domain.repository.PricingRepository;

import java.util.Optional;

public class InMemoryPricingConfigRepository implements PricingRepository {
    @Override
    public Optional<PricingConfig> getDefaultConfig() {
        return Optional.of(new PricingConfig(
                1.5,
                0.5,
                2.0));
    }
}
