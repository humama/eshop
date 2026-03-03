package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

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
        product.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setName("Sampo Cap Bambang");
        product.setQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getId(), savedProduct.getId());
        assertEquals(product.getName(), savedProduct.getName());
        assertEquals(product.getQuantity(), savedProduct.getQuantity());
    }
    
    @Test
    void testFindAllIfEmpty(){
        Iterator <Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }
    @Test
    void testFindAllIfMoreThanOneProduct(){
        Product product1 = new Product();
        product1.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setName("Sampo Cap Bambang");
        product1.setQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setName("Sampo Cap Usep");
        product2.setQuantity(50);
        productRepository.create(product2);

        Iterator <Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getId(), savedProduct.getId());
        savedProduct = productIterator.next();
        assertEquals(product2.getId(), savedProduct.getId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void update_existingProduct_updatesFields(){
        Product p = new Product();
        p.setId("id-1");
        p.setName("Old");
        p.setQuantity(5);
        productRepository.create(p);

        Product updated = new Product();
        updated.setId("id-1");
        updated.setName("New");
        updated.setQuantity(10);

        Product result = productRepository.update(updated);

        assertNotNull(result);
        assertEquals("New", result.getName());
        assertEquals(10, result.getQuantity());
    }

    @Test
    void update_nonExisting_returnsNull(){
        Product updated = new Product();
        updated.setId("missing");
        updated.setName("New");
        updated.setQuantity(10);

        Product result = productRepository.update(updated);
        assertNull(result);
    }

    @Test
    void delete_existing_removesAndReturnsTrue(){
        Product p = new Product();
        p.setId("id-2");
        p.setName("ToDelete");
        p.setQuantity(1);
        productRepository.create(p);

        boolean deleted = productRepository.delete("id-2");
        assertTrue(deleted);
        assertNull(productRepository.findById("id-2"));
    }

    @Test
    void delete_nonExisting_returnsFalse(){
        boolean deleted = productRepository.delete("nope");
        assertFalse(deleted);
    }
}