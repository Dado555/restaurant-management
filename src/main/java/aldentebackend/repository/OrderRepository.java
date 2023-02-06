package aldentebackend.repository;

import aldentebackend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> getOrdersByDateAfterAndDateBefore(Long start, Long end);

    @Query(value = "select order from Order order where order.table.id=:id and order.status <> 'FINISHED'")
    List<Order> getOrdersForTable(@Param("id") Long id);

    @Query(value = "select order from Order order where order.status='IN_PROGRESS' order by order.id")
    Collection<Order> findOrdersByStatusInProgress();

    @Query(value = "select order from Order order where order.status='FOR_PREPARATION' or order.status='IN_PROGRESS' order by order.id")
    Collection<Order> findOrdersByStatusForPreparationOrStatusInProgress();

    @Query(value = "select order from Order order where order.status='FOR_PREPARATION' or order.status='IN_PROGRESS' or order.status='READY' or order.status='DELIVERED' order by order.id")
    Collection<Order> findAllActiveOrders();

    @Query(value = "select order from Order order where order.status='FOR_PREPARATION' order by order.id")
    Collection<Order> findOrdersByStatusForPreparation();

    @Query(value = "select o from Order o where o.date > :start and o.date < :end")
    Collection<Order> findAllInRange(@Param("start") long start, @Param("end") long end);
}
