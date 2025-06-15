package domen.rideapp.infrastructure.map;

import domen.rideapp.domain.model.GeoPoint;
import domen.rideapp.domain.model.Localization;
import domen.rideapp.domain.model.RouteEstimate;
import domen.rideapp.domain.service.MapService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@SpringBootTest
@EnableConfigurationProperties
@ImportAutoConfiguration // гарантирует загрузку ConversionService и бин-конфигураций
@Import(domen.rideapp.infrastructure.AppConfig.class)
class GoogleMapsServiceIntegrationTest {
    @Autowired
    private MapService mapService;

    @Autowired
    private Environment environment;

    private Localization localization;
    @BeforeEach
    void setUp() {
        localization = new Localization(
                new GeoPoint(52.2297, 21.0122),
                new GeoPoint(50.0647, 19.9450)
        );
    }

    @Test
    void shouldGetEstimateFromGoogleMaps_whenApiKeyConfigured() {
        String apiKey = environment.getProperty("google.maps.api-key");
        assumeTrue(apiKey != null && !apiKey.isBlank(), "API key is missing. Skipping test.");

        Optional<RouteEstimate> result = mapService.getRouteEstimate(localization);

        assertTrue(result.isPresent(), "RouteEstimate should be present");
        assertTrue(result.get().distanceKm() > 0, "Distance should be > 0");
        assertTrue(result.get().durationMinutes() > 0, "Duration should be > 0");
    }
}