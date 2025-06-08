package domen.rideapp.infrastructure.mapping;

import domen.rideapp.api.response.DriverResponse;
import domen.rideapp.domain.model.Driver;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DriverToDriverResponse implements Converter<Driver, DriverResponse> {
    @Override
    public DriverResponse convert(Driver source) {
        return new DriverResponse(source.id(), source.firstName(), source.lastName(), source.status().toString());
    }
}
