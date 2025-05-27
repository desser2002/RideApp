package domen.rideapp.domain.service;

import domen.rideapp.domain.model.Driver;
import domen.rideapp.domain.model.DriverStatus;
import domen.rideapp.domain.model.Ride;
import domen.rideapp.domain.model.RideStatus;
import domen.rideapp.domain.repository.DriverRepository;
import domen.rideapp.domain.repository.RideRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        rideService.rideInitiation(customer, from, to);
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

    @Test
    void shouldAssignRidesWhenRidesAmountIsMoreThenDrivers() {
        //given
        Ride ride1 = spy(new Ride("Customer1", new Localization("from", "to"), RideStatus.PENDING, 12.01));
        Ride ride2 = spy(new Ride("Customer2", new Localization("from", "to"), RideStatus.PENDING, 12.02));
        Ride ride3 = spy(new Ride("Customer3", new Localization("from", "to"), RideStatus.PENDING, 12.03));
        List<Ride> rides = new ArrayList<>(List.of(ride1, ride2, ride3));
        Driver driver1 = spy(new Driver(UUID.randomUUID().toString(), "Jon", "Latitude", DriverStatus.AVAILABLE));
        Driver driver2 = spy(new Driver(UUID.randomUUID().toString(), "Bob", "Latitude", DriverStatus.AVAILABLE));
        List<Driver> drivers = new ArrayList<>(List.of(driver1, driver2));
        when(rideRepository.getPendingRides()).thenReturn(rides);
        when(driverRepository.getAvailableDrivers()).thenReturn(drivers);
        ///when
        rideService.assignDriversToRides();
        //then
        verify(ride1).setStatus(RideStatus.FOUND);
        verify(ride2).setStatus(RideStatus.FOUND);
        verify(ride3, never()).setStatus(RideStatus.FOUND);
        verify(driver1).copyWith(DriverStatus.IN_RIDE);
        verify(driver2).copyWith(DriverStatus.IN_RIDE);
        verify(driverRepository, times(2)).save(any());
        verify(rideRepository, times(2)).save(any());
    }

    @Test
    void shouldAssignRidesWhenRidesAmountIsLessThenDrivers() {
        //given
        Ride ride1 = spy(new Ride("Customer1", new Localization("from", "to"), RideStatus.PENDING, 12.01));
        Ride ride2 = spy(new Ride("Customer2", new Localization("from", "to"), RideStatus.PENDING, 12.02));
        List<Ride> rides = new ArrayList<>(List.of(ride1, ride2));
        Driver driver1 = spy(new Driver(UUID.randomUUID().toString(), "Jon", "Latitude", DriverStatus.AVAILABLE));
        Driver driver2 = spy(new Driver(UUID.randomUUID().toString(), "Bob", "Latitude", DriverStatus.AVAILABLE));
        Driver driver3 = spy(new Driver(UUID.randomUUID().toString(), "Yann", "Kowalski", DriverStatus.AVAILABLE));
        List<Driver> drivers = new ArrayList<>(List.of(driver1, driver2, driver3));
        when(rideRepository.getPendingRides()).thenReturn(rides);
        when(driverRepository.getAvailableDrivers()).thenReturn(drivers);
        ///when
        rideService.assignDriversToRides();
        //then
        verify(ride1).setStatus(RideStatus.FOUND);
        verify(ride2).setStatus(RideStatus.FOUND);
        verify(driver1).copyWith(DriverStatus.IN_RIDE);
        verify(driver2).copyWith(DriverStatus.IN_RIDE);
        verify(driver3, never()).copyWith(DriverStatus.IN_RIDE);
        verify(driverRepository, times(2)).save(any());
        verify(rideRepository, times(2)).save(any());
    }

    @Test
    void shouldAssignRidesWhenRidesAmountIsEqualsThenDrivers() {
        //given
        Ride ride1 = spy(new Ride("Customer1", new Localization("from", "to"), RideStatus.PENDING, 12.01));
        Ride ride2 = spy(new Ride("Customer2", new Localization("from", "to"), RideStatus.PENDING, 12.02));
        Ride ride3 = spy(new Ride("Customer3", new Localization("from", "to"), RideStatus.PENDING, 12.03));
        List<Ride> rides = new ArrayList<>(List.of(ride1, ride2, ride3));
        Driver driver1 = spy(new Driver(UUID.randomUUID().toString(), "Jon", "Latitude", DriverStatus.AVAILABLE));
        Driver driver2 = spy(new Driver(UUID.randomUUID().toString(), "Bob", "Latitude", DriverStatus.AVAILABLE));
        Driver driver3 = spy(new Driver(UUID.randomUUID().toString(), "Yann", "Kowalski", DriverStatus.AVAILABLE));
        List<Driver> drivers = new ArrayList<>(List.of(driver1, driver2, driver3));
        when(rideRepository.getPendingRides()).thenReturn(rides);
        when(driverRepository.getAvailableDrivers()).thenReturn(drivers);
        ///when
        rideService.assignDriversToRides();
        //then
        verify(ride1).setStatus(RideStatus.FOUND);
        verify(ride2).setStatus(RideStatus.FOUND);
        verify(ride3).setStatus(RideStatus.FOUND);
        verify(driver1).copyWith(DriverStatus.IN_RIDE);
        verify(driver2).copyWith(DriverStatus.IN_RIDE);
        verify(driver3).copyWith(DriverStatus.IN_RIDE);
        verify(driverRepository, times(3)).save(any());
        verify(rideRepository, times(3)).save(any());
    }

    @Test
    void shouldAssignRidesWhenRidesAmountIsZero() {
        //given
        List<Ride> rides = new ArrayList<>(List.of());
        Driver driver1 = spy(new Driver(UUID.randomUUID().toString(), "Jon", "Latitude", DriverStatus.AVAILABLE));
        Driver driver2 = spy(new Driver(UUID.randomUUID().toString(), "Bob", "Latitude", DriverStatus.AVAILABLE));
        Driver driver3 = spy(new Driver(UUID.randomUUID().toString(), "Yan", "Kowalski", DriverStatus.AVAILABLE));
        List<Driver> drivers = new ArrayList<>(List.of(driver1, driver2, driver3));
        when(rideRepository.getPendingRides()).thenReturn(rides);
        when(driverRepository.getAvailableDrivers()).thenReturn(drivers);
        ///when
        rideService.assignDriversToRides();
        //then
        verify(driver1, never()).copyWith(DriverStatus.IN_RIDE);
        verify(driver2, never()).copyWith(DriverStatus.IN_RIDE);
        verify(driver3, never()).copyWith(DriverStatus.IN_RIDE);
        verify(driverRepository, never()).save(any());
        verify(rideRepository, never()).save(any());
    }
}
