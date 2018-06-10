package com.avenuecode.orders.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.avenuecode.orders.util.Util;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "products")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @JsonIgnore
    private String productId;

    @Column(unique = true, nullable = false, length = 10)
    private String upc;

    @Column(unique = true, nullable = false, length = 13)
    private String sku;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    public BigDecimal getPrice() {
      return price;
    }
    
    @ManyToMany(mappedBy="products", fetch=FetchType.EAGER)
    @JsonIgnore
    private List<Order> orders = new ArrayList<>();

    public List<Order> getOrders() {
      return orders;
    }

    public String getProductId() {
      return productId;
    }

    public void setProductId(String productId) {
      this.productId = productId;
    }

    public String getUpc() {
      return upc;
    }

    public void setUpc(String upc) {
      this.upc = upc;
    }

    public String getSku() {
      return sku;
    }

    public void setSku(String sku) {
      this.sku = sku;
    }

    public String getDescription() {
      return description;
    }

    public void setDescription(String description) {
      this.description = description;
    }

    public void setPrice(BigDecimal price) {
      this.price = price;
    }

    public void setOrders(List<Order> orders) {
      this.orders = orders;
    }
    
    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((description == null) ? 0 : description.hashCode());
      result = prime * result + ((price == null) ? 0 : price.hashCode());
      result = prime * result + ((productId == null) ? 0 : productId.hashCode());
      result = prime * result + ((sku == null) ? 0 : sku.hashCode());
      result = prime * result + ((upc == null) ? 0 : upc.hashCode());
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null) {
        return false;
      }
      if (!(obj instanceof Product)) {
        return false;
      }
      Product other = (Product) obj;
      return Util.isEqual(this.description, other.description) &&
          Util.isEqual(this.price, other.price) &&
          Util.isEqual(this.productId, other.productId) &&
          Util.isEqual(this.sku, other.sku) &&
          Util.isEqual(this.upc, other.upc);
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("Product [productId=");
      builder.append(productId);
      builder.append(", upc=");
      builder.append(upc);
      builder.append(", sku=");
      builder.append(sku);
      builder.append(", description=");
      builder.append(description);
      builder.append(", price=");
      builder.append(price);
      builder.append(", orders=");
      builder.append(orders);
      builder.append("]");
      return builder.toString();
    }

}
