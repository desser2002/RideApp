package domen.rideapp.infrastructure.jpa.adapter;

import domen.rideapp.domain.model.Driver;
import domen.rideapp.domain.model.DriverStatus;
import domen.rideapp.infrastructure.jpa.mapper.DriverMapper;
import domen.rideapp.infrastructure.jpa.model.DriverJpa;
import domen.rideapp.infrastructure.jpa.repository.DriverRepositoryJpa;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class DriverRepositoryJpaAdapterTest {
    @Mock
    DriverRepositoryJpa repositoryJpa;

    @InjectMocks
    DriverRepositoryJpaAdapter adapter;

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
    @DisplayName("getAvailableDrivers returns domain list mapped from JPA")
    void getAvailableDrivers_shouldReturnMappedDomainList() {
        DriverJpa driverJpa = mock(DriverJpa.class);
        Driver driver = mock(Driver.class);
        DriverStatus status = DriverStatus.AVAILABLE;
        when(repositoryJpa.findByStatus(status)).thenReturn(List.of(driverJpa));

        try (MockedStatic<DriverMapper> mockDriverMapper = mockStatic(DriverMapper.class)) {
            mockDriverMapper.when(() -> DriverMapper.toDomain(driverJpa)).thenReturn(driver);

            List<Driver> drivers = adapter.getAvailableDrivers();
            assertThat(drivers).containsExactly(driver);
            mockDriverMapper.verify(() -> DriverMapper.toDomain(driverJpa));
        }
    }

    @Test
    @DisplayName("save invokes repository save with mapped entity")
    void save_invokesRepositorySaveWithDriverEntity() {
        Driver driver = mock(Driver.class);
        DriverJpa driverJpa = mock(DriverJpa.class);

        try (MockedStatic<DriverMapper> mockDriverMapper = mockStatic(DriverMapper.class)) {
            mockDriverMapper.when(() -> DriverMapper.toEntity(driver)).thenReturn(driverJpa);

            adapter.save(driver);

            verify(repositoryJpa, times(1)).save(driverJpa);
            mockDriverMapper.verify(() -> DriverMapper.toEntity(driver), times(1));
        }
    }

    @Test
    @DisplayName("remove calls repository deleteById")
    void remove_shouldCallRepositoryDeleteById() {
        String driverId = "driver1";
        adapter.remove(driverId);
        verify(repositoryJpa, times(1)).deleteById(driverId);
    }

    @Test
    @DisplayName("getAll returns mapped domain list")
    void getAll_shouldReturnMappedDomainList() {
        DriverJpa driverJpa = mock(DriverJpa.class);
        Driver driver = mock(Driver.class);
        List<DriverJpa> jpaList = Collections.nCopies(3,driverJpa);
        List<Driver> drivers = Collections.nCopies(3,driver);
        when(repositoryJpa.findAll()).thenReturn(jpaList);

        try (MockedStatic<DriverMapper> mockDriverMapper = mockStatic(DriverMapper.class)) {
            mockDriverMapper.when(() -> DriverMapper.toDomain(driverJpa)).thenReturn(driver);

            List<Driver> result = adapter.getAll();

            assertThat(result).containsExactlyElementsOf(drivers);
            verify(repositoryJpa, times(1)).findAll();
            mockDriverMapper.verify(() -> DriverMapper.toDomain(driverJpa), times(3));
        }
    }

    @Test
    @DisplayName("getAvailableDrivers returns empty list if none available")
    void getAvailableDrivers_shouldReturnEmptyListWhenNone() {
        when(repositoryJpa.findByStatus(DriverStatus.AVAILABLE)).thenReturn(List.of());

        List<Driver> result = adapter.getAvailableDrivers();

        assertThat(result).isEmpty();
        verify(repositoryJpa, times(1)).findByStatus(DriverStatus.AVAILABLE);
    }

    @Test
    @DisplayName("getDriverById returns mapped Optional when found")
    void getDriverById_shouldReturnMappedOptional() {
        String id = "driver";
        DriverJpa driverJpa = mock(DriverJpa.class);
        Driver driver = mock(Driver.class);
        when(repositoryJpa.findById(id)).thenReturn(Optional.of(driverJpa));

        try (MockedStatic<DriverMapper> mockDriverMapper = mockStatic(DriverMapper.class)) {
            mockDriverMapper.when(() -> DriverMapper.toDomain(driverJpa)).thenReturn(driver);

            Optional<Driver> result = adapter.getDriverById(id);

            assertThat(result).isPresent().contains(driver);
            verify(repositoryJpa, times(1)).findById(id);
            mockDriverMapper.verify(() -> DriverMapper.toDomain(driverJpa), times(1));
        }
    }

    @Test
    @DisplayName("getDriverById returns empty Optional when not found")
    void getDriverById_shouldReturnEmptyOptionalWhenNotFound() {
        String id = "unknown";
        when(repositoryJpa.findById(id)).thenReturn(Optional.empty());

        Optional<Driver> result = adapter.getDriverById(id);

        assertThat(result).isEmpty();
        verify(repositoryJpa, times(1)).findById(id);
    }

    @Test
    @DisplayName("clear calls repository deleteAll")
    void clear_shouldCallRepositoryDeleteAll() {
        adapter.clear();
        verify(repositoryJpa, times(1)).deleteAll();
    }

}