package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProductServiceImplTest {

    private ProductServiceImpl service;
    private ProductRepository repository;

    @BeforeEach
    void setUp() throws Exception{
        service = new ProductServiceImpl();
        repository = new ProductRepository();
        // inject repository into service via reflection
        Field repoField = ProductServiceImpl.class.getDeclaredField("productRepository");
        repoField.setAccessible(true);
        repoField.set(service, repository);
    }

    @Test
    void create_generatesId_whenMissing(){
        Product p = new Product();
        p.setProductName("New");
        p.setProductQuantity(3);

        Product result = service.create(p);
        assertNotNull(result.getProductId());
        assertFalse(result.getProductId().isEmpty());
    }

    @Test
    void findAll_returnsCreatedProducts(){
        Product p1 = new Product(); p1.setProductName("A"); p1.setProductQuantity(1);
        Product p2 = new Product(); p2.setProductName("B"); p2.setProductQuantity(2);
        service.create(p1);
        service.create(p2);

        List<Product> all = service.findAll();
        assertEquals(2, all.size());
    }

    @Test
    void findById_and_update_and_delete_flow(){
        Product p = new Product(); p.setProductName("X"); p.setProductQuantity(5);
        Product created = service.create(p);
        String id = created.getProductId();

        Product found = service.findById(id);
        assertNotNull(found);
        assertEquals("X", found.getProductName());

        // update
        Product update = new Product();
        update.setProductId(id);
        update.setProductName("Y");
        update.setProductQuantity(9);
        Product updated = service.update(update);
        assertNotNull(updated);
        assertEquals("Y", updated.getProductName());
        assertEquals(9, updated.getProductQuantity());

        // delete
        boolean deleted = service.delete(id);
        assertTrue(deleted);
        assertNull(service.findById(id));
    }
}
