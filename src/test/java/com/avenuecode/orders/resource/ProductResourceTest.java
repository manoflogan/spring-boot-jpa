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
import com.avenuecode.orders.domain.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author krishnanand (Kartik Krishnanand)
 */
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
  TransactionalTestExecutionListener.class})
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= {App.class})
public class ProductResourceTest {
  
  @Autowired
  private ProductResource productResource;
  
  private MockMvc mockMvc;
  
  @Before
  public void setUp() throws Exception {
    this.mockMvc = MockMvcBuilders.standaloneSetup(this.productResource).build();
  }
  
  @Test
  public void testGetProducts() throws Exception {
    MvcResult result =
        this.mockMvc.perform(MockMvcRequestBuilders.get("/products/").
                contentType(MediaType.APPLICATION_JSON_UTF8)).
            andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    String response = new String(result.getResponse().getContentAsByteArray());
    ObjectMapper mapper = new ObjectMapper();
    List<Product> products = mapper.readValue(response, new TypeReference<List<Product>>(){});
    Assert.assertEquals(5, products.size());
  }
  
  @Test
  public void testGetProductsWithPriceGreaterThanThirty() throws Exception {
    MvcResult result =
        this.mockMvc.perform(MockMvcRequestBuilders.get("/products/greaterthanprice/30").
                contentType(MediaType.APPLICATION_JSON_UTF8)).
            andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    String response = new String(result.getResponse().getContentAsByteArray());
    ObjectMapper mapper = new ObjectMapper();
    List<Product> products = mapper.readValue(response, new TypeReference<List<Product>>(){});
    Assert.assertEquals(3, products.size());
  }
    
    @Test
    public void testGetProductsByProductId() throws Exception {
      MvcResult result =
          this.mockMvc.perform(MockMvcRequestBuilders.get("/products/1/").
                  contentType(MediaType.APPLICATION_JSON_UTF8)).
              andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
      String response = new String(result.getResponse().getContentAsByteArray());
      ObjectMapper mapper = new ObjectMapper();
      Product actual = mapper.readValue(response, Product.class);
      Product expected = new Product();
      expected.setUpc("1257833283");
      expected.setSku("9394550220002");
      expected.setDescription("Diva Jeans");
      expected.setPrice(new BigDecimal("39.99"));
      Assert.assertEquals(expected, actual);
  }

}
