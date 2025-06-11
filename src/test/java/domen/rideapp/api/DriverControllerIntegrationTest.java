package domen.rideapp.api;

import domen.rideapp.api.request.AddDriverRequest;
import domen.rideapp.api.request.UpdateDriverRequest;
import domen.rideapp.api.request.UpdateDriverStatusRequest;
import domen.rideapp.api.response.DriverResponse;
import domen.rideapp.domain.model.Driver;
import domen.rideapp.domain.model.DriverStatus;
import domen.rideapp.domain.repository.DriverRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class DriverControllerIntegrationTest {
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private DriverRepository driverRepository;

    @BeforeEach
    void cleanUp() {
        driverRepository.clear();
    }

    @Test
    void shouldAddDriver() {
        //given
        AddDriverRequest request = new AddDriverRequest("Firstname", "Lastname");

        //when then
        createDriver(request).expectStatus().isCreated();
        List<Driver> drivers = driverRepository.getAll();
        Assertions.assertEquals(1, drivers.size());
        Assertions.assertEquals(request.firstName(), drivers.getFirst().firstName());
        Assertions.assertEquals(request.lastName(), drivers.getLast().lastName());
    }

    @Test
    void shouldGetAllDrivers() {
        //given
        List<AddDriverRequest> requests = List.of(
                new AddDriverRequest("Firstname", "Lastname"),
                new AddDriverRequest("Firstname2", "Lastname2"),
                new AddDriverRequest("Firstname3", "Lastname3")
        );
        requests.forEach(request -> createDriver(request).expectStatus().isCreated());
        //when then
        getAllDrivers()
                .expectStatus().isOk()
                .expectBodyList(DriverResponse.class)
                .hasSize(3);
    }

    @Test
    void shouldGetDriverById() {
        //given
        AddDriverRequest request1 = new AddDriverRequest("Firstname", "Lastname");
        AddDriverRequest request2 = new AddDriverRequest("Firstname2", "Lastname2");

        createDriver(request1).expectStatus().isCreated();
        DriverResponse driverResponse = createDriverAndReturn(request2);

        Assertions.assertNotNull(driverResponse);

        //when then
        getDriverById(driverResponse.id())
                .expectStatus().isOk()
                .expectBody(DriverResponse.class)
                .value(driver -> {
                    Assertions.assertEquals(request2.firstName(), driver.firstName());
                    Assertions.assertEquals(request2.lastName(), driver.lastName());
                });
    }

    @Test
    void shouldDeleteDriver() {
        //given
        AddDriverRequest request1 = new AddDriverRequest("Firstname", "Lastname");
        AddDriverRequest request2 = new AddDriverRequest("Firstname2", "Lastname2");

        createDriver(request1).expectStatus().isCreated();
        DriverResponse driverResponse = createDriverAndReturn(request2);
        Assertions.assertNotNull(driverResponse);
        //when then
        deleteDriverById(driverResponse.id());
        getAllDrivers()
                .expectBodyList(DriverResponse.class)
                .hasSize(1);
    }

    @Test
    void shouldUpdateDriver() {
        //given
        AddDriverRequest addDriverRequest = new AddDriverRequest("Firstname", "Lastname");
        DriverResponse driverResponse = createDriverAndReturn(addDriverRequest);
        Assertions.assertNotNull(driverResponse);
        UpdateDriverRequest updateDriverRequest =
                new UpdateDriverRequest("New Firstname", "New Lastname", DriverStatus.IN_RIDE.toString());
        // when then
        updateDriver(driverResponse.id(), updateDriverRequest);
        getDriverById(driverResponse.id())
                .expectStatus().isOk()
                .expectBody(DriverResponse.class)
                .value(driver -> {
                    Assertions.assertEquals(updateDriverRequest.firstName(), driver.firstName());
                    Assertions.assertEquals(updateDriverRequest.lastName(), driver.lastName());
                    Assertions.assertEquals(updateDriverRequest.driverStatus(), driver.driverStatus());
                });
    }

    @Test
    void shouldUpdateDriverStatus() {
        //given
        AddDriverRequest addDriverRequest = new AddDriverRequest("Firstname", "Lastname");
        DriverResponse driverResponse = createDriverAndReturn(addDriverRequest);
        Assertions.assertNotNull(driverResponse);
        //when then
        UpdateDriverStatusRequest updateDriverStatusRequest =
                new UpdateDriverStatusRequest(DriverStatus.IN_RIDE.toString());
        updateDriverStatus(driverResponse.id(), updateDriverStatusRequest);
        getDriverById(driverResponse.id())
                .expectStatus().isOk()
                .expectBody(DriverResponse.class)
                .value(driver ->
                        Assertions.assertEquals(updateDriverStatusRequest.driverStatus(), driver.driverStatus()));
    }

    private WebTestClient.ResponseSpec createDriver(AddDriverRequest request) {
        return webTestClient.post()
                .uri("/api/drivers/add")
                .bodyValue(request)
                .exchange();
    }

    private DriverResponse createDriverAndReturn(AddDriverRequest request) {
        return createDriver(request)
                .expectStatus().isCreated()
                .expectBody(DriverResponse.class)
                .returnResult()
                .getResponseBody();
    }

    private WebTestClient.ResponseSpec getDriverById(String id) {
        return webTestClient.get().uri("/api/drivers/" + id)
                .exchange();
    }

    private void deleteDriverById(String id) {
        webTestClient.delete()
                .uri("/api/drivers/" + id)
                .exchange();
    }

    private WebTestClient.ResponseSpec getAllDrivers() {
        return webTestClient.get().uri("/api/drivers")
                .exchange();
    }

    private void updateDriver(String id, UpdateDriverRequest request) {
        webTestClient.put()
                .uri("/api/drivers/" + id)
                .bodyValue(request)
                .exchange();
    }

    private void updateDriverStatus(String id, UpdateDriverStatusRequest request) {
        webTestClient.patch()
                .uri("/api/drivers/" + id + "/status")
                .bodyValue(request)
                .exchange();
    }
}