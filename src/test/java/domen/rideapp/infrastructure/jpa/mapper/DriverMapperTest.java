package domen.rideapp.infrastructure.jpa.mapper;

import domen.rideapp.domain.model.Driver;
import domen.rideapp.domain.model.DriverStatus;
import domen.rideapp.infrastructure.jpa.model.DriverJpa;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class DriverMapperTest {
    @Test
    @DisplayName("Should correctly map Driver domain to DriverJpa entity")
    void toEntity_forValid_shouldReturnCorrectEntity() {
        String id = "driver-123";
        String firstName = "John";
        String lastName = "Doe";
        DriverStatus status = DriverStatus.AVAILABLE;

        Driver driver = new Driver(id, firstName, lastName, status);

        DriverJpa result = DriverMapper.toEntity(driver);

        assertEquals(id, result.getId());
        assertEquals(firstName, result.getFirstName());
        assertEquals(lastName, result.getLastName());
        assertEquals(status, result.getStatus());
    }

    @Test
    @DisplayName("Should correctly map DriverJpa entity to Driver domain")
    void toDomain_forValidEntity_shouldReturnCorrectDriver() {
        String id = "driver-123";
        String firstName = "John";
        String lastName = "Doe";
        DriverStatus status = DriverStatus.AVAILABLE;

        DriverJpa driverJpa = new DriverJpa(id, firstName, lastName, status);

        Driver result = DriverMapper.toDomain(driverJpa);

        assertEquals(id, result.id());
        assertEquals(firstName, result.firstName());
        assertEquals(lastName, result.lastName());
        assertEquals(status, result.status());
    }

    @Test
    @DisplayName("Should return null when mapping null Driver domain to entity")
    void toEntity_whenDriverDomainIsNull_shouldReturnNull() {
        DriverJpa result = DriverMapper.toEntity(null);
        assertNull(result);
    }

    @Test
    @DisplayName("Should return null when mapping null DriverJpa entity to domain")
    void toDomain_whenDriverEntityIsNull_shouldReturnNull() {
        Driver result = DriverMapper.toDomain(null);
        assertNull(result);
    }

}