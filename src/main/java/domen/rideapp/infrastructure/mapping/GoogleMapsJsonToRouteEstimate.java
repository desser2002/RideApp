package domen.rideapp.infrastructure.mapping;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import domen.rideapp.domain.model.RouteEstimate;
import domen.rideapp.infrastructure.exception.ExternalApiException;
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
            String globalStatus = root.path("status").asText();
            if (!"OK".equals(globalStatus)) {
                throw new ExternalApiException("Google Maps global response status not OK: " + globalStatus);
            }
            JsonNode element = root.path("rows").get(0).path("elements").get(0);

            String elementStatus = element.path("status").asText();
            if (!"OK".equals(elementStatus)) {
                throw new ExternalApiException("Google Maps element status not OK: " + elementStatus);
            }

            int distanceInMeters = element.path("distance").path("value").asInt();
            int durationInSeconds = element.path("duration").path("value").asInt();

            return new RouteEstimate(distanceInMeters, durationInSeconds);
        } catch (ExternalApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ExternalApiException("Failed to parse route estimate from Google response", e);
        }
    }
}
