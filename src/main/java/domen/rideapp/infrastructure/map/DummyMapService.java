package domen.rideapp.infrastructure.map;

import domen.rideapp.domain.model.Localization;
import domen.rideapp.domain.model.RouteEstimate;
import domen.rideapp.infrastructure.mapping.MapService;

import java.util.Optional;

public class DummyMapService implements MapService {
    @Override
    public Optional<RouteEstimate> getRouteEstimate(Localization localization) {
        return Optional.of(new RouteEstimate(35000, 2200));
    }
}
