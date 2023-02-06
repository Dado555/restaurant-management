package aldentebackend.repository;

import aldentebackend.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query(value = "select oi from OrderItem oi " +
            "left join fetch oi.order as o " +
            "left join fetch oi.menuItem as mi " +
            "left join fetch mi.item as i " +
            "where o.date > :start " +
            "and o.date < :end")
    Collection<OrderItem> findAllInRange(@Param("start") long start, @Param("end") long end);

    @Query(value = "select item from OrderItem item where item.status='FOR_PREPARATION'")
    Collection<OrderItem> findByStatusForPreparation();

    @Query(value = "select item from OrderItem item left join fetch item.order as o where item.status='DELIVERED' and o.status = 'FINISHED'")
    Collection<OrderItem> findDeliveredAndOrderFinished();
}
