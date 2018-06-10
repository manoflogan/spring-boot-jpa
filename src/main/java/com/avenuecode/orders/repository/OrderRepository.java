package com.avenuecode.orders.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.avenuecode.orders.domain.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Serializable> {
  
  /**
   * Returns all orders by status.
   * 
   * @param status status to filter orders
   * @return list of orders having specified status
   */
  List<Order> findOrdersByStatus(String status);
  
  /**
   * Returns orders whose discount field does not have {@code NULL} value.
   */
  List<Order> findOrdersByDiscountIsNotNull();
  
  /**
   * Returns orders which have more than 2 products.
   */
  @Query("SELECT o FROM Order o WHERE size(o.products) > 2")
  List<Order> findOrdersWithMoreThanTwoProducts();
}
