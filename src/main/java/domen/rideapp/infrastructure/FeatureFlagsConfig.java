package domen.rideapp.infrastructure;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "feature")
public record FeatureFlagsConfig(boolean googleMapsEnabled) {}
