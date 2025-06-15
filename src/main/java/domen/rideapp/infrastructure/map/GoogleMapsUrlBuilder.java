package domen.rideapp.infrastructure.map;

import domen.rideapp.domain.model.Localization;
import domen.rideapp.infrastructure.GoogleMapsConfig;
import org.springframework.stereotype.Component;

@Component
public class GoogleMapsUrlBuilder {
    private final GoogleMapsConfig config;

    public GoogleMapsUrlBuilder(GoogleMapsConfig config) {
        this.config = config;
    }

    public String buildDistanceMatrixUrl(Localization localization) {
        return String.format(java.util.Locale.US,
                "%s?origins=%.4f,%.4f&destinations=%.4f,%.4f&key=%s",
                config.getUri(),
                localization.from().latitude(), localization.from().longitude(),
                localization.to().latitude(), localization.to().longitude(),
                config.getApiKey()
        );
    }
}
