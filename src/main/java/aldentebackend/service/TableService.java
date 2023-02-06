package aldentebackend.service;

import aldentebackend.model.RestaurantTable;

import java.util.List;


public interface TableService extends JPAService<RestaurantTable> {
    List<RestaurantTable> getTablesInSection(Long sectorId);
}
