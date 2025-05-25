package domen.rideapp.domain.model;

public record Driver(String id, String firstName, String lastName, DriverStatus status) {
    public Driver copyWith(DriverStatus driverStatus) {
        return new Driver(this.id, this.firstName, this.lastName, driverStatus);
    }
}
