package domen.rideapp.infrastructure.jpa.mapper;

import domen.rideapp.domain.model.Driver;
import domen.rideapp.domain.model.Localization;
import domen.rideapp.domain.model.Price;
import domen.rideapp.domain.model.Ride;
import domen.rideapp.infrastructure.jpa.model.DriverJpa;
import domen.rideapp.infrastructure.jpa.model.LocalizationJpa;
import domen.rideapp.infrastructure.jpa.model.PriceJpa;
import domen.rideapp.infrastructure.jpa.model.RideJpa;

public class RideMapper {
    private RideMapper(){}

    public static Ride toDomain(RideJpa rideJpa) {
        if (rideJpa == null) {
            return null;
        }
        Localization localization = LocalizationMapper.toDomain(rideJpa.getLocalization());
        Price price = PriceMapper.toDomain(rideJpa.getPrice());
        Driver driver = DriverMapper.toDomain(rideJpa.getDriver());
        return new Ride(
                rideJpa.getId(),
                rideJpa.getCustomer(),
                localization,
                rideJpa.getStatus(),
                price,
                driver);

    }

    public static RideJpa toEntity(Ride ride) {
        if (ride == null) {
            return null;
        }

        LocalizationJpa localization = LocalizationMapper.toEntity(ride.getLocalization());
        PriceJpa price = PriceMapper.toEntity(ride.getPrice());
        DriverJpa driver = DriverMapper.toEntity(ride.getDriver());

        return new RideJpa(
                ride.getId(),
                ride.getCustomer(),
                localization,
                ride.getStatus(),
                price,
                driver);
    }


}
