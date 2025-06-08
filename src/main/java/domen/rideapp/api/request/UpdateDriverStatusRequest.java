package domen.rideapp.api.request;

import domen.rideapp.domain.model.DriverStatus;

public record UpdateDriverStatusRequest(DriverStatus status) {
}
