package org.eduardomango.practicaspringweb.model.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.eduardomango.practicaspringweb.model.entities.product.dtos.ProductRequestDTO;
import org.eduardomango.practicaspringweb.model.entities.product.dtos.ProductResponseDTO;
import org.eduardomango.practicaspringweb.model.entities.product.entity.ProductEntity;
import org.eduardomango.practicaspringweb.model.exceptions.ErrorMessage;
import org.eduardomango.practicaspringweb.model.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Products", description = "API para la gestión de productos")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Obtener todos los productos")
    @ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAll() {
        List<ProductResponseDTO> response = productService.findAll().stream()
                .map(p -> new ProductResponseDTO(p.getId(), p.getName(), p.getPrice(), p.getDescription()))
                .toList();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtener un producto por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getById(@PathVariable Long id) {
        ProductEntity p = productService.findById(id);
        return ResponseEntity.ok(new ProductResponseDTO(p.getId(), p.getName(), p.getPrice(), p.getDescription()));
    }

    @Operation(summary = "Obtener un producto por nombre")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    @GetMapping("/name/{name}")
    public ResponseEntity<ProductResponseDTO> getByName(@PathVariable String name) {
        ProductEntity p = productService.findByName(name);
        return ResponseEntity.ok(new ProductResponseDTO(p.getId(), p.getName(), p.getPrice(), p.getDescription()));
    }

    @Operation(summary = "Obtener productos por precio")
    @ApiResponse(responseCode = "200", description = "Lista filtrada exitosamente")
    @GetMapping("/expensive-than/{price}")
    public ResponseEntity<List<ProductResponseDTO>> getMoreExpensiveThan(@PathVariable Double price) {
        List<ProductResponseDTO> response = productService.findMoreExpensiveThan(price).stream()
                .map(p -> new ProductResponseDTO(p.getId(), p.getName(), p.getPrice(), p.getDescription()))
                .toList();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Crear un nuevo producto")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Error de validación en los datos de entrada", content = @Content)
    })
    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(@Valid @RequestBody ProductRequestDTO requestDTO) {
        long newId = productService.findAll().stream().mapToLong(ProductEntity::getId).max().orElse(0L) + 1;

        ProductEntity product = new ProductEntity(newId, requestDTO.getName(), requestDTO.getPrice(), requestDTO.getDescription());
        productService.save(product);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ProductResponseDTO(product.getId(), product.getName(), product.getPrice(), product.getDescription()));
    }

    @Operation(summary = "Actualizar un producto existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto actualizado"),
            @ApiResponse(responseCode = "400", description = "Error de validación en los datos de entrada", content = @Content),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ProductRequestDTO requestDTO) {
        ProductEntity existingProduct = productService.findById(id);

        existingProduct.setName(requestDTO.getName());
        existingProduct.setPrice(requestDTO.getPrice());
        existingProduct.setDescription(requestDTO.getDescription());

        productService.update(existingProduct);

        return ResponseEntity.ok(new ProductResponseDTO(
                existingProduct.getId(), existingProduct.getName(), existingProduct.getPrice(), existingProduct.getDescription()));
    }

    @Operation(summary = "Eliminar un producto")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Producto eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ProductEntity product = productService.findById(id);
        productService.delete(product);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
