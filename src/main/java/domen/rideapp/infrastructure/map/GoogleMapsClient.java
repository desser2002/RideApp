package domen.rideapp.infrastructure.map;

import domen.rideapp.domain.model.Localization;
import domen.rideapp.domain.service.ExternalApiException;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class GoogleMapsClient {
    private final GoogleMapsUrlBuilder urlBuilder;
    private final HttpClient httpClient;

    public GoogleMapsClient(GoogleMapsUrlBuilder urlBuilder, HttpClient httpClient) {
        this.urlBuilder = urlBuilder;
        this.httpClient = httpClient;
    }

    public String fetchDistanceMatrixJson(Localization localization) throws ExternalApiException {
        String url = urlBuilder.buildDistanceMatrixUrl(localization);
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();
            System.out.println(request);
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            throw new ExternalApiException(e.getMessage(), e);
        }
    }
}
