package org.eduardomango.practicaspringweb.model.controllers;

import jakarta.validation.Valid;

import org.eduardomango.practicaspringweb.model.entities.product.dtos.ProductRequestDTO;
import org.eduardomango.practicaspringweb.model.entities.product.dtos.ProductResponseDTO;
import org.eduardomango.practicaspringweb.model.entities.product.entity.ProductEntity;
import org.eduardomango.practicaspringweb.model.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAll() {
        List<ProductResponseDTO> response = productService.findAll().stream()
                .map(p -> new ProductResponseDTO(p.getId(), p.getName(), p.getPrice(), p.getDescription()))
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getById(@PathVariable Long id) {
        ProductEntity p = productService.findById(id);
        return ResponseEntity.ok(new ProductResponseDTO(p.getId(), p.getName(), p.getPrice(), p.getDescription()));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ProductResponseDTO> getByName(@PathVariable String name) {
        ProductEntity p = productService.findByName(name);
        return ResponseEntity.ok(new ProductResponseDTO(p.getId(), p.getName(), p.getPrice(), p.getDescription()));
    }

    @GetMapping("/expensive-than/{price}")
    public ResponseEntity<List<ProductResponseDTO>> getMoreExpensiveThan(@PathVariable Double price) {
        List<ProductResponseDTO> response = productService.findMoreExpensiveThan(price).stream()
                .map(p -> new ProductResponseDTO(p.getId(), p.getName(), p.getPrice(), p.getDescription()))
                .toList();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(@Valid @RequestBody ProductRequestDTO requestDTO) {
        // Simulamos la creación del ID
        long newId = productService.findAll().stream().mapToLong(ProductEntity::getId).max().orElse(0L) + 1;

        ProductEntity product = new ProductEntity(
                newId,
                requestDTO.getName(),
                requestDTO.getPrice(),
                requestDTO.getDescription()
        );
        productService.save(product);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ProductResponseDTO(product.getId(), product.getName(), product.getPrice(), product.getDescription()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ProductRequestDTO requestDTO) {
        ProductEntity existingProduct = productService.findById(id);

        existingProduct.setName(requestDTO.getName());
        existingProduct.setPrice(requestDTO.getPrice());
        existingProduct.setDescription(requestDTO.getDescription());

        productService.update(existingProduct);

        return ResponseEntity.ok(new ProductResponseDTO(
                existingProduct.getId(),
                existingProduct.getName(),
                existingProduct.getPrice(),
                existingProduct.getDescription()
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ProductEntity product = productService.findById(id);
        productService.delete(product);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
