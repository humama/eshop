package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();

    public Product create(Product product){
        productData.add(product);
        return product;
    }

    public Iterator<Product> findAll(){
        return productData.iterator();
    }

    public boolean deleteById(String id){
        if(id == null) return false;
        return productData.removeIf(p -> id.equals(p.getProductId()));
    }

    public Product findById(String id){
        if(id == null) return null;
        for(Product p : productData){
            if(id.equals(p.getProductId())) return p;
        }
    }

    public Product update(Product product){
        if(product == null || product.getProductId() == null) return null;
        for(int i = 0; i < productData.size(); i++){
            Product p = productData.get(i);
            if(p.getProductId() != null && p.getProductId().equals(product.getProductId())){
                p.setProductName(product.getProductName());
                p.setProductQuantity(product.getProductQuantity());
                productData.set(i, p);
                return p;
            }
        }
        return null;
    }
}
