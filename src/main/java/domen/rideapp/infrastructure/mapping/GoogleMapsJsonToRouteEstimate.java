package domen.rideapp.infrastructure.mapping;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import domen.rideapp.domain.model.RouteEstimate;
import domen.rideapp.domain.service.ExternalApiException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class GoogleMapsJsonToRouteEstimate implements Converter<String, RouteEstimate> {
    private final ObjectMapper mapper;

    public GoogleMapsJsonToRouteEstimate(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public RouteEstimate convert(String json) {
        try {
            JsonNode root = mapper.readTree(json);
            JsonNode element = root.path("rows").get(0).path("elements").get(0);

            int distanceInMeters = element.path("distance").path("value").asInt();
            int durationInSeconds = element.path("duration").path("value").asInt();

            double distanceKm = distanceInMeters / 1000.0;
            int durationMinutes = durationInSeconds / 60;

            return new RouteEstimate(distanceKm, durationMinutes);
        } catch (Exception e) {
            throw new ExternalApiException("Failed to parse route estimate from Google response", e);
        }
    }
}
