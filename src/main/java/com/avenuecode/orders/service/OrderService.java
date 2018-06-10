package com.avenuecode.orders.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avenuecode.orders.domain.Order;
import com.avenuecode.orders.repository.OrderRepository;

/**
 * Implementation of service layer to fetch orders.
 */
@Service
public class OrderService {
  private static final Log LOG = LogFactory.getLog(OrderService.class);

  @Autowired
  private OrderRepository orderRepository;

  /**
   * Returns list of orders.
   */
  @Transactional(readOnly = true)
  public List<Order> listOrders() {
    if (LOG.isInfoEnabled()) {
      LOG.info("Fetching all orders.");
    }
    return orderRepository.findAll();
  }

  /**
   * Returns an order by order id.
   */
  @Transactional(readOnly = true)
  public Order getOrder(String orderId) {
    if (LOG.isInfoEnabled()) {
      LOG.info(String.format("Fetching orders by order id: %s.", orderId));
    }
    return orderRepository.findOne(orderId);
  }

  /**
   * Returns orders by status.
   * 
   * @param status status to filter by
   * @return list of orders
   */
  @Transactional(readOnly = true)
  public List<Order> getOrderByStatus(String status) {
    if (LOG.isInfoEnabled()) {
      LOG.info(String.format("Fetching orders by status: %s", status));
    }
    return this.orderRepository.findOrdersByStatus(status);
  }

  /**
   * Returns a list of discounted orders.
   */
  @Transactional(readOnly = true)
  public List<Order> findDiscountedOrders() {
    if (LOG.isInfoEnabled()) {
      LOG.info(String.format("Fetching all discounted orders."));
    }
    return this.orderRepository.findOrdersByDiscountIsNotNull();
  }
  
  
  /**
   * Returns a list of orders containing more than two products. 
   * @return
   */
  @Transactional(readOnly = true)
  public List<Order> findOrdersWithMoreThanTwoProducts() {
    if (LOG.isInfoEnabled()) {
      LOG.info(String.format("Fetching all orders with more than two products."));
    }
    return this.orderRepository.findOrdersWithMoreThanTwoProducts();
  }

}
