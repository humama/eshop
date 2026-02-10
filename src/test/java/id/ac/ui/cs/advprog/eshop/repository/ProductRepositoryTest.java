package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;
    @BeforeEach
    void setUp() {
        productRepository = new ProductRepository();
    }
    @Test
    void testCreateAndFind(){
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }
    
    @Test
    void testFindAllIfEmpty(){
        Iterator <Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }
    @Test
    void testFindAllIfMoreThanOneProduct(){
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator <Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void update_existingProduct_updatesFields(){
        Product p = new Product();
        p.setProductId("id-1");
        p.setProductName("Old");
        p.setProductQuantity(5);
        productRepository.create(p);

        Product updated = new Product();
        updated.setProductId("id-1");
        updated.setProductName("New");
        updated.setProductQuantity(10);

        Product result = productRepository.update(updated);

        assertNotNull(result);
        assertEquals("New", result.getProductName());
        assertEquals(10, result.getProductQuantity());
    }

    @Test
    void update_nonExisting_returnsNull(){
        Product updated = new Product();
        updated.setProductId("missing");
        updated.setProductName("New");
        updated.setProductQuantity(10);

        Product result = productRepository.update(updated);
        assertNull(result);
    }

    @Test
    void delete_existing_removesAndReturnsTrue(){
        Product p = new Product();
        p.setProductId("id-2");
        p.setProductName("ToDelete");
        p.setProductQuantity(1);
        productRepository.create(p);

        boolean deleted = productRepository.deleteById("id-2");
        assertTrue(deleted);
        assertNull(productRepository.findById("id-2"));
    }

    @Test
    void delete_nonExisting_returnsFalse(){
        boolean deleted = productRepository.deleteById("nope");
        assertFalse(deleted);
    }
}