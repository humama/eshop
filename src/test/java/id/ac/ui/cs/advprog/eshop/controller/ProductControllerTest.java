package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService service;

    @Mock
    private Model model;

    @InjectMocks
    private ProductController controller;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId("id1");
        product.setName("Test Product");
        product.setQuantity(5);
    }

    @Test
    void createProductPage_shouldReturnCreateView() {
        String view = controller.createProductPage(model);

        assertEquals("createProduct", view);
        verify(model, times(1)).addAttribute(eq("product"), any(Product.class));
    }

    @Test
    void createProductPost_shouldCallServiceAndRedirect() {
        String view = controller.createProductPost(product, model);

        verify(service, times(1)).create(product);
        assertEquals("redirect:/product/list", view);
    }

    @Test
    void productListPage_shouldReturnProductListView() {
        List<Product> products = Collections.singletonList(product);
        when(service.findAll()).thenReturn(products);

        String view = controller.productListPage(model);

        verify(model).addAttribute("products", products);
        assertEquals("productList", view);
    }

    @Test
    void editProductPage_whenProductExists_shouldReturnEditView() {
        when(service.findById("id1")).thenReturn(product);

        String view = controller.editProductPage("id1", model);

        verify(model).addAttribute("product", product);
        assertEquals("editProduct", view);
    }

    @Test
    void editProductPage_whenProductNotExists_shouldRedirect() {
        when(service.findById("missing")).thenReturn(null);

        String view = controller.editProductPage("missing", model);

        assertEquals("redirect:/product/list", view);
    }

    @Test
    void updateProductPost_shouldCallServiceAndRedirect() {
        String view = controller.updateProductPost(product);

        verify(service).update(product);
        assertEquals("redirect:/product/list", view);
    }

    @Test
    void deleteProduct_shouldCallServiceAndRedirect() {
        String view = controller.deleteProduct("id1");

        verify(service).delete("id1");
        assertEquals("redirect:/product/list", view);
    }

    @Test
    void home_shouldReturnIndexView() {
        String view = controller.home(model);

        assertEquals("index", view);
    }
}