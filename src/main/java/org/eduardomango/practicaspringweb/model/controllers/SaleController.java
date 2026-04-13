package org.eduardomango.practicaspringweb.model.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.eduardomango.practicaspringweb.model.entities.sale.dtos.SaleRequestDTO;
import org.eduardomango.practicaspringweb.model.entities.sale.dtos.SaleResponseDTO;
import org.eduardomango.practicaspringweb.model.entities.sale.entity.SaleEntity;
import org.eduardomango.practicaspringweb.model.exceptions.ErrorMessage;
import org.eduardomango.practicaspringweb.model.services.SaleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
@Tag(name = "Sales", description = "API para registrar y consultar ventas")
public class SaleController {

    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @Operation(summary = "Obtener todas las ventas")
    @ApiResponse(responseCode = "200", description = "Lista de ventas obtenida")
    @GetMapping
    public ResponseEntity<List<SaleResponseDTO>> getAll() {
        List<SaleResponseDTO> response = saleService.findAll().stream()
                .map(sale -> new SaleResponseDTO(
                        sale.getId(), sale.getProducts().getName(), sale.getClient().getUsername(), sale.getQuantity(), sale.getSaleDate()))
                .toList();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtener una venta por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Venta encontrada"),
            @ApiResponse(responseCode = "404", description = "Venta no encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<SaleResponseDTO> getById(@PathVariable Long id) {
        SaleEntity sale = saleService.findById(id);
        return ResponseEntity.ok(new SaleResponseDTO(
                sale.getId(), sale.getProducts().getName(), sale.getClient().getUsername(), sale.getQuantity(), sale.getSaleDate()));
    }

    @Operation(summary = "Registrar una nueva venta")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Venta registrada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Error de validación en la solicitud", content = @Content),
            @ApiResponse(responseCode = "404", description = "Producto o Usuario no encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PostMapping
    public ResponseEntity<SaleResponseDTO> create(@Valid @RequestBody SaleRequestDTO saleRequest) {
        SaleEntity newSale = saleService.createSale(
                saleRequest.getProductId(), saleRequest.getUserId(), saleRequest.getQuantity());

        return ResponseEntity.status(HttpStatus.CREATED).body(new SaleResponseDTO(
                newSale.getId(), newSale.getProducts().getName(), newSale.getClient().getUsername(), newSale.getQuantity(), newSale.getSaleDate()));
    }

    @Operation(summary = "Actualizar una venta")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Venta actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Error de validación en la solicitud", content = @Content),
            @ApiResponse(responseCode = "404", description = "Venta, Producto o Usuario no encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<SaleResponseDTO> update(@PathVariable Long id, @Valid @RequestBody SaleRequestDTO saleRequest) {
        SaleEntity updatedSale = saleService.update(id, saleRequest);

        return ResponseEntity.ok(new SaleResponseDTO(
                updatedSale.getId(), updatedSale.getProducts().getName(), updatedSale.getClient().getUsername(), updatedSale.getQuantity(), updatedSale.getSaleDate()));
    }

    @Operation(summary = "Eliminar una venta")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Venta eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Venta no encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        saleService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
