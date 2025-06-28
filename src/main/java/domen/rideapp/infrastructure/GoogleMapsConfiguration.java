package domen.rideapp.infrastructure;

import domen.rideapp.infrastructure.map.GoogleMapsClient;
import domen.rideapp.infrastructure.map.GoogleMapsService;
import domen.rideapp.infrastructure.map.GoogleMapsUrlBuilder;
import domen.rideapp.infrastructure.mapping.MapService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;

import java.net.http.HttpClient;

@Configuration
public class GoogleMapsConfiguration {
    @Bean
    HttpClient httpClient() {
        return HttpClient.newHttpClient();
    }

    @Bean
    GoogleMapsClient googleMapsClient(GoogleMapsUrlBuilder urlBuilder, HttpClient httpClient) {
        return new GoogleMapsClient(urlBuilder, httpClient);
    }

    @Bean
    GoogleMapsUrlBuilder googleMapsUrlBuilder(GoogleMapsConfig config) {
        return new GoogleMapsUrlBuilder(config);
    }

    @Bean
    MapService googleMapsServiceProd(GoogleMapsClient client,
                                     ConversionService conversionService,
                                     GoogleMapsConfig config) {
        if (config.apiKey() == null || config.apiKey().isBlank()) {
            throw new IllegalStateException("Google Maps API key must be set in production.");
        }
        return new GoogleMapsService(client, conversionService);
    }
}
