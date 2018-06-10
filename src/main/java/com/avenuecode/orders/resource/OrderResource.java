package com.avenuecode.orders.resource;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avenuecode.orders.domain.Order;
import com.avenuecode.orders.service.OrderService;

/**
 * Encapsulates all end points related to orders.
 */
@RestController
@RequestMapping("/orders")
public class OrderResource {

  private static final Log LOG = LogFactory.getLog(OrderResource.class);

  @Autowired
  private OrderService orderService;

  /**
   * Returns all orders.
   */
  @GetMapping
  public ResponseEntity<List<Order>> listOrders() {
    if (LOG.isInfoEnabled()) {
      LOG.info("Fetching all orders.");
    }
    return ok(orderService.listOrders());
  }

  /**
   * Returns order by order id.
   */
  @GetMapping(value = "/{orderId}")
  public ResponseEntity<Order> getOrder(@PathVariable String orderId) {
    Order order = orderService.getOrder(orderId);
    if (order == null) {
      LOG.warn(String.format("No order for an order id: %s.", orderId));
      return notFound().build();
    }
    if (LOG.isInfoEnabled()) {
      LOG.info(String.format("Fetched object by %s: %s", orderId, order));
    }
    return ok(order);
  }

  /**
   * Returns order by status.
   */
  @GetMapping(value = "/status/{status}")
  public ResponseEntity<List<Order>> getOrdersByStatus(@PathVariable String status) {
    return returnOkOrNotFound(orderService.getOrderByStatus(status));
  }

  /**
   * Returns all discounted orders.
   */
  @GetMapping(value = "/discounted")
  public ResponseEntity<List<Order>> getDiscountedOrders() {
    return returnOkOrNotFound(orderService.findDiscountedOrders());
  }

  /**
   * Returns all the orders that has more than two products.
   */
  @GetMapping(value = "/morethantwoproducts")
  public ResponseEntity<List<Order>> getOrderWithMoreThanTwoProducts() {
    return returnOkOrNotFound(orderService.findOrdersWithMoreThanTwoProducts());
  }

  /**
   * Returns a response entity representing 200 OK status or 400 status.
   * 
   * @param objects list of objects to returned as a response body
   * @return response entity
   */
  protected <T> ResponseEntity<List<T>> returnOkOrNotFound(List<T> objects) {
    if (objects == null || objects.isEmpty()) {
      LOG.warn("No objects were found for the request");
      return notFound().build();
    }
    LOG.warn("The returned response = " + objects);
    return ok(objects);
  }
}
