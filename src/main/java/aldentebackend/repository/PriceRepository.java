package aldentebackend.repository;

import aldentebackend.model.Price;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRepository extends JpaRepository<Price, Long> {

    Price findFirstByMenuItemIdAndDateLessThanEqualOrderByDateDesc(Long menuItemId, Long epoch);
}
