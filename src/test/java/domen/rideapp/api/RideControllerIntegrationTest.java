package domen.rideapp.api;

import domen.rideapp.api.request.InitRideRequest;
import domen.rideapp.domain.model.Ride;
import domen.rideapp.domain.model.RideStatus;
import domen.rideapp.domain.repository.RideRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class RideControllerIntegrationTest {
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private RideRepository rideRepository;

    @Test
    void shouldInitRide() {
        //given
        InitRideRequest request = new InitRideRequest("customer1", "from", "to");
        //when then
        createRide(request).expectStatus().isCreated();
        List<Ride> rides = rideRepository.getAllRides();
        Assertions.assertEquals(1, rides.size());
        Assertions.assertEquals(request.customer(), rides.getFirst().getCustomer());
        Assertions.assertEquals(request.from(), rides.getFirst().getLocalization().from());
        Assertions.assertEquals(request.to(), rides.getFirst().getLocalization().to());
        Assertions.assertEquals(RideStatus.PENDING, rides.getFirst().getStatus());
    }

    private WebTestClient.ResponseSpec createRide(InitRideRequest request) {
        return webTestClient.post()
                .uri("/api/rides/init")
                .bodyValue(request)
                .exchange();
    }
}