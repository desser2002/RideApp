package domen.rideapp.api.request;

import domen.rideapp.domain.model.GeoPoint;
import jakarta.validation.constraints.NotBlank;

public record InitRideRequest(
        @NotBlank String customer,
        GeoPoint from,
        GeoPoint to
) {
}
