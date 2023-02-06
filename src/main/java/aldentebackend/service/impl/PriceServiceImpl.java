package aldentebackend.service.impl;

import aldentebackend.exception.BadRequestException;
import aldentebackend.model.Price;
import aldentebackend.repository.PriceRepository;
import aldentebackend.service.PriceService;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PriceServiceImpl extends JPAServiceImpl<Price> implements PriceService {

    private final PriceRepository priceRepository;

    @Override
    protected JpaRepository<Price, Long> getEntityRepository() {
        return priceRepository;
    }

    @Override
    public Price findCurrentPriceForMenuItem(Long menuItemId) {
        Price price = priceRepository.findFirstByMenuItemIdAndDateLessThanEqualOrderByDateDesc(menuItemId, System.currentTimeMillis());
        if(price == null)
            throw new BadRequestException("Wrong menuItemId passed");
        return price;
    }

    @Override
    public Price findCurrentPriceForMenuItem(Long menuItemId, Long time) {
        Price price = priceRepository.findFirstByMenuItemIdAndDateLessThanEqualOrderByDateDesc(menuItemId, time);
        if(price == null)
            throw new BadRequestException("Wrong menuItemId passed");
        return price;
    }
}
