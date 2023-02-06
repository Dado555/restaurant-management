package aldentebackend.repository;

import aldentebackend.model.RestaurantTable;
import aldentebackend.model.Sector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectorRepository extends JpaRepository<Sector, Long> {

}
