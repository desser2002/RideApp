package domen.rideapp.infrastructure.jpa.mapper;

import domen.rideapp.domain.model.Price;
import domen.rideapp.infrastructure.jpa.model.PriceJpa;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PriceMapperTest {
    @Test
    @DisplayName("Should correctly map Price domain to PriceJpa entity")
    void toEntity_forValid_shouldReturnCorrectEntity() {
        double amount = 123.45;
        Currency currency = Currency.getInstance("USD");
        Price price = new Price(amount, currency);

        PriceJpa result = PriceMapper.toEntity(price);

        assertEquals(amount, result.getAmount());
        assertEquals(currency, result.getCurrency());
    }

    @Test
    @DisplayName("Should correctly map PriceJpa entity to Price domain")
    void toDomain_forValidEntity_shouldReturnCorrectPrice() {
        double amount = 123.45;
        String currencyCode = "USD";
        Currency currency = Currency.getInstance(currencyCode);
        PriceJpa priceJpa = new PriceJpa(amount, currencyCode);

        Price result = PriceMapper.toDomain(priceJpa);

        assertEquals(amount, result.amount());
        assertEquals(currency, result.currency());
    }

    @Test
    @DisplayName("Should return null when mapping null Price domain to entity")
    void toEntity_whenPriceDomainIsNull_shouldReturnNull() {
        PriceJpa result = PriceMapper.toEntity(null);
        assertNull(result);
    }

    @Test
    @DisplayName("Should return null when mapping null PriceJpa entity to domain")
    void toDomain_whenPriceEntityIsNull_shouldReturnNull() {
        Price result = PriceMapper.toDomain(null);
        assertNull(result);
    }

}