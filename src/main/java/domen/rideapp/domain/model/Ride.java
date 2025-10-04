package domen.rideapp.domain.model;

import java.util.UUID;

//TODO implement builder pattern
public class Ride {
    private final String id;

    private final String customer;
    private final Localization localization;
    private RideStatus status;
    private final Price price;
    private Driver driver;

    public Ride(String customer, Localization localization, RideStatus status, Price price) {
        this.id = UUID.randomUUID().toString();
        this.customer = customer;
        this.localization = localization;
        this.status = status;
        this.price = price;
    }

    public Ride(String id, String customer, Localization localization, RideStatus status, Price price, Driver driver) {
        this.id = id != null ? id : UUID.randomUUID().toString();
        this.customer = customer;
        this.localization = localization;
        this.status = status;
        this.price = price;
        this.driver = driver;
    }

    public String getId() {
        return id;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public void setStatus(RideStatus status) {
        this.status = status;
    }

    public String getCustomer() {
        return customer;
    }

    public Localization getLocalization() {
        return localization;
    }

    public RideStatus getStatus() {
        return status;
    }

    public Price getPrice() {
        return price;
    }

    public Driver getDriver() {
        return driver;
    }
}
