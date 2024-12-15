package com.orders.repository;

import com.orders.domain.CalculatedOrders;
import com.orders.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalculatedOrdersRepository extends JpaRepository<CalculatedOrders, Long> {

}
