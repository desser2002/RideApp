package domen.rideapp.domain.service;

import domen.rideapp.domain.model.*;
import domen.rideapp.domain.repository.DriverRepository;
import domen.rideapp.domain.repository.RideRepository;
import domen.rideapp.domain.repository.RideCacheRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RideServiceTest {
    @Mock
    private PricingService pricingService;
    @Mock
    private RideRepository rideRepository;
    @Mock
    private DriverRepository driverRepository;
    @Mock
    private RideCacheRepository rideCacheRepository;
    @InjectMocks
    private RideService rideService;

    @Test
    void shouldAddRide() {
        //given
        String customer = "Customer";
        GeoPoint from = new GeoPoint(52.2297, 21.0122);
        GeoPoint to = new GeoPoint(51.1079, 17.0385);
        Localization localization = new Localization(from, to);
        //when
        rideService.rideInitiation(customer, localization);
        //then
        ArgumentCaptor<Ride> argumentCaptor = ArgumentCaptor.forClass(Ride.class);
        verify(rideCacheRepository).save(argumentCaptor.capture());
        verify(rideRepository).save(argumentCaptor.capture());
        Ride ride = argumentCaptor.getValue();
        assertEquals(customer, ride.getCustomer());
        assertEquals(from, ride.getLocalization().from());
        assertEquals(to, ride.getLocalization().to());
        assertNull(ride.getDriver());
        verify(pricingService).getCost(localization);
    }

    @Test
    void shouldAssignRidesWhenRidesAmountIsMoreThenDrivers() {
        //given
        GeoPoint from = new GeoPoint(52.2297, 21.0122);
        GeoPoint to = new GeoPoint(51.1079, 17.0385);
        Localization localization = new Localization(from, to);
        Ride ride1 = spy(new Ride("Customer1", localization, RideStatus.PENDING, new Price("12.01 PLN")));
        Ride ride2 = spy(new Ride("Customer2", localization, RideStatus.PENDING, new Price("12.02 PLN")));
        Ride ride3 = spy(new Ride("Customer3", localization, RideStatus.PENDING, new Price("12.03 PLN")));
        List<Ride> rides = new ArrayList<>(List.of(ride1, ride2, ride3));
        Driver driver1 = spy(new Driver(UUID.randomUUID().toString(), "Jon", "Latitude", DriverStatus.AVAILABLE));
        Driver driver2 = spy(new Driver(UUID.randomUUID().toString(), "Bob", "Latitude", DriverStatus.AVAILABLE));
        List<Driver> drivers = new ArrayList<>(List.of(driver1, driver2));
        when(rideCacheRepository.getPendingRides()).thenReturn(rides);
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
        GeoPoint from = new GeoPoint(52.2297, 21.0122);
        GeoPoint to = new GeoPoint(51.1079, 17.0385);
        Localization localization = new Localization(from, to);
        Ride ride1 = spy(new Ride("Customer1", localization, RideStatus.PENDING, new Price("12.01 PLN")));
        Ride ride2 = spy(new Ride("Customer2", localization, RideStatus.PENDING, new Price("12.02 PLN")));
        List<Ride> rides = new ArrayList<>(List.of(ride1, ride2));
        Driver driver1 = spy(new Driver(UUID.randomUUID().toString(), "Jon", "Latitude", DriverStatus.AVAILABLE));
        Driver driver2 = spy(new Driver(UUID.randomUUID().toString(), "Bob", "Latitude", DriverStatus.AVAILABLE));
        Driver driver3 = spy(new Driver(UUID.randomUUID().toString(), "Yann", "Kowalski", DriverStatus.AVAILABLE));
        List<Driver> drivers = new ArrayList<>(List.of(driver1, driver2, driver3));
        when(rideCacheRepository.getPendingRides()).thenReturn(rides);
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
        GeoPoint from = new GeoPoint(52.2297, 21.0122);
        GeoPoint to = new GeoPoint(51.1079, 17.0385);
        Localization localization = new Localization(from, to);
        Ride ride1 = spy(new Ride("Customer1", localization, RideStatus.PENDING, new Price("12.01 PLN")));
        Ride ride2 = spy(new Ride("Customer2", localization, RideStatus.PENDING, new Price("12.02 PLN")));
        Ride ride3 = spy(new Ride("Customer3", localization, RideStatus.PENDING, new Price("12.03 PLN")));
        List<Ride> rides = new ArrayList<>(List.of(ride1, ride2, ride3));
        Driver driver1 = spy(new Driver(UUID.randomUUID().toString(), "Jon", "Latitude", DriverStatus.AVAILABLE));
        Driver driver2 = spy(new Driver(UUID.randomUUID().toString(), "Bob", "Latitude", DriverStatus.AVAILABLE));
        Driver driver3 = spy(new Driver(UUID.randomUUID().toString(), "Yann", "Kowalski", DriverStatus.AVAILABLE));
        List<Driver> drivers = new ArrayList<>(List.of(driver1, driver2, driver3));
        when(rideCacheRepository.getPendingRides()).thenReturn(rides);
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
        when(rideCacheRepository.getPendingRides()).thenReturn(rides);
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
