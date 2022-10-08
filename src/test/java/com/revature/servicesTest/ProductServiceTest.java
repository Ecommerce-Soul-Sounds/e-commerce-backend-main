package com.revature.servicesTest;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revature.dtos.ProductInfo;
import com.revature.models.Product;
import com.revature.repositories.ProductRepository;
import com.revature.services.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductServiceTest {
    @Mock
    private static ProductRepository mockProductDao;
    
    @InjectMocks
    private static ProductService productServ;

    private static Product product1, product2;
    private static List<Product> dummyDb;

    @BeforeAll
    static void setupBeforeClass() {
        mockProductDao = Mockito.mock(ProductRepository.class);
        productServ = new ProductService(mockProductDao);

        product1 = new Product(1, 1, 1.00, "cat", "brand", "desc1", "image1", "name1");
        product2 = new Product(2, 2, 2.00, "cat", "brand", "desc2", "image2", "name2");

        dummyDb = new ArrayList<Product>();

        dummyDb.add(product1);
        dummyDb.add(product2);

    }

    @Test
    @Order(1)
    @DisplayName("1. Mock validation Sanity Test")
    void checkMockInjection() {
        assertThat(mockProductDao).isNotNull();
        assertThat(productServ).isNotNull();

    }

    @Test
    @Order(2)
    @DisplayName("2. Test delete a product")
    void testDelete() {
        int id = 3;
        Product newProduct = new Product(id,3, 3.00, "cat3", "brand3", "desc", "image3", "name3");
        
        doNothing().when(mockProductDao).delete(newProduct);
        assertEquals(true, productServ.delete(newProduct));
    }

    @Test
    @Order(3)
    @DisplayName("3. Test find all products")
    void testFindAll() {
        when(productServ.findAll()).thenReturn(dummyDb);
        assertEquals(dummyDb, productServ.findAll());

    }

    @Test
    @Order(4)
    @DisplayName("4. Test find product by id")
    void testFindById() {
        when(productServ.findById(1)).thenReturn(Optional.of(product1));
		assertEquals(Optional.of(product1), productServ.findById(1));

    }

    @Test
    @Order(5)
    @DisplayName("5. Test get product by brand")
    void testGetProductsByBrand() {
        when(productServ.getProductsByBrand(product1.getBrand())).thenReturn(dummyDb);
        assertEquals(dummyDb,productServ.getProductsByBrand(product1.getBrand()));

    }

    @Test
    @Order(6)
    @DisplayName("Test get product by category")
    void testGetProductsByCategory() {
        when(productServ.getProductsByCategory(product1.getCategory())).thenReturn(dummyDb);
        assertEquals(dummyDb,productServ.getProductsByCategory(product1.getCategory()));
    }

    @Test
    @Order(7)
    @DisplayName("7. Test save a product")
    void testSave() {
        Product product3 = new Product(3, 3, 3.00, "cat3", "brand3", "desc3", "image", "name3");
        when(productServ.save(product3)).thenReturn(product3);
		assertEquals(product3, productServ.save(product3));

    }

    @Test
    @Order(8)
    @DisplayName("8. Test save all products")
    void testSaveAll() {
       ProductInfo mockProductInfo1 = new ProductInfo(1, 1);
       ProductInfo mockProductInfo2 = new ProductInfo(2, 2);

      List<ProductInfo> dummyDb2 = new ArrayList<>();
      dummyDb2.add(mockProductInfo1);
      dummyDb2.add(mockProductInfo2);

        when(productServ.saveAll(dummyDb,dummyDb2)).thenReturn(dummyDb);
		assertEquals(dummyDb, productServ.saveAll(dummyDb,dummyDb2));

    }

    @Test
    @Order(9)
    @DisplayName("9. Test update prodcut")
    void testUpdate() {
        product2.setQuantity(6);
        product2.setBrand("newBrand");
        when(productServ.findById(1)).thenReturn(Optional.of(product1));
        when(mockProductDao.save(product1)).thenReturn(product1);
	
		
		assertEquals(product1, productServ.update(product1));
    }
}
