package com.avenuecode.orders.resource;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.avenuecode.orders.App;
import com.avenuecode.orders.domain.Order;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author krishnanand (Kartik Krishnanand)
 */
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
  TransactionalTestExecutionListener.class})
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= {App.class})
public class OrderResourceTest {
  
  @Autowired
  private OrderResource orderResource;
  
  private MockMvc mockMvc;
  
  @Before
  public void setUp() throws Exception {
    this.mockMvc = MockMvcBuilders.standaloneSetup(this.orderResource).build();
  }
  
  @Test
  public void testGetAllOrders() throws Exception {
    MvcResult result =
        this.mockMvc.perform(MockMvcRequestBuilders.get("/orders/").
                contentType(MediaType.APPLICATION_JSON_UTF8)).
            andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    String response = new String(result.getResponse().getContentAsByteArray());
    ObjectMapper mapper = new ObjectMapper();
    List<Order> orders = mapper.readValue(response, new TypeReference<List<Order>>(){});
    Assert.assertEquals(5,  orders.size());
  }
  
  @Test
  public void testGetByOrderId() throws Exception {
    MvcResult result =
        this.mockMvc.perform(MockMvcRequestBuilders.get("/orders/1/").
                contentType(MediaType.APPLICATION_JSON_UTF8)).
            andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    String response = new String(result.getResponse().getContentAsByteArray());
    ObjectMapper mapper = new ObjectMapper();
    Order actual = mapper.readValue(response, Order.class);
    Order expected = new Order();
    expected.setOrderNumber("RTL_1001");
    expected.setTaxPercent(new BigDecimal("10.00"));
    expected.setStatus("SHIPPED");
    Assert.assertEquals(actual, expected);
  }
  
  @Test
  public void testGetShippedOrders() throws Exception {
    MvcResult result =
        this.mockMvc.perform(MockMvcRequestBuilders.get("/orders/status/SHIPPED/").
                contentType(MediaType.APPLICATION_JSON_UTF8)).
            andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    String response = new String(result.getResponse().getContentAsByteArray());
    ObjectMapper mapper = new ObjectMapper();
    List<Order> actual = mapper.readValue(response, new TypeReference<List<Order>>(){});
    Assert.assertEquals(3, actual.size());
  }
  
  @Test
  public void testGetDiscountedOrders() throws Exception {
    MvcResult result =
        this.mockMvc.perform(MockMvcRequestBuilders.get("/orders/discounted/").
                contentType(MediaType.APPLICATION_JSON_UTF8)).
            andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    String response = new String(result.getResponse().getContentAsByteArray());
    ObjectMapper mapper = new ObjectMapper();
    List<Order> actual = mapper.readValue(response, new TypeReference<List<Order>>(){});
    Assert.assertEquals(2, actual.size());
  }
  
  @Test
  public void testGetOrdersWithMoreThanTwoProducts() throws Exception {
    MvcResult result =
        this.mockMvc.perform(MockMvcRequestBuilders.get("/orders/morethantwoproducts/").
                contentType(MediaType.APPLICATION_JSON_UTF8)).
            andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    String response = new String(result.getResponse().getContentAsByteArray());
    ObjectMapper mapper = new ObjectMapper();
    List<Order> actual = mapper.readValue(response, new TypeReference<List<Order>>(){});
    Assert.assertEquals(1, actual.size());
    Assert.assertEquals(3,  actual.get(0).getProducts().size());
  }

}
