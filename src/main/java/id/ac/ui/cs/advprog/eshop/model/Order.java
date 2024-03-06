package id.ac.ui.cs.advprog.eshop.model;

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
    }

    public Order(String id, List<Product> products, long timestamp, String author, String status) {
    }
}
