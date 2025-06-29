package domen.rideapp.domain.model;

import java.util.Currency;
import java.util.Objects;

public record Price(double amount, Currency currency) {
    private static final String PRICE_FORMAT_REGEX = "\\d+(\\.\\d{1,2})?\\s+[A-Z]{3}";

    public Price(String valueWithCurrency) {
        this(parseAmount(valueWithCurrency), parseCurrency(valueWithCurrency));
    }

    private static double parseAmount(String valueWithCurrency) {
        if (valueWithCurrency == null || !valueWithCurrency.matches(PRICE_FORMAT_REGEX)) {
            throw new IllegalArgumentException("Invalid format. " +
                    "Expected format: '<amount> <currency>', e.g. '123.45 PLN'");
        }
        String[] parts = valueWithCurrency.trim().split("\\s+");
        return Math.round(Double.parseDouble(parts[0]) * 100.0) / 100.0;
    }

    private static Currency parseCurrency(String valueWithCurrency) {
        String[] parts = valueWithCurrency.trim().split("\\s+");
        return Currency.getInstance(parts[1]);
    }

    @Override
    public String toString() {
        return amount + " " + currency.getCurrencyCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof Price(double otherAmount, Currency otherCurrency)) {
            return Double.compare(otherAmount, amount) == 0 &&
                    currency.equals(otherCurrency);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, currency);
    }
}
