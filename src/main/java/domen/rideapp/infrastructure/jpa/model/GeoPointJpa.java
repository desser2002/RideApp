package domen.rideapp.infrastructure.jpa.model;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class GeoPointJpa {
    private double latitude;
    private double longitude;

    public GeoPointJpa() {
    }

    public GeoPointJpa(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        GeoPointJpa geoPoint = (GeoPointJpa) o;
        return Double.compare(latitude, geoPoint.latitude) == 0 && Double.compare(longitude, geoPoint.longitude) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }
}
