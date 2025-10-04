package domen.rideapp.infrastructure.jpa.mapper;

import domen.rideapp.domain.model.GeoPoint;
import domen.rideapp.infrastructure.jpa.model.GeoPointJpa;

public class GeoPointMapper {
    private GeoPointMapper() {}

    public static GeoPoint toDomain(GeoPointJpa geoPointJpa) {
        if (geoPointJpa == null) {
            return null;
        }
        return new GeoPoint(geoPointJpa.getLatitude(), geoPointJpa.getLongitude());
    }

    public static GeoPointJpa toEntity(GeoPoint geoPoint) {
        if (geoPoint == null) {
            return null;
        }
        return new GeoPointJpa(geoPoint.latitude(), geoPoint.longitude());
    }

}
