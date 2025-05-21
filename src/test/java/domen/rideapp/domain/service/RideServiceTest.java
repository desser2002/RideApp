package domen.rideapp.domain.service;
import domen.rideapp.domain.model.Ride;
import domen.rideapp.domain.repository.DriverRepository;
import domen.rideapp.domain.repository.RideRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RideServiceTest {
    @Mock
    private PricingService pricingService;
    @Mock
    private RideRepository rideRepository;
    @Mock
    private DriverRepository driverRepository;
    @InjectMocks
    private RideService rideService;

    @Test
    void shouldAddRide() {
        //given
        String customer = "Customer";
        String from = "First city";
        String to = "Second city";
        //when
        rideService.rideInitiation(customer,from, to);
        //then
        ArgumentCaptor<Ride> argumentCaptor = ArgumentCaptor.forClass(Ride.class);
        verify(rideRepository).save(argumentCaptor.capture());
        Ride ride = argumentCaptor.getValue();
        assertEquals(customer, ride.getCustomer());
        assertEquals(from, ride.getLocalization().from());
        assertEquals(to, ride.getLocalization().to());
        assertNull(ride.getDriver());
        verify(pricingService).getCost(from, to);
    }
}