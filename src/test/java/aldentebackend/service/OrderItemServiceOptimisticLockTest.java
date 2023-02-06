package aldentebackend.service;

import aldentebackend.constants.OrderItemConstants;
import aldentebackend.model.*;
import aldentebackend.model.enums.OrderItemStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertThrows;


@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class OrderItemServiceOptimisticLockTest {

    @Autowired
    private OrderItemService orderItemService;

    @Test
    public void saveOrderItem_calledWithMultipleThreads_ObjectOptimisticLockingFailureException() throws Throwable {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        Future<?> future1 = executor.submit(() -> {
            OrderItem orderItem = orderItemService.findOne(OrderItemConstants.ID);
            orderItem.setStatus(OrderItemStatus.IN_PROGRESS);
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            assertThrows(ObjectOptimisticLockingFailureException.class, () -> orderItemService.save(orderItem));
        });

        executor.submit(() -> {
            OrderItem orderItem = orderItemService.findOne(OrderItemConstants.ID);
            orderItem.setStatus(OrderItemStatus.IN_PROGRESS);
            orderItemService.save(orderItem);
        });

        try {
            future1.get(); // podize ExecutionException za bilo koji izuzetak iz prvog child threada
        } catch (ExecutionException e) {
            System.out.println("Exception from thread " + e.getCause().getClass()); // u pitanju je bas ObjectOptimisticLockingFailureException
            throw e.getCause();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }

}
