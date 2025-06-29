package domen.rideapp.infrastructure.mapping;

import domen.rideapp.domain.model.Localization;
import domen.rideapp.domain.model.RouteEstimate;

import java.util.Optional;

public interface MapService {
    Optional<RouteEstimate> getRouteEstimate(Localization localization);
}
