package domen.rideapp.infrastructure.jpa.model;

import jakarta.persistence.*;

import java.util.Objects;

@Embeddable
public class LocalizationJpa {
    @Embedded
    @AttributeOverride(name="latitude", column=@Column(name="from_latitude"))
    @AttributeOverride(name="longitude", column=@Column(name="from_longitude"))
    private GeoPointJpa from;

    @Embedded
    @AttributeOverride(name="latitude", column=@Column(name="to_latitude"))
    @AttributeOverride(name="longitude", column=@Column(name="to_longitude"))
    private GeoPointJpa to;

    public LocalizationJpa() {
    }

    public LocalizationJpa(GeoPointJpa from, GeoPointJpa to) {
        this.from = from;
        this.to = to;
    }

    public GeoPointJpa getFrom() {
        return from;
    }

    public void setFrom(GeoPointJpa from) {
        this.from = from;
    }

    public GeoPointJpa getTo() {
        return to;
    }

    public void setTo(GeoPointJpa to) {
        this.to = to;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        LocalizationJpa that = (LocalizationJpa) o;
        return Objects.equals(from, that.from) && Objects.equals(to, that.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }
}
