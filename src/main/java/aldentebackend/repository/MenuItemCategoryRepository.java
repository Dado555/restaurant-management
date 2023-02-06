package aldentebackend.repository;

import aldentebackend.model.MenuItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MenuItemCategoryRepository extends JpaRepository<MenuItemCategory, Long> {

    @Query(value = "select c from MenuItemCategory c " +
            "left join fetch c.menuItems mi " +
            "left join fetch mi.item " +
            "left join fetch mi.prices " +
            "where c.name = ?1")
    MenuItemCategory findByName(String name);

    @Query(value = "select c from MenuItemCategory c " +
            "left join fetch c.menuItems mi " +
            "left join fetch mi.item " +
            "left join fetch mi.prices " +
            "where c.id = ?1")
    MenuItemCategory findByIdFetch(Long id);
}
