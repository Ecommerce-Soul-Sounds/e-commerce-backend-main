package com.revature.services;

import com.revature.dtos.ProductInfo;
import com.revature.models.Product;
import com.revature.repositories.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
  
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<Product> getProductsByBrand(String brand) {
        return productRepository.getProductsByBrand(brand);
    }

    public List<Product> getProductsByCategory(String category) {
        return productRepository.getProductsByCategory(category);
    }

    public Optional<Product> findById(int id) {
        return productRepository.findById(id);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product update(Product product) {
        Product target = productRepository.findById(product.getId()).get();
		target.setBrand(product.getBrand());
        target.setCategory(product.getCategory());
        target.setDescription(product.getDescription());
        target.setImage(product.getImage());
        target.setName(product.getName());
        target.setPrice(product.getPrice());
        target.setQuantity(product.getQuantity());

		return productRepository.save(target);
    }
    public List<Product> saveAll(List<Product> productList, List<ProductInfo> metadata) {
    	return productRepository.saveAll(productList);
    }

    public boolean delete(Product product) {
        productRepository.delete(product);
        return true;
    }
}
