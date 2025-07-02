package domen.rideapp.infrastructure.mixin;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import domen.rideapp.domain.model.Driver;
import domen.rideapp.domain.model.Localization;
import domen.rideapp.domain.model.Price;
import domen.rideapp.domain.model.RideStatus;

public abstract class RideMixin {
    @JsonCreator
    public RideMixin(
            @JsonProperty("id") String id,
            @JsonProperty("customer") String customer,
            @JsonProperty("localization") Localization localization,
            @JsonProperty("status") RideStatus status,
            @JsonProperty("price") Price price,
            @JsonProperty("driver") Driver driver
    ) {
    }
}
