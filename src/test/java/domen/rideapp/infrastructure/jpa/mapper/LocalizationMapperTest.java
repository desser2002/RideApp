package domen.rideapp.infrastructure.jpa.mapper;

import domen.rideapp.domain.model.GeoPoint;
import domen.rideapp.domain.model.Localization;
import domen.rideapp.infrastructure.jpa.model.GeoPointJpa;
import domen.rideapp.infrastructure.jpa.model.LocalizationJpa;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LocalizationMapperTest {
    @Test
    @DisplayName("Should correctly map Localization ot Localization Jpa Entity")
    void toEntity_forValid_shouldReturnCorrectEntity() {
        GeoPoint from = new GeoPoint(23.0121, 29.0189);
        GeoPoint to = new GeoPoint(35.8121, 89.1289);
        Localization localization = new Localization(from, to);

        LocalizationJpa result = LocalizationMapper.toEntity(localization);

        assertAll(
                () -> assertThat(result.getTo())
                        .extracting(GeoPointJpa::getLatitude, GeoPointJpa::getLongitude)
                        .containsExactly(to.latitude(), to.longitude()),

                () -> assertThat(result.getFrom())
                        .extracting(GeoPointJpa::getLatitude, GeoPointJpa::getLongitude)
                        .containsExactly(from.latitude(), from.longitude()));
    }

    @Test
    @DisplayName("Should correctly map LocalizationJpa on Localization domain")
    void toDomain_forValidEntity_shouldReturnCorrectLocalization() {
        GeoPointJpa from = new GeoPointJpa(23.0121, 29.0189);
        GeoPointJpa to = new GeoPointJpa(35.8121, 89.1289);
        LocalizationJpa localizationJpa = new LocalizationJpa(from, to);

        Localization result = LocalizationMapper.toDomain(localizationJpa);

        assertAll(
                () -> assertThat(result.to())
                        .extracting(GeoPoint::latitude, GeoPoint::longitude)
                        .containsExactly(to.getLatitude(), to.getLongitude()),

                () -> assertThat(result.from())
                        .extracting(GeoPoint::latitude, GeoPoint::longitude)
                        .containsExactly(from.getLatitude(), from.getLongitude())
        );
    }

    @Test
    @DisplayName("Should return null on mapping null Localization to Entity")
    void toEntity_whenLocalizationIsNull_shouldReturnNull() {
        LocalizationJpa result = LocalizationMapper.toEntity(null);
        assertNull(result);
    }

    @Test
    @DisplayName("Should return null on mapping null Localization to Domain")
    void toDomain_whenLocalizationEntityIsNull_shouldReturnNull() {
        Localization result = LocalizationMapper.toDomain(null);
        assertNull(result);
    }
}