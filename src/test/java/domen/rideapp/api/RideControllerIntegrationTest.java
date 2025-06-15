package domen.rideapp.api;

import domen.rideapp.api.request.AddDriverRequest;
import domen.rideapp.api.request.InitRideRequest;
import domen.rideapp.api.response.RideResponse;
import domen.rideapp.domain.model.*;
import domen.rideapp.domain.repository.DriverRepository;
import domen.rideapp.domain.repository.PricingRepository;
import domen.rideapp.domain.repository.RideRepository;
import domen.rideapp.domain.service.MapService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class RideControllerIntegrationTest {
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private RideRepository rideRepository;
    @Autowired
    private DriverRepository driverRepository;

    @BeforeEach
    void cleanUp() {
        rideRepository.clear();
        driverRepository.clear();
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        public MapService mapService() {
            return localization -> Optional.of(new RouteEstimate(10.0, 15));
        }

        @Bean
        public PricingRepository pricingRepository() {
            return () -> Optional.of(new PricingConfig(2.0, 1.0, 5.0));
        }
    }

    @Test
    void shouldGetAllRides() {
        //given
        List<InitRideRequest> requests = List.of(
                new InitRideRequest("customer1", new GeoPoint(52.2297, 21.0122), new GeoPoint(50.0647, 19.9450)),
                new InitRideRequest("customer2", new GeoPoint(52.2297, 21.0122), new GeoPoint(50.0647, 19.9450)),
                new InitRideRequest("customer3", new GeoPoint(52.2297, 21.0122), new GeoPoint(50.0647, 19.9450))
        );

        requests.forEach(request -> createRide(request).expectStatus().isCreated());
        //when then
        getAllRides().expectStatus().isOk()
                .expectBodyList(RideResponse.class)
                .value(rideResponses -> {
                    rideResponses.sort(Comparator.comparing(RideResponse::customer));
                    Assertions.assertEquals(3, rideResponses.size());
                });
    }

    @Test
    void shouldInitRide() {
        //given
        InitRideRequest request = new InitRideRequest(
                "customer1",
                new GeoPoint(52.2297, 21.0122),
                new GeoPoint(50.0647, 19.9450)
        );
        //when then
        createRide(request).expectStatus().isCreated();
        List<Ride> rides = rideRepository.getAllRides();
        Assertions.assertEquals(1, rides.size());
        Assertions.assertEquals(request.customer(), rides.getFirst().getCustomer());
        Assertions.assertEquals(request.from(), rides.getFirst().getLocalization().from());
        Assertions.assertEquals(request.to(), rides.getFirst().getLocalization().to());
        Assertions.assertEquals(RideStatus.PENDING, rides.getFirst().getStatus());
    }

    @Test
    void shouldAssignDriversToRide() {
        //given
        List<InitRideRequest> requests = List.of(
                new InitRideRequest("customer1", new GeoPoint(52.2297, 21.0122), new GeoPoint(50.0647, 19.9450)),
                new InitRideRequest("customer2", new GeoPoint(52.2297, 21.0122), new GeoPoint(50.0647, 19.9450)),
                new InitRideRequest("customer3", new GeoPoint(52.2297, 21.0122), new GeoPoint(50.0647, 19.9450))
        );

        requests.forEach(request -> createRide(request).expectStatus().isCreated());

        AddDriverRequest addDriverRequest1 = new AddDriverRequest("Jakub", "Nowacki");
        AddDriverRequest addDriverRequest2 = new AddDriverRequest("Nowak", "Lew");

        createDriver(addDriverRequest1).expectStatus().isCreated();
        createDriver(addDriverRequest2).expectStatus().isCreated();
        //when

        assignRides().expectStatus().isOk()
                .expectBody(Integer.class)
                .value(response -> Assertions.assertEquals(2, response));
    }

    @Test
    void shouldReturnBadRequest() {
        //given
        InitRideRequest request = new InitRideRequest("",
                new GeoPoint(52.2297, 21.0122), new GeoPoint(50.0647, 19.9450));
        //when then
        createRide(request).expectStatus().isBadRequest();
    }

    private WebTestClient.ResponseSpec createRide(InitRideRequest request) {
        return webTestClient.post()
                .uri("/api/rides/init")
                .bodyValue(request)
                .exchange();
    }

    private WebTestClient.ResponseSpec createDriver(AddDriverRequest request) {
        return webTestClient.post()
                .uri("/api/drivers/add")
                .bodyValue(request)
                .exchange();
    }

    private WebTestClient.ResponseSpec getAllRides() {
        return webTestClient.get()
                .uri("/api/rides")
                .exchange();
    }

    private WebTestClient.ResponseSpec assignRides() {
        return webTestClient.post()
                .uri("/api/rides/assign")
                .exchange();
    }
}