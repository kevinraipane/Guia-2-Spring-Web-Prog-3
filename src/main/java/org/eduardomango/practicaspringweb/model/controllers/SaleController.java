package org.eduardomango.practicaspringweb.model.controllers;

import jakarta.validation.Valid;

import org.eduardomango.practicaspringweb.model.entities.sale.dtos.SaleRequestDTO;
import org.eduardomango.practicaspringweb.model.entities.sale.dtos.SaleResponseDTO;
import org.eduardomango.practicaspringweb.model.entities.sale.entity.SaleEntity;
import org.eduardomango.practicaspringweb.model.services.SaleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SaleController {

    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @GetMapping
    public ResponseEntity<List<SaleResponseDTO>> getAll() {
        List<SaleResponseDTO> response = saleService.findAll().stream()
                .map(sale -> new SaleResponseDTO(
                        sale.getId(),
                        sale.getProducts().getName(),
                        sale.getClient().getUsername(),
                        sale.getQuantity(),
                        sale.getSaleDate()
                ))
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleResponseDTO> getById(@PathVariable Long id) {
        SaleEntity sale = saleService.findById(id);
        return ResponseEntity.ok(new SaleResponseDTO(
                sale.getId(),
                sale.getProducts().getName(),
                sale.getClient().getUsername(),
                sale.getQuantity(),
                sale.getSaleDate()
        ));
    }

    @PostMapping
    public ResponseEntity<SaleResponseDTO> create(@Valid @RequestBody SaleRequestDTO saleRequest) {
        SaleEntity newSale = saleService.createSale(
                saleRequest.getProductId(),
                saleRequest.getUserId(),
                saleRequest.getQuantity()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(new SaleResponseDTO(
                newSale.getId(),
                newSale.getProducts().getName(),
                newSale.getClient().getUsername(),
                newSale.getQuantity(),
                newSale.getSaleDate()
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SaleResponseDTO> update(@PathVariable Long id, @Valid @RequestBody SaleRequestDTO saleRequest) {
        SaleEntity updatedSale = saleService.update(id, saleRequest);

        return ResponseEntity.ok(new SaleResponseDTO(
                updatedSale.getId(),
                updatedSale.getProducts().getName(),
                updatedSale.getClient().getUsername(),
                updatedSale.getQuantity(),
                updatedSale.getSaleDate()
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        saleService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
