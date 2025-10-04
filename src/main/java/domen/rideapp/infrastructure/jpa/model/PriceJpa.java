package domen.rideapp.infrastructure.jpa.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Currency;
import java.util.Objects;

@Embeddable
public class PriceJpa {
    private double amount;

    @Column(length = 3)
    private String currencyCode;

    public PriceJpa() {
    }

    public PriceJpa(double amount, String currencyCode) {
        this.amount = amount;
        this.currencyCode = currencyCode;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return Currency.getInstance(currencyCode);
    }

    public void setCurrencyCode(Currency currency) {
        this.currencyCode = currency.getCurrencyCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PriceJpa price = (PriceJpa) o;
        return Double.compare(amount, price.amount) == 0 && Objects.equals(currencyCode, price.currencyCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, currencyCode);
    }
}
