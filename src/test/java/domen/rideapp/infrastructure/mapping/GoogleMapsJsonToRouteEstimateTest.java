package domen.rideapp.infrastructure.mapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import domen.rideapp.domain.model.RouteEstimate;
import domen.rideapp.infrastructure.exception.ExternalApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GoogleMapsJsonToRouteEstimateTest {
    private GoogleMapsJsonToRouteEstimate converter;

    @BeforeEach
    public void setUp() {
        converter = new GoogleMapsJsonToRouteEstimate(new ObjectMapper());
    }

    @Test
    void shouldReturnRouteEstimate() {
        //given
        int distance = 1500;
        int duration = 300;

        String googleMapsJson = buildGoogleMapsJson(distance, duration, "OK", "OK");
        //when
        RouteEstimate routeEstimate = converter.convert(googleMapsJson);
        //then
        assertNotNull(routeEstimate);
        assertEquals(distance, routeEstimate.distanceInMeters());
        assertEquals(duration, routeEstimate.durationInSeconds());
    }

    @Test
    void shouldHandleZeroValues() {
        //given
        String googleMapsJson = buildGoogleMapsJson(0, 0, "OK", "OK");
        //when
        RouteEstimate routeEstimate = converter.convert(googleMapsJson);
        //then
        assertNotNull(routeEstimate);
        assertEquals(0, routeEstimate.distanceInMeters());
        assertEquals(0, routeEstimate.durationInSeconds());
    }

    @Test
    void shouldThrowWhenElementStatusNotOk() {
        //given
        String googleMapsJson = buildGoogleMapsJson(1000, 60, "OK", "ZERO_RESULTS");
        //when then
        assertThrows(ExternalApiException.class, () -> converter.convert(googleMapsJson));
    }

    @Test
    void shouldThrowExceptionWhenGlobalStatusIsNotOk() {
        //given
        String googleMapsJson = buildGoogleMapsJson(1000, 60, "REQUEST_DENIED", "OK");
        //when then
        assertThrows(ExternalApiException.class, () -> converter.convert(googleMapsJson));
    }

    @Test
    void shouldThrowExceptionWhenRowsAreMissing() {
        //given
        String json = """
                {
                  "status": "OK"
                }
                """;

        //when then
        assertThrows(ExternalApiException.class, () -> converter.convert(json));
    }

    @Test
    void shouldThrowExceptionWhenElementsAreEmpty() {
        //given
        String json = """
                {
                  "status": "OK",
                  "rows": [
                    {
                      "elements": []
                    }
                  ]
                }
                """;

        //when then
        assertThrows(ExternalApiException.class, () -> converter.convert(json));
    }

    private String buildGoogleMapsJson(Integer distance, Integer duration, String globalStatus, String elementStatus) {
        StringBuilder sb = new StringBuilder();
        sb.append("{ \"status\": \"").append(globalStatus).append("\", ");
        sb.append("\"rows\": [ { \"elements\": [ { ");

        if (distance != null) {
            sb.append("\"distance\": { \"value\": ").append(distance).append(" }, ");
        }
        if (duration != null) {
            sb.append("\"duration\": { \"value\": ").append(duration).append(" }, ");
        }

        sb.append("\"status\": \"").append(elementStatus).append("\" } ] } ] }");
        return sb.toString();
    }
}


