package domen.rideapp.infrastructure.jpa.mapper;

import domen.rideapp.domain.model.GeoPoint;
import domen.rideapp.domain.model.Localization;
import domen.rideapp.infrastructure.jpa.model.GeoPointJpa;
import domen.rideapp.infrastructure.jpa.model.LocalizationJpa;

public class LocalizationMapper {

    private LocalizationMapper() {}

    public static Localization toDomain(LocalizationJpa localizationJpa) {
        if (localizationJpa == null) {
            return null;
        }

        GeoPoint from = GeoPointMapper.toDomain(localizationJpa.getFrom());
        GeoPoint to = GeoPointMapper.toDomain(localizationJpa.getTo());

        return new Localization(from, to);
    }

    public static LocalizationJpa toEntity(Localization localization) {
        if (localization == null) {
            return null;
        }

        GeoPointJpa from = GeoPointMapper.toEntity(localization.from());
        GeoPointJpa to = GeoPointMapper.toEntity(localization.to());
        return new LocalizationJpa(from,to);
    }
}
