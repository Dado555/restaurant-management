package aldentebackend.service.impl;

import aldentebackend.model.Drink;
import aldentebackend.repository.DrinkRepository;
import aldentebackend.service.DrinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class DrinkServiceImpl extends JPAServiceImpl<Drink> implements DrinkService {

    private final DrinkRepository drinkRepository;

    @Autowired
    public DrinkServiceImpl(DrinkRepository drinkRepository) {
        this.drinkRepository = drinkRepository;
    }

    @Override
    protected JpaRepository<Drink, Long> getEntityRepository() {
        return drinkRepository;
    }
}
