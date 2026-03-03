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
        repository = new ProductRepository();
        service = new ProductServiceImpl(repository);
        // inject repository into service via reflection
        Field repoField = ProductServiceImpl.class.getDeclaredField("productRepository");
        repoField.setAccessible(true);
        repoField.set(service, repository);
    }

    @Test
    void create_generatesId_whenMissing(){
        Product p = new Product();
        p.setName("New");
        p.setQuantity(3);

        Product result = service.create(p);
        assertNotNull(result.getId());
        assertFalse(result.getId().isEmpty());
    }

    @Test
    void findAll_returnsCreatedProducts(){
        Product p1 = new Product(); p1.setName("A"); p1.setQuantity(1);
        Product p2 = new Product(); p2.setName("B"); p2.setQuantity(2);
        service.create(p1);
        service.create(p2);

        List<Product> all = service.findAll();
        assertEquals(2, all.size());
    }

    @Test
    void findById_and_update_and_delete_flow(){
        Product p = new Product(); p.setName("X"); p.setQuantity(5);
        Product created = service.create(p);
        String id = created.getId();

        Product found = service.findById(id);
        assertNotNull(found);
        assertEquals("X", found.getName());

        // update
        Product update = new Product();
        update.setId(id);
        update.setName("Y");
        update.setQuantity(9);
        Product updated = service.update(update);
        assertNotNull(updated);
        assertEquals("Y", updated.getName());
        assertEquals(9, updated.getQuantity());

        // delete
        boolean deleted = service.delete(id);
        assertTrue(deleted);
        assertNull(service.findById(id));
    }
}
