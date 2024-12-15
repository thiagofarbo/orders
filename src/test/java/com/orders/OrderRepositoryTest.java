//package com.orders;
//
//import com.orders.domain.Order;
//import com.orders.domain.OrderItem;
//import com.orders.repository.OrderRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//@SpringBootTest
//public class OrderRepositoryTest {
//
//    @Autowired
//    private OrderRepository orderRepository;
//
//    @Test
//    public void testSaveOrderWithItems() {
//        Order order = new Order();
////        order.setExternalId("12345");
//        order.setTotalValue(100.0);
//        order.setStatus("NEW");
//        order.setCreatedAt(LocalDateTime.now());
//
//        OrderItem item1 = new OrderItem("Produto 1", 30.0, 1L);
//        OrderItem item2 = new OrderItem("Produto 2", 70.0, 1L);
//
//        order.setItems(List.of(item1, item2));
//
//        Order savedOrder = orderRepository.save(order);
//
//        assertNotNull(savedOrder.getId());
//        assertEquals(2, savedOrder.getItems().size());
//    }
//}
