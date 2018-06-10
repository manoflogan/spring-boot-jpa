package com.avenuecode.orders.resource;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avenuecode.orders.domain.Product;
import com.avenuecode.orders.service.ProductService;

/**
 * Instance of this class encapsulates all endpoints related to Product.
 */
@RestController
@RequestMapping("/products")
public class ProductResource {

  private static final Log LOG = LogFactory.getLog(ProductResource.class);

  @Autowired
  private ProductService productService;

  /**
   * Returns a list of all products.
   */
  @GetMapping
  public ResponseEntity<List<Product>> listProducts() {
    return ok(productService.listProducts());
  }

  /**
   * Returns a list of all products having price greater than path variable value.
   */
  @GetMapping(value = "/greaterthanprice/{price}")
  public ResponseEntity<List<Product>> findProductGreater(@PathVariable BigDecimal price) {
    if (LOG.isInfoEnabled()) {
      LOG.info(String.format("Fetching all the products having price greater than: {}", price));
    }
    return ok(productService.findProductGreaterThanPrice(price));
  }

  /**
   * Fetches product by product id.
   */
  @GetMapping(value = "/{productId}")
  public ResponseEntity<Product> getProduct(@PathVariable String productId) {
    Product product = productService.getProduct(productId);
    if (product == null) {
      LOG.warn(String.format("No objects were found for the product id: %s", productId));
      return notFound().build();
    }
    if (LOG.isInfoEnabled()) {
      LOG.info(String.format("Fetching product by id: %s = ", productId, product));
    }
    return ok(product);
  }
}
