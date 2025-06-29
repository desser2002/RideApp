package domen.rideapp.infrastructure.pricing;

import domen.rideapp.GoogleMapsWireMockTestConfig;
import domen.rideapp.domain.model.GeoPoint;
import domen.rideapp.domain.model.Localization;
import domen.rideapp.domain.model.Price;
import domen.rideapp.domain.service.PricingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Import(GoogleMapsWireMockTestConfig.class)
class CustomPricingServiceIntegrationTest {
    @Autowired
    private PricingService pricingService;

    @Test
    void shouldCalculatePricing() {
        //given
        Localization localization = new Localization(
                new GeoPoint(52.2297, 21.0122),
                new GeoPoint(50.0647, 19.9450)
        );
        //when
        Price price = pricingService.getCost(localization);
        //then
        assertTrue(price.amount() > 0);
    }
}