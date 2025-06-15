package domen.rideapp.infrastructure.pricing;

import domen.rideapp.domain.model.Localization;
import domen.rideapp.domain.model.PricingConfig;
import domen.rideapp.domain.model.RouteEstimate;
import domen.rideapp.domain.repository.PricingRepository;
import domen.rideapp.domain.service.MapService;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CustomPricingServiceTest {
    @Test
    void shouldCalculateCorrectCost() {
        // given
        MapService mapService = mock(MapService.class);
        PricingRepository pricingRepository = mock(PricingRepository.class);
        Localization loc = mock(Localization.class);

        RouteEstimate estimate = new RouteEstimate(100.0, 60);
        PricingConfig config = new PricingConfig(2.0, 1.0, 5.0);

        when(mapService.getRouteEstimate(loc)).thenReturn(Optional.of(estimate));
        when(pricingRepository.getDefaultConfig()).thenReturn(Optional.of(config));
        CustomPricingService pricingService = new CustomPricingService(mapService, pricingRepository);
        //when
        double cost = pricingService.getCost(loc);
        //then
        double expectedCost = estimate.distanceKm() * config.pricePerKm()
                + estimate.durationMinutes() * config.pricePerMinute()
                + config.pickupFee();
        assertEquals(Math.round(expectedCost * 100.0) / 100.0, cost);
    }
}