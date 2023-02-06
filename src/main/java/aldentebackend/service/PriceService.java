package aldentebackend.service;

import aldentebackend.model.Price;

public interface PriceService extends JPAService<Price> {

    Price findCurrentPriceForMenuItem(Long menuItemId);

    Price findCurrentPriceForMenuItem(Long menuItemId, Long time);
}
