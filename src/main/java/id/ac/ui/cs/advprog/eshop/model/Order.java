package id.ac.ui.cs.advprog.eshop.model;
import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;

import java.util.List;
import lombok.*;

@Builder
@Getter
public class Order {
    String id;
    List<Product> products;
    long orderTime;
    String autor;
    String status;

    public Order(String id, List<Product> products, long orderTime, String author) {
        this.id = id;
        this.orderTime = orderTime;
        this.autor = author;
        this.status = OrderStatus.WAITING_PAYMENT.getValue();

        if (products.isEmpty()) {
            throw new IllegalArgumentException();
        } else {
            this.products = products;
        }
    }

    public Order(String id, List<Product> products, long timestamp, String author, String status) {
        this(id, products, timestamp, author);
        this.setStatus(status);
    }

    public void setStatus(String status) {
        if (OrderStatus.contains(status)) {
            this.status = status;
        } else {
            throw new IllegalArgumentException();
        }
    }
}