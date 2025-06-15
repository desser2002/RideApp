package domen.rideapp.domain.service;

import domen.rideapp.domain.model.Localization;

public interface PricingService {
    double getCost(Localization localization);
}
