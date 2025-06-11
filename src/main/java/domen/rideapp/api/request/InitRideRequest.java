package domen.rideapp.api.request;

import jakarta.validation.constraints.NotBlank;

public record InitRideRequest(
        @NotBlank String customer,
        @NotBlank String from,
        @NotBlank String to
) {}
