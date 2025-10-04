package domen.rideapp.infrastructure.jpa.mapper;

import domen.rideapp.domain.model.GeoPoint;
import domen.rideapp.infrastructure.jpa.model.GeoPointJpa;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeoPointMapperTest {

    @Test
    @DisplayName("Should correctly map GeoPoint domain to GeoPointEntity")
    void toEntity_forValidMap_shouldReturnCorrectEntity() {
        double latitude = 23.0202;
        double longitude = 14.0303;
        GeoPoint geoPoint = new GeoPoint(latitude, longitude);

        GeoPointJpa result = GeoPointMapper.toEntity(geoPoint);
        assertEquals(latitude, result.getLatitude());
        assertEquals(longitude, result.getLongitude());
    }

    @Test
    @DisplayName("Should correctly map GeoPointEntity to GeoPoint domain")
    void toDomain_forValidEntity_shouldReturnCorrectGeoPoint() {
        double latitude = 23.0202;
        double longitude = 14.0303;
        GeoPointJpa geoPointJpa = new GeoPointJpa(latitude, longitude);

        GeoPoint result = GeoPointMapper.toDomain(geoPointJpa);

        assertEquals(latitude, result.latitude());
        assertEquals(longitude, result.longitude());
    }

    @Test
    @DisplayName("Should return null on mapping null GeoPoint to Entity")
    void toEntity_whenGeoPointDomainIsNull_shouldReturnNull() {
        GeoPointJpa result = GeoPointMapper.toEntity(null);
        assertNull(result);
    }

    @Test
    @DisplayName("Should return null on mapping null GeoPointEntity to Domain")
    void toDomain_whenGeoPointEntityIsNull_shouldReturnNull() {
        GeoPoint result = GeoPointMapper.toDomain(null);
        assertNull(result);
    }
}