package domen.rideapp.infrastructure.pricing;

import domen.rideapp.domain.model.Localization;
import domen.rideapp.domain.model.Price;
import domen.rideapp.domain.model.PricingConfig;
import domen.rideapp.domain.model.RouteEstimate;
import domen.rideapp.infrastructure.mapping.MapService;
import org.junit.jupiter.api.Test;

import java.util.Currency;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CustomPricingServiceTest {
    @Test
    void shouldCalculateCorrectCost() {
        // given
        MapService mapService = mock(MapService.class);

        Localization loc = mock(Localization.class);

        RouteEstimate estimate = new RouteEstimate(100.0, 60);
        PricingConfig config = new PricingConfig(2.0, 1.0, 5.0, Currency.getInstance("PLN"));

        when(mapService.getRouteEstimate(loc)).thenReturn(Optional.of(estimate));

        CustomPricingService pricingService = new CustomPricingService(mapService, config);
        //when
        Price price = pricingService.getCost(loc);
        //then
        double expectedCost = estimate.distanceInMeters() * config.pricePerKm() / 1000
                + estimate.durationInSeconds() * config.pricePerMinute() / 60
                + config.pickupFee();
        assertEquals(Math.round(expectedCost * 100.0) / 100.0, price.amount());
    }
}