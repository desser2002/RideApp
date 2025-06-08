package domen.rideapp.api;

import domen.rideapp.api.request.AddDriverRequest;
import domen.rideapp.api.request.InitRideRequest;
import domen.rideapp.api.response.RideResponse;
import domen.rideapp.domain.model.Ride;
import domen.rideapp.domain.model.RideStatus;
import domen.rideapp.domain.repository.RideRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Comparator;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class RideControllerIntegrationTest {
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private RideRepository rideRepository;

    @BeforeEach
    void cleanUp() {
        rideRepository.clear();
    }

    @Test
    void shouldGetAllRides() {
        //given
        InitRideRequest request1 = new InitRideRequest("customer1", "from", "to");
        InitRideRequest request2 = new InitRideRequest("customer2", "from", "to");
        InitRideRequest request3 = new InitRideRequest("customer3", "from", "to");

        createRide(request1).expectStatus().isCreated();
        createRide(request2).expectStatus().isCreated();
        createRide(request3).expectStatus().isCreated();

        //when then
        webTestClient.get()
                .uri("/api/rides")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(RideResponse.class)
                .value(rideResponses -> {
                    rideResponses.sort(Comparator.comparing(RideResponse::customer));
                    Assertions.assertEquals(3, rideResponses.size());
                    Assertions.assertEquals(request1.customer(), rideResponses.get(0).customer());
                    Assertions.assertEquals(request2.customer(), rideResponses.get(1).customer());
                    Assertions.assertEquals(request3.customer(), rideResponses.get(2).customer());
                });
    }

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

    @Test
    void shouldAssignDriversToRide() {
        //given
        InitRideRequest initRideRequest1 = new InitRideRequest("customer1", "from", "to");
        InitRideRequest initRideRequest2 = new InitRideRequest("customer2", "from", "to");
        InitRideRequest initRideRequest3 = new InitRideRequest("customer3", "from", "to");

        createRide(initRideRequest1).expectStatus().isCreated();
        createRide(initRideRequest2).expectStatus().isCreated();
        createRide(initRideRequest3).expectStatus().isCreated();

        AddDriverRequest addDriverRequest1 = new AddDriverRequest("Jakub", "Nowacki");
        AddDriverRequest addDriverRequest2 = new AddDriverRequest("Nowak", "Lew");

        createDriver(addDriverRequest1).expectStatus().isCreated();
        createDriver(addDriverRequest2).expectStatus().isCreated();
        //when
        webTestClient.post()
                .uri("/api/rides/assign")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Integer.class)
                .value(response -> Assertions.assertEquals(2, response));
    }

    @Test
    void shouldReturnBadRequest() {
        //given
        InitRideRequest request = new InitRideRequest("", "", "");

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
}