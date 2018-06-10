package com.avenuecode.orders.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.avenuecode.orders.App;
import com.avenuecode.orders.domain.Order;

/**
 * @author krishnanand (Kartik Krishnanand)
 */
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
  TransactionalTestExecutionListener.class})
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
public class OrderServiceTest {
  
  @Autowired
  private OrderService orderService;
  
  @Test
  public void testGetShippedOrders() {
    List<Order> orders = this.orderService.getOrderByStatus("SHIPPED");
    Assert.assertEquals(3,  orders.size());
  }
  
  @Test
  public void testFindOrdersByDiscountIsNotNull() throws Exception {
    List<Order> orders = this.orderService.findDiscountedOrders();
    Assert.assertEquals(2,  orders.size());
  }
  
  @Test
  public void testFindOrdersWithMoreThanTwoProducts() throws Exception {
    List<Order> orders = this.orderService.findOrdersWithMoreThanTwoProducts();
    Assert.assertEquals(1,  orders.size());
  }
}