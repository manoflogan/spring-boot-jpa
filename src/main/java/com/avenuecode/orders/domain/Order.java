package com.avenuecode.orders.domain;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static java.math.BigDecimal.ROUND_FLOOR;
import static java.math.BigInteger.ZERO;
import static javax.persistence.CascadeType.ALL;
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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.avenuecode.orders.util.Util;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@Entity
@Table(name = "orders")
@JsonInclude(NON_NULL)
public class Order implements Serializable {

    public static final int PRECISION = 2;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @JsonIgnore
    private String orderId;

    @Column(unique = true, nullable = false, length = 8)
    private String orderNumber;

    @Column
    private BigDecimal discount;

    @Column(nullable = false)
    private BigDecimal taxPercent;

    private BigDecimal total;

    private BigDecimal totalTax;

    private BigDecimal grandTotal;

    @Column(length = 10)
    private String status;

    @ManyToMany(cascade = ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "order_product",
            joinColumns = @JoinColumn(name = "order_id", updatable = false, nullable = false),
            inverseJoinColumns = @JoinColumn(name = "product_id", updatable = false, nullable = false)
    )
    private List<Product> products = new ArrayList<>();

    public BigDecimal getTotal() {
        BigDecimal total = new BigDecimal(ZERO);
        if (products == null || products.isEmpty()) {
          return total;
        }
        for (Product product : products) {
            total = total.add(product.getPrice());
        }
        return scaled(total);
    }

    public BigDecimal getTotalTax() {
        return scaled(getTotal().multiply(taxPercent.divide(new BigDecimal("100"))));
    }

    public BigDecimal getGrandTotal() {
        BigDecimal total = this.getTotal().add(getTotalTax());
        if (discount != null) {
            return scaled(total.subtract(discount));
        }
        return scaled(total);
    }

    private BigDecimal scaled(BigDecimal value) {
        return value.setScale(PRECISION, ROUND_FLOOR);
    }

    public String getOrderId() {
      return orderId;
    }

    public void setOrderId(String orderId) {
      this.orderId = orderId;
    }

    public String getOrderNumber() {
      return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
      this.orderNumber = orderNumber;
    }

    public BigDecimal getDiscount() {
      return discount;
    }

    public void setDiscount(BigDecimal discount) {
      this.discount = discount;
    }

    public BigDecimal getTaxPercent() {
      return taxPercent;
    }

    public void setTaxPercent(BigDecimal taxPercent) {
      this.taxPercent = taxPercent;
    }

    public String getStatus() {
      return status;
    }

    public void setStatus(String status) {
      this.status = status;
    }

    public List<Product> getProducts() {
      return products;
    }

    public void setProducts(List<Product> products) {
      this.products = products;
    }

    public void setTotal(BigDecimal total) {
      this.total = total;
    }

    public void setTotalTax(BigDecimal totalTax) {
      this.totalTax = totalTax;
    }

    public void setGrandTotal(BigDecimal grandTotal) {
      this.grandTotal = grandTotal;
    }
    
    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((discount == null) ? 0 : discount.hashCode());
      result = prime * result + ((grandTotal == null) ? 0 : grandTotal.hashCode());
      result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
      result = prime * result + ((orderNumber == null) ? 0 : orderNumber.hashCode());
      result = prime * result + ((status == null) ? 0 : status.hashCode());
      result = prime * result + ((taxPercent == null) ? 0 : taxPercent.hashCode());
      result = prime * result + ((total == null) ? 0 : total.hashCode());
      result = prime * result + ((totalTax == null) ? 0 : totalTax.hashCode());
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
      if (!Order.class.isAssignableFrom(this.getClass())) {
        return false;
      }
      Order other = (Order) obj;
      return Util.isEqual(this.discount, other.getDiscount()) &&
          Util.isEqual(this.orderId, other.getOrderId()) &&
          Util.isEqual(this.orderNumber, other.getOrderNumber()) &&
          Util.isEqual(this.status, other.getStatus()) &&
          Util.isEqual(this.taxPercent, other.getTaxPercent());
    }
    
    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("Order [orderId=");
      builder.append(orderId);
      builder.append(", orderNumber=");
      builder.append(orderNumber);
      builder.append(", discount=");
      builder.append(discount);
      builder.append(", taxPercent=");
      builder.append(taxPercent);
      builder.append(", total=");
      builder.append(total);
      builder.append(", totalTax=");
      builder.append(totalTax);
      builder.append(", grandTotal=");
      builder.append(grandTotal);
      builder.append(", status=");
      builder.append(status);
      builder.append("]");
      return builder.toString();
    }

}
