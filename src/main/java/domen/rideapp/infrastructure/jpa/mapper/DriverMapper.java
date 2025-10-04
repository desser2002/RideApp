package domen.rideapp.infrastructure.jpa.mapper;

import domen.rideapp.domain.model.Driver;
import domen.rideapp.infrastructure.jpa.model.DriverJpa;


public class DriverMapper {
    private DriverMapper() {}

    public static Driver toDomain(DriverJpa driverJpa) {
        if (driverJpa == null) {
            return null;
        }

        return new Driver(
                driverJpa.getId(),
                driverJpa.getFirstName(),
                driverJpa.getLastName(),
                driverJpa.getStatus()
        );
    }

    public static DriverJpa toEntity(Driver driver) {
        if (driver == null) {
            return null;
        }

        return new DriverJpa(
                driver.id(),
                driver.firstName(),
                driver.lastName(),
                driver.status()
        );
    }
}
