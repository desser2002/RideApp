package domen.rideapp.infrastructure.mapping;

import domen.rideapp.api.response.RideResponse;
import domen.rideapp.domain.model.Ride;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RideToRideResponse implements Converter<Ride, RideResponse> {
    @Override
    public RideResponse convert(Ride source) {
        return new RideResponse(source.getId(), source.getCustomer(), source.getStatus().name(),
                source.getPrice().toString());
    }
}
