package domen.rideapp;

import com.github.tomakehurst.wiremock.WireMockServer;
import domen.rideapp.infrastructure.GoogleMapsConfig;
import domen.rideapp.infrastructure.map.GoogleMapsClient;
import domen.rideapp.infrastructure.map.GoogleMapsUrlBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.net.http.HttpClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@TestConfiguration
public class GoogleMapsWireMockTestConfig {
    @Bean(initMethod = "start", destroyMethod = "stop")
    public WireMockServer wireMockServer() {
        return new WireMockServer(0);
    }

    @Bean
    public Void googleMapsStubInitializer(WireMockServer server) {
        server.stubFor(get(urlPathEqualTo("/maps/api/distancematrix/json"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {
                                  "destination_addresses": ["Krakow, Poland"],
                                  "origin_addresses": ["Warsaw, Poland"],
                                  "rows": [
                                    {
                                      "elements": [
                                        {
                                          "distance": {
                                            "text": "295 km",
                                            "value": 295000
                                          },
                                          "duration": {
                                            "text": "4 hours",
                                            "value": 14400
                                          },
                                          "status": "OK"
                                        }
                                      ]
                                    }
                                  ],
                                  "status": "OK"
                                }
                                """)));
        return null;
    }

    @Bean
    @Primary
    public GoogleMapsConfig testGoogleMapsConfig(WireMockServer server) {
        return new GoogleMapsConfig(
                server.baseUrl() + "/maps/api/distancematrix/json",
                "test-api-key"
        );
    }

    @Bean
    @Primary
    public GoogleMapsUrlBuilder testGoogleMapsUrlBuilder(GoogleMapsConfig config) {
        return new GoogleMapsUrlBuilder(config);
    }

    @Bean
    @Primary
    public GoogleMapsClient testGoogleMapsClient(GoogleMapsUrlBuilder urlBuilder) {
        return new GoogleMapsClient(urlBuilder, HttpClient.newHttpClient());
    }
}