package domen.rideapp.infrastructure.jpa.mapper;

import domen.rideapp.domain.model.*;
import domen.rideapp.infrastructure.jpa.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Currency;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class RideMapperTest {
    @Test
    @DisplayName("Should correctly map Ride domain to RideJpa entity")
    void toEntity_forValid_shouldReturnCorrectEntity() {
        Driver driver = new Driver("driverId", "John", "Doe", DriverStatus.AVAILABLE);
        Localization localization = new Localization(
                new domen.rideapp.domain.model.GeoPoint(23.0, 24.0),
                new domen.rideapp.domain.model.GeoPoint(35.0, 36.0)
        );
        Price price = new Price(100.0, Currency.getInstance("USD"));
        Ride ride = new Ride("rideId", "customer1", localization,
                RideStatus.PENDING, price, driver);

        RideJpa result = RideMapper.toEntity(ride);

        assertAll("Check all fields of RideJpa",
                () -> assertThat(result.getId()).isEqualTo(ride.getId()),
                () -> assertThat(result.getCustomer()).isEqualTo(ride.getCustomer()),
                () -> assertThat(result.getStatus()).isEqualTo(ride.getStatus()),
                () -> assertThat(result.getLocalization().getFrom())
                        .extracting("latitude", "longitude")
                        .containsExactly(localization.from().latitude(), localization.from().longitude()),
                () -> assertThat(result.getLocalization().getTo())
                        .extracting("latitude", "longitude")
                        .containsExactly(localization.to().latitude(), localization.to().longitude()),
                () -> assertThat(result.getPrice())
                        .extracting("amount", "currency")
                        .containsExactly(price.amount(), price.currency()),
                () -> assertThat(result.getDriver())
                        .extracting("id", "firstName", "lastName", "status")
                        .containsExactly(driver.id(), driver.firstName(), driver.lastName(), driver.status())
        );

    }

    @Test
    @DisplayName("Should correctly map RideJpa entity to Ride domain")
    void toDomain_forValidEntity_shouldReturnCorrectDomain() {
        DriverJpa driverJpa = new DriverJpa("driverId", "John", "Doe", DriverStatus.AVAILABLE);

        GeoPointJpa from = new GeoPointJpa(23.0, 24.0);
        GeoPointJpa to = new GeoPointJpa(35.0, 36.0);
        LocalizationJpa localizationJpa = new LocalizationJpa(from, to);

        PriceJpa priceJpa = new PriceJpa(100.0, "USD");

        RideJpa rideJpa = new RideJpa(
                "rideId",
                "customer1",
                localizationJpa,
                RideStatus.FOUND,
                priceJpa,
                driverJpa
        );

        Ride result = RideMapper.toDomain(rideJpa);

        assertAll("Check all properties for Ride domain",
                () -> assertThat(result.getId()).isEqualTo(rideJpa.getId()),
                () -> assertThat(result.getCustomer()).isEqualTo(rideJpa.getCustomer()),
                () -> assertThat(result.getStatus()).isEqualTo(rideJpa.getStatus()),
                () -> assertThat(result.getLocalization().from())
                        .extracting("latitude", "longitude")
                        .containsExactly(
                                rideJpa.getLocalization().getFrom().getLatitude(),
                                rideJpa.getLocalization().getFrom().getLongitude()
                        ),
                () -> assertThat(result.getLocalization().to())
                        .extracting("latitude", "longitude")
                        .containsExactly(
                                rideJpa.getLocalization().getTo().getLatitude(),
                                rideJpa.getLocalization().getTo().getLongitude()
                        ),
                () -> assertThat(result.getPrice())
                        .extracting("amount", "currency")
                        .containsExactly(
                                rideJpa.getPrice().getAmount(),
                                rideJpa.getPrice().getCurrency()
                        ),
                () -> assertThat(result.getDriver())
                        .extracting("id", "firstName", "lastName", "status")
                        .containsExactly(
                                rideJpa.getDriver().getId(),
                                rideJpa.getDriver().getFirstName(),
                                rideJpa.getDriver().getLastName(),
                                rideJpa.getDriver().getStatus()
                        )
        );
    }


}