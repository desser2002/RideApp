package domen.rideapp.infrastructure.map;

import domen.rideapp.domain.model.GeoPoint;
import domen.rideapp.domain.model.Localization;
import domen.rideapp.infrastructure.GoogleMapsConfig;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GoogleMapsUrlBuilderTest {
    @Test
    void shouldBuildGoogleMapsUrl() {
        //given
        GoogleMapsConfig config = mock(GoogleMapsConfig.class);
        Localization localization = new Localization(new GeoPoint(52.5200, 13.4050), new GeoPoint(48.8566, 2.3522));
        when(config.getUri()).thenReturn("https://maps.googleapis.com/maps/api/distancematrix/json");
        when(config.getApiKey()).thenReturn("FAKE_API_KEY");

        GoogleMapsUrlBuilder builder = new GoogleMapsUrlBuilder(config);

        //when
        String resultUrl = builder.buildDistanceMatrixUrl(localization);
        //then
        String expectedUrl = "https://maps.googleapis.com/maps/api/distancematrix/json" +
                "?origins=52.5200,13.4050&destinations=48.8566,2.3522&key=FAKE_API_KEY";

        assertEquals(expectedUrl, resultUrl);
    }
}