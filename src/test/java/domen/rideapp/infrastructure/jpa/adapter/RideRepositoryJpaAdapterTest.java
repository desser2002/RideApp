package domen.rideapp.infrastructure.jpa.adapter;

import domen.rideapp.domain.model.Ride;
import domen.rideapp.domain.model.RideStatus;
import domen.rideapp.infrastructure.jpa.mapper.RideMapper;
import domen.rideapp.infrastructure.jpa.model.RideJpa;
import domen.rideapp.infrastructure.jpa.repository.RideRepositoryJpa;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class RideRepositoryJpaAdapterTest {

    @Mock
    RideRepositoryJpa repositoryJpa;

    @InjectMocks
    RideRepositoryJpaAdapter adapter;

    private AutoCloseable mocks;

    @BeforeEach
    void setup() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }

    @Test
    @DisplayName("Save should call save with mapped entity")
    void save_shouldCallSaveOnJpaRepositoryWithJpaEntity() {
        Ride ride = mock(Ride.class);
        RideJpa rideJpa = mock(RideJpa.class);

        try (MockedStatic<RideMapper> mockRideMapper = mockStatic(RideMapper.class)) {
            mockRideMapper.when(() -> RideMapper.toEntity(ride)).thenReturn(rideJpa);

            adapter.save(ride);

            mockRideMapper.verify(() -> RideMapper.toEntity(ride), times(1));
            verify(repositoryJpa, times(1)).save(rideJpa);
        }
    }

    @Test
    @DisplayName("getPendingRides should return list of pending rides")
    void getPendingRides_shouldReturnPendingRides() {
        Ride ride = mock(Ride.class);
        List<Ride> rides = Collections.nCopies(3, ride);
        RideJpa rideJpa = mock(RideJpa.class);
        List<RideJpa> rideJpas = Collections.nCopies(3, rideJpa);
        RideStatus pending = RideStatus.PENDING;

        when(repositoryJpa.findByStatus(pending)).thenReturn(rideJpas);

        try (MockedStatic<RideMapper> mockRideMapper = mockStatic(RideMapper.class)) {
            mockRideMapper.when(() -> RideMapper.toDomain(rideJpa)).thenReturn(ride);

            List<Ride> result = adapter.getPendingRides();

            assertThat(result).containsExactlyElementsOf(rides);
            verify(repositoryJpa, times(1)).findByStatus(pending);
            mockRideMapper.verify(() -> RideMapper.toDomain(rideJpa), times(3));
        }

    }

    @Test
    @DisplayName("getAllRides should return list of all rides")
    void getAllRides_shouldReturnAllRides() {
        Ride ride = mock(Ride.class);
        List<Ride> rides = Collections.nCopies(3, ride);
        RideJpa rideJpa = mock(RideJpa.class);
        List<RideJpa> rideJpas = Collections.nCopies(3, rideJpa);

        when(repositoryJpa.findAll()).thenReturn(rideJpas);

        try (MockedStatic<RideMapper> mockRideMapper = mockStatic(RideMapper.class)) {
            mockRideMapper.when(() -> RideMapper.toDomain(rideJpa)).thenReturn(ride);
            List<Ride> result = adapter.getAllRides();
            assertThat(result).containsExactlyElementsOf(rides);
            verify(repositoryJpa, times(1)).findAll();
            mockRideMapper.verify(() -> RideMapper.toDomain(rideJpa), times(3));
        }
    }


}