package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@Import(ThymeleafAutoConfiguration.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService service;

    @Test
    void getCreatePage_showsForm() throws Exception{
        mockMvc.perform(get("/product/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("createProduct"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    void postCreate_callsServiceAndRedirects() throws Exception{
        when(service.create(any(Product.class))).thenAnswer(i -> i.getArgument(0));

        mockMvc.perform(post("/product/create")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("productName", "P1")
                .param("productQuantity", "4")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"));

        verify(service, times(1)).create(any(Product.class));
    }

    @Test
    void getList_showsProducts() throws Exception{
        Product p = new Product(); p.setProductId("id"); p.setProductName("N"); p.setProductQuantity(2);
        when(service.findAll()).thenReturn(Collections.singletonList(p));

        mockMvc.perform(get("/product/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("productList"))
                .andExpect(model().attributeExists("products"));
    }

    @Test
    void editFound_rendersEdit() throws Exception{
        Product p = new Product(); p.setProductId("abcd"); p.setProductName("X"); p.setProductQuantity(1);
        when(service.findById("abcd")).thenReturn(p);

        mockMvc.perform(get("/product/edit/abcd"))
                .andExpect(status().isOk())
                .andExpect(view().name("editProduct"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    void editNotFound_redirectsToList() throws Exception{
        when(service.findById("missing")).thenReturn(null);

        mockMvc.perform(get("/product/edit/missing"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"));
    }

    @Test
    void postUpdate_callsServiceAndRedirects() throws Exception{
        when(service.update(any(Product.class))).thenReturn(new Product());

        mockMvc.perform(post("/product/update")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("productId", "id1")
                .param("productName", "Updated")
                .param("productQuantity", "3")
        )
                .andExpect(status().is3xxRedirection());

        verify(service, times(1)).update(any(Product.class));
    }

    @Test
    void getDelete_callsServiceAndRedirects() throws Exception{
        doReturn(true).when(service).delete("del1");

        mockMvc.perform(get("/product/delete/del1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"));

        verify(service, times(1)).delete("del1");
    }

    @Test
    void getRoot_returnsIndex() throws Exception{
        mockMvc.perform(get("/product/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }
}
