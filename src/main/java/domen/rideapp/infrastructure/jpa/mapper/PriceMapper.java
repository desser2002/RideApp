package domen.rideapp.infrastructure.jpa.mapper;

import domen.rideapp.domain.model.Price;
import domen.rideapp.infrastructure.jpa.model.PriceJpa;

public class PriceMapper {
    private PriceMapper() {}

    public static Price toDomain(PriceJpa priceJpa) {
        if (priceJpa == null) {
            return null;
        }

        return new Price(priceJpa.getAmount(), priceJpa.getCurrency());
    }

    public static PriceJpa toEntity(Price price) {
        if (price == null) {
            return null;
        }

        return new PriceJpa(price.amount(), price.currency().getCurrencyCode());
    }
}
