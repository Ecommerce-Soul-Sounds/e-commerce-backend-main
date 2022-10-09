package com.revature.controllers;

import com.revature.annotations.Authorized;
import com.revature.dtos.ProductInfo;
import com.revature.models.ClientMessage;
import com.revature.models.Product;
import com.revature.services.ProductService;
import com.revature.util.ClientMessageUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:3000" }, allowCredentials = "true")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Authorized
    @GetMapping
    public ResponseEntity<List<Product>> getInventory() {
        return ResponseEntity.ok(productService.findAll());
    }

    @Authorized
    @GetMapping("/brand")
    @CrossOrigin(allowCredentials = "true", methods = RequestMethod.GET, allowedHeaders = "*")
    public List<Product> getProductsByBrand(@RequestParam String brand, HttpServletRequest request) {
        return productService.getProductsByBrand(brand);
    }

    @Authorized
    @GetMapping("/category")
    @CrossOrigin(allowCredentials = "true", methods = RequestMethod.GET, allowedHeaders = "*")
    public List<Product> getProductsByType(@RequestParam String category, HttpServletRequest request) {
        System.out.println(category);
        return productService.getProductsByCategory(category);
    }

    @Authorized
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") int id) {
        Optional<Product> optional = productService.findById(id);

        if (!optional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(optional.get());
    }

    @Authorized
    @PutMapping
    public ResponseEntity<Product> upsert(@RequestBody Product product) {
        return ResponseEntity.ok(productService.save(product));
    }

    @Authorized
    @PutMapping("/update")
    public @ResponseBody ClientMessage updateProduct(@RequestBody Product product) {
        if (productService.update(product) != null) {
            return ClientMessageUtil.UPDATE_SUCCESSFUL;
        } else {
            return ClientMessageUtil.UPDATE_FAILED;
        }
    }

    @Authorized
    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") int id) {
        Optional<Product> optional = productService.findById(id);

        if (!optional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Product product = optional.get();
        productService.delete(product);

        return ResponseEntity.ok(optional.get());
    }
}
