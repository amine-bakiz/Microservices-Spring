package com.productService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductRepository repository;

    public ProductController(ProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return repository.save(product);
    }
    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Long productId) {
        Optional<Product> product = repository.findById(productId);
        return ResponseEntity.ok(new Product(product.get().getId(), product.get().getName(),product.get().getDescription(), product.get().getPrice(), product.get().getStock()));
    }


    @PutMapping("/{productId}/stock")
    public ResponseEntity<Void> updateStock(
            @PathVariable Long productId,
            @RequestParam Integer quantity
    ) {
        return repository.findById(productId).map(product -> {
            int newStock = product.getStock() - quantity;

            // Prevent negative stock
            if (newStock < 0) {
                throw new RuntimeException("Stock cannot be negative");
            }

            product.setStock(newStock);
            repository.save(product);
            return ResponseEntity.ok().<Void>build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
