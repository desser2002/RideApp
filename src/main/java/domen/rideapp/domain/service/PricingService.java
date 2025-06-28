package domen.rideapp.domain.service;

import domen.rideapp.domain.model.Localization;
import domen.rideapp.domain.model.Price;

public interface PricingService {
    Price getCost(Localization localization);
}
