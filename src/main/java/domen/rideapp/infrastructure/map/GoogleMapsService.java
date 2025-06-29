package domen.rideapp.infrastructure.map;

import domen.rideapp.domain.model.Localization;
import domen.rideapp.domain.model.RouteEstimate;
import domen.rideapp.infrastructure.exception.ExternalApiException;
import domen.rideapp.infrastructure.mapping.MapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;

import java.util.Optional;

public class GoogleMapsService implements MapService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GoogleMapsService.class);
    private final GoogleMapsClient client;
    private final ConversionService conversionService;

    public GoogleMapsService(GoogleMapsClient client, ConversionService conversionService) {
        this.client = client;
        this.conversionService = conversionService;
    }

    public Optional<RouteEstimate> getRouteEstimate(Localization localization) {
        try {
            String json = client.fetchDistanceMatrixJson(localization);
            RouteEstimate estimate = conversionService.convert(json, RouteEstimate.class);
            if (estimate == null) {
                throw new IllegalStateException("ConversionService returned null for valid JSON");
            }
            return Optional.of(estimate);
        } catch (ExternalApiException e) {
            LOGGER.error(e.getMessage(), e);
            return Optional.empty();
        }
    }
}
