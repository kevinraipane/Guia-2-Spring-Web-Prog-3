package org.eduardomango.practicaspringweb.model.controllers;

import org.eduardomango.practicaspringweb.model.dtos.SaleRequest;
import org.eduardomango.practicaspringweb.model.entities.SaleEntity;
import org.eduardomango.practicaspringweb.model.exceptions.ProductNotFoundException;
import org.eduardomango.practicaspringweb.model.exceptions.SaleNotFoundException;
import org.eduardomango.practicaspringweb.model.exceptions.UserNotFoundException;
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
    public ResponseEntity<List<SaleEntity>> getAll() {
        return ResponseEntity.ok(saleService.findAll()); //200
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleEntity> getById(@PathVariable long id) {
        try {
            SaleEntity sale = saleService.findById(id);
            return ResponseEntity.ok(sale); //200
        } catch (SaleNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); //404
        }
    }

    @PostMapping
    public ResponseEntity<SaleEntity> create(@RequestBody SaleRequest saleRequest) {
        try {
            SaleEntity newSale = saleService.createSale(
                    saleRequest.getProductId(),
                    saleRequest.getUserId(),
                    saleRequest.getQuantity()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(newSale);
        } catch (UserNotFoundException | ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); //404 Not Found si fallan las validaciones
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); //400 Bad Request si llega mal
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SaleEntity> update(@PathVariable Long id, @RequestBody SaleRequest saleRequest) {
        try {
            SaleEntity updatedSale = saleService.update(id, saleRequest);
            return ResponseEntity.ok(updatedSale);
        } catch (SaleNotFoundException | UserNotFoundException | ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            saleService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); //204
        } catch (SaleNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); //404
        }
    }

}
