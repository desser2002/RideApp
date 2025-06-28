package domen.rideapp.infrastructure.map;

import domen.rideapp.domain.model.GeoPoint;
import domen.rideapp.domain.model.Localization;
import domen.rideapp.infrastructure.GoogleMapsConfig;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GoogleMapsUrlBuilderTest {
    @Test
    void shouldBuildGoogleMapsUrl() {
        //given
        Localization localization = new Localization(new GeoPoint(52.5200, 13.4050), new GeoPoint(48.8566, 2.3522));
        String uri = "https://maps.googleapis.com/maps/api/distancematrix/json";
        String apiKey = "FAKE_API_KEY";
        GoogleMapsConfig config = new GoogleMapsConfig(uri, apiKey);
        GoogleMapsUrlBuilder builder = new GoogleMapsUrlBuilder(config);
        //when
        String resultUrl = builder.buildDistanceMatrixUrl(localization);
        //then
        String expectedUrl = uri +
                "?origins=" + localization.from().toString() +
                "&destinations=" + localization.to().toString() + "&key=" + apiKey;
        assertEquals(expectedUrl, resultUrl);
    }
}