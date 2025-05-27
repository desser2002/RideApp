package domen.rideapp.domain.model;

import domen.rideapp.domain.service.Localization;

import java.util.UUID;

public class Ride {
    private String id;

    public String getId() {
        return id;
    }

    private String customer;
    private Localization localization;
    private RideStatus status;
    private double cost;
    private Driver driver;

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Ride(String customer, Localization localization, RideStatus status, double cost) {
        this.id = UUID.randomUUID().toString();
        this.customer = customer;
        this.localization = localization;
        this.status = status;
        this.cost = cost;
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

    public double getCost() {
        return cost;
    }

    public Driver getDriver() {
        return driver;
    }
}
