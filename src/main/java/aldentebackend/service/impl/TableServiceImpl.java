package aldentebackend.service.impl;

import aldentebackend.model.RestaurantTable;
import aldentebackend.repository.TableRepository;
import aldentebackend.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TableServiceImpl extends JPAServiceImpl<RestaurantTable> implements TableService {

    private final TableRepository tableRepository;

    @Autowired
    public TableServiceImpl(TableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }

    @Override
    protected JpaRepository<RestaurantTable, Long> getEntityRepository() {
        return tableRepository;
    }

    @Override
    public List<RestaurantTable> getTablesInSection(Long sectorId) {
        return tableRepository.getAllBySectorId(sectorId);
    }
}
