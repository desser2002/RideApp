package domen.rideapp.domain.model;

import static java.util.Locale.US;

public record GeoPoint(double latitude, double longitude) {
    @Override
    public String toString() {
        return String.format(US, "%.4f,%.4f", latitude, longitude);
    }
}
