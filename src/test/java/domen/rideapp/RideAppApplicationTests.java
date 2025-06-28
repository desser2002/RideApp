package domen.rideapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(GoogleMapsWireMockTestConfig.class)
class RideAppApplicationTests {
    @Test
    void contextLoads() {
    }
}
