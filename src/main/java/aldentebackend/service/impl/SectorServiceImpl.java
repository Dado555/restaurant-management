package aldentebackend.service.impl;

import aldentebackend.model.RestaurantTable;
import aldentebackend.model.Sector;
import aldentebackend.repository.SectorRepository;
import aldentebackend.repository.TableRepository;
import aldentebackend.service.SectorService;
import aldentebackend.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class SectorServiceImpl extends JPAServiceImpl<Sector> implements SectorService {

    private final SectorRepository sectorRepository;

    @Autowired
    public SectorServiceImpl(SectorRepository sectorRepository) {
        this.sectorRepository = sectorRepository;
    }

    @Override
    protected JpaRepository<Sector, Long> getEntityRepository() {
        return sectorRepository;
    }
}
