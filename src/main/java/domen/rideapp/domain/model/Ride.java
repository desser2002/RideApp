package domen.rideapp.domain.model;
import domen.rideapp.domain.service.Localization;

public class Ride {
    private String customer;
    private Localization localization;
    private RideStatus status;
    private double cost;
    private Driver driver;

    public Ride(String customer, Localization localization, RideStatus status, double cost) {
        this.customer = customer;
        this.localization = localization;
        this.status = status;
        this.cost = cost;
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

    public double getCost() {
        return cost;
    }

    public Driver getDriver() {
        return driver;
    }
}
