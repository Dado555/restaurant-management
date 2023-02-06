package aldentebackend.service.impl;

import aldentebackend.model.Food;
import aldentebackend.repository.FoodRepository;
import aldentebackend.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class FoodServiceImpl extends JPAServiceImpl<Food> implements FoodService {

    private final FoodRepository foodRepository;

    @Autowired
    public FoodServiceImpl(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    @Override
    protected JpaRepository<Food, Long> getEntityRepository() {
        return foodRepository;
    }
}
