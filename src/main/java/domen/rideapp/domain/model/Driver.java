package domen.rideapp.domain.model;

import java.util.UUID;

public record Driver(String id, String firstName, String lastName, DriverStatus status) {
    public Driver(String firstName, String lastName) {
        this(UUID.randomUUID().toString(), firstName, lastName, DriverStatus.AVAILABLE);
    }

    public Driver copyWith(DriverStatus driverStatus) {
        return new Driver(this.id, this.firstName, this.lastName, driverStatus);
    }

    public Driver copyWith(String firstName, String lastName, DriverStatus driverStatus) {
        return new Driver(this.id, firstName, lastName, driverStatus);
    }
}
