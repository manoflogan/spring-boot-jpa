package com.avenuecode.orders.service;

import java.math.BigDecimal;

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
import com.avenuecode.orders.domain.Product;

/**
 * @author krishnanand (Kartik Krishnanand)
 */
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
  TransactionalTestExecutionListener.class})
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
public class ProductServiceTest {
  
  @Autowired
  private ProductService productService;
  
  @Test
  public void testListProducts() throws Exception {
    Assert.assertEquals(5,  this.productService.listProducts().size());
  }
  
  @Test
  public void testFindOne() throws Exception {
    Product expected =new Product();
    expected.setProductId("1");
    expected.setUpc("1257833283");
    expected.setDescription("Diva Jeans");
    expected.setSku("9394550220002");
    expected.setPrice(new BigDecimal("39.99"));
    Product actual = this.productService.getProduct("1");
    Assert.assertEquals(expected, actual);
  }
  
  @Test
  public void testFindProductsWithPriceMoreThanThirty() {
    Assert.assertEquals(
        3,
        this.productService.findProductGreaterThanPrice(new BigDecimal("30")).size());
    
  }

}
