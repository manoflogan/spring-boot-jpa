package com.avenuecode.orders.service;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avenuecode.orders.domain.Product;
import com.avenuecode.orders.repository.ProductRepository;

@Service
public class ProductService {
  
  private static final Log LOG = LogFactory.getLog(ProductService.class);

  @Autowired
  private ProductRepository productRepository;

  @Transactional(readOnly = true)
  public List<Product> listProducts() {
    if (LOG.isInfoEnabled()) {
      LOG.info("Fetching all the products.");
    }
    return productRepository.findAll();
  }

  @Transactional(readOnly = true)
  public Product getProduct(String productId) {
    if (LOG.isInfoEnabled()) {
      LOG.info(String.format("Fetching all products by product id: %s.", productId));
    }
    return productRepository.findOne(productId);
  }

  @Transactional(readOnly = true)
  public List<Product> findProductGreaterThanPrice(BigDecimal price) {
    if (LOG.isInfoEnabled()) {
      LOG.info(String.format("Fetching all products greater than price: %s.", price));
    }
    return productRepository.findByPriceGreaterThan(price);
  }

}
