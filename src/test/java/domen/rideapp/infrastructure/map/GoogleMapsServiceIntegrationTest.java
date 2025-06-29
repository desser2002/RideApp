package domen.rideapp.infrastructure.map;

import domen.rideapp.GoogleMapsWireMockTestConfig;
import domen.rideapp.domain.model.GeoPoint;
import domen.rideapp.domain.model.Localization;
import domen.rideapp.domain.model.RouteEstimate;
import domen.rideapp.infrastructure.mapping.MapService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Import(GoogleMapsWireMockTestConfig.class)
class GoogleMapsServiceIntegrationTest {
    @Autowired
    private MapService mapService;
    private Localization localization;

    @BeforeEach
    void setUp() {
        localization = new Localization(
                new GeoPoint(52.2297, 21.0122),
                new GeoPoint(50.0647, 19.9450)
        );
    }

    @Test
    void shouldGetEstimateFromGoogleMaps() {
        Optional<RouteEstimate> result = mapService.getRouteEstimate(localization);

        assertTrue(result.isPresent(), "RouteEstimate should be present");
        assertTrue(result.get().distanceInMeters() > 0, "Distance should be > 0");
        assertTrue(result.get().durationInSeconds() > 0, "Duration should be > 0");
    }
}