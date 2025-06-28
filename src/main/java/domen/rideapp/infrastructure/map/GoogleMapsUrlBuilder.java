package domen.rideapp.infrastructure.map;

import domen.rideapp.domain.model.Localization;
import domen.rideapp.infrastructure.GoogleMapsConfig;

import java.util.Locale;

public class GoogleMapsUrlBuilder {
    private static final String MATRIX_URL_FORMAT = "%s?origins=%.4f,%.4f&destinations=%.4f,%.4f&key=%s";
    private final GoogleMapsConfig config;

    public GoogleMapsUrlBuilder(GoogleMapsConfig config) {
        this.config = config;
    }

    public String buildDistanceMatrixUrl(Localization localization) {
        return String.format(Locale.US,
                MATRIX_URL_FORMAT,
                config.uri(),
                localization.from().latitude(), localization.from().longitude(),
                localization.to().latitude(), localization.to().longitude(),
                config.apiKey()
        );
    }
}
