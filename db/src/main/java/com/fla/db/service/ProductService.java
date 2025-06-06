package com.fla.db.service;

import com.fla.db.model.Product;
import com.fla.db.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // Marks this class as a Spring service component
public class ProductService {

    private ProductRepository productRepository;

    @Autowired // Injects the ProductRepository dependency
    public ProductService(ProductRepository repository) {
        this.productRepository = repository;
    }

    public List<Product> getProducts() {
        // Retrieves all products from the database
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        // Retrieves a product by its ID, or returns a dummy product if not found
        // Product dummyProduct = new Product();
        // dummyProduct.setId(0L);
        // dummyProduct.setTitle("Unknown Product");
        // dummyProduct.setPrice(0);
        // dummyProduct.setBrand("UNKNOWN");
        // dummyProduct.setCategory("UNKNOWN");
        // Optional<Product> optionalProduct = productRepository.findById(id);
        // Product product;
        // if (optionalProduct.isPresent()) {
        // product = optionalProduct.get();
        // }
        // else {
        // product = null;
        // }

        // product = optionalProduct.isPresent() ? optionalProduct.get() : null;
        // return product;
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> getProductsByTitle(String searchTerm) {
        return productRepository.findByTitleContainingIgnoreCase(searchTerm);
    }

    public Product createProduct(Product product) {
        if (product == null || product.getTitle() == null || product.getPrice() <= 0) {
            return null; // Return null if the product is invalid
        } else {
            return productRepository.save(product);
        }
    }

    public Product updateProduct(Long id, Product productDetails) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            return null;
        } else {
            Product product = optionalProduct.get();
            product.setTitle(productDetails.getTitle());
            product.setPrice(productDetails.getPrice());
            product.setBrand(productDetails.getBrand());
            product.setCategory(productDetails.getCategory());
            return productRepository.save(product);
        }
    }

    public boolean deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            return false;
        } else {
            productRepository.deleteById(id);
            return true;
        }
    }

    public List<Product> getProductsByPrice(int startPrice, int endPrice) {
        return productRepository.findByPriceBetween(startPrice, endPrice);
    }

    public List<Product> getProductsByCategoryOrderByTitle(String category) {
        return productRepository.findByCategoryContainingIgnoreCaseOrderByTitle(category);
    }

    public List<Product> getProductsByBrandAndPriceRange(String brand, int minPrice, int maxPrice) {
        return productRepository.getProductsByBrandPrice(brand, minPrice, maxPrice);
    }
}