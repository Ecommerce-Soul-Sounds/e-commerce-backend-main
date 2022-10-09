package com.revature.test.controllersTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import com.revature.util.ClientMessageUtil;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.revature.controllers.ProductController;
import com.revature.dtos.ProductInfo;
import com.revature.models.Product;
import com.revature.services.ProductService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductControllerTest {

    private static Product mockProduct1, mockProduct2;
    private static ProductInfo mockProductInfo1, mockProductInfo2;
    private static List<Product> dummyDb;
    private static List<ProductInfo> dummyDb2;
    ObjectMapper om = new ObjectMapper();

    @Autowired
    ProductController productController;

    @Autowired
    private MockMvc mockmvc;

    @MockBean
    private static ProductService productService;

    @SuppressWarnings("deprecation")
    public boolean isValidJSON(final String json) {
        boolean valid = false;
        try {
            final JsonParser parser = new ObjectMapper().getFactory().createParser(json);
            while (parser.nextToken() != null) {
            }
            valid = true;
        } catch (JsonParseException jpe) {
            jpe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return valid;
    }

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        System.out.println("setUpBeforeClass() :: building test objects...");

        mockProduct1 = new Product(1, 1, 1.00, "cat", "brand", "desc1", "image1", "name1");
        mockProduct2 = new Product(2, 2, 2.00, "cat", "brand", "desc2", "image2", "name2");

        mockProductInfo1 = new ProductInfo(1, 1);
        mockProductInfo2 = new ProductInfo(2, 2);

        dummyDb = new ArrayList<>();
        dummyDb.add(mockProduct1);
        dummyDb.add(mockProduct2);

        dummyDb2 = new ArrayList<>();
        dummyDb2.add(mockProductInfo1);
        dummyDb2.add(mockProductInfo2);

    }

    @Test
    @Order(1)
    @DisplayName("1. AppContext Sanity Test")
    public void contextLoads() throws Exception {

        assertThat(productController).isNotNull();

    }

    @Test
    @Order(2)
    @DisplayName("2. Delete product")
    void testDeleteProduct() throws Exception {
        int id = 3;
        Product newProduct = new Product(id,3, 3.00, "cat3", "brand3", "desc", "image3", "name3");
        
        when(productService.delete(newProduct)).thenReturn(true);
        when(productService.findById(id)).thenReturn(Optional.of(newProduct));
        
             
        mockmvc.perform(delete("/api/product/{id}", id))
                .andExpect(status().isOk()).andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.price").value(newProduct.getPrice()))
                .andExpect(jsonPath("$.category").value(newProduct.getCategory()))
                .andExpect(jsonPath("$.brand").value(newProduct.getBrand()))
                .andExpect(jsonPath("$.description").value(newProduct.getDescription()))
                .andExpect(jsonPath("$.image").value(newProduct.getImage()))
                .andExpect(jsonPath("$.name").value(newProduct.getName()))
                .andDo(print());

    }

    @Test
    @Order(3)
    @DisplayName("3. Get inventory")
    void testGetInventory() throws Exception{

        when(productService.findAll()).thenReturn(dummyDb);
        
        mockmvc.perform(get("/api/product"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size()").value(dummyDb.size()))
        .andDo(print());


    }

    @Test
    @Order(4)
    @DisplayName("4. Get product by id")
    void testGetProductById() throws Exception {
        int id = 3;
        Product newProduct = new Product(id, 3, 3.00, "cat3", "brand3", "desc", "image3", "name3");

        when(productService.findById(id)).thenReturn(Optional.of(newProduct));

        mockmvc.perform(get("/api/product/{id}", id))
                .andExpect(status().isOk()).andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.price").value(newProduct.getPrice()))
                .andExpect(jsonPath("$.category").value(newProduct.getCategory()))
                .andExpect(jsonPath("$.brand").value(newProduct.getBrand()))
                .andExpect(jsonPath("$.description").value(newProduct.getDescription()))
                .andExpect(jsonPath("$.image").value(newProduct.getImage()))
                .andExpect(jsonPath("$.name").value(newProduct.getName()))
                .andDo(print());

    }

    @Test
    @Order(5)
    @DisplayName("5. Get products by brand")
    void testGetProductsByBrand() throws Exception {
        int id = 3;
        Product newProduct = new Product(id, 3, 3.00, "cat3", "brand3", "desc", "image3", "name3");
        MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
        paramsMap.add("brand", newProduct.getBrand());

        when(productService.getProductsByBrand(newProduct.getBrand())).thenReturn(dummyDb);

        mockmvc.perform(get("/api/product/brand").params(paramsMap))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(dummyDb.size()))
                .andDo(print());

    }

    @Test
    @Order(6)
    @DisplayName("6. Get product by type")
    void testGetProductsByType() throws Exception {
        int id = 3;
        Product newProduct = new Product(id, 3, 3.00, "cat", "brand", "desc", "image3", "name3");
        MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
        paramsMap.add("category", newProduct.getCategory());

        when(productService.getProductsByCategory(newProduct.getCategory())).thenReturn(dummyDb);

        mockmvc.perform(get("/api/product/category").params(paramsMap))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(dummyDb.size()))
                .andDo(print());

    }


    @Test
    @Order(7)
    @DisplayName("7. Update product")
    void testUpdateProduct() throws Exception{
        when(productService.update(mockProduct1)).thenReturn((mockProduct1));
        RequestBuilder request = MockMvcRequestBuilders.put("/api/product/update")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(om.writeValueAsString(mockProduct1))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockmvc.perform(request).andReturn();
        assertEquals((om.writeValueAsString(ClientMessageUtil.UPDATE_SUCCESSFUL)), result.getResponse().getContentAsString());

    }

    @Test
    @Order(8)
    @DisplayName("8. Upsert prodcut")
    void testUpsert() throws Exception {
        mockProduct1.setName("newName");

        when(productService.save(mockProduct1)).thenReturn((mockProduct1));
        mockmvc.perform(put("/api/product").contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(mockProduct1)))
                .andExpect(status().isOk()).andExpect(jsonPath("$.id").value(mockProduct1.getId()))
                .andExpect(jsonPath("$.price").value(mockProduct1.getPrice()))
                .andExpect(jsonPath("$.category").value(mockProduct1.getCategory()))
                .andExpect(jsonPath("$.brand").value(mockProduct1.getBrand()))
                .andExpect(jsonPath("$.description").value(mockProduct1.getDescription()))
                .andExpect(jsonPath("$.image").value(mockProduct1.getImage()))
                .andExpect(jsonPath("$.name").value(mockProduct1.getName()))
                .andDo(print());
    }
}
