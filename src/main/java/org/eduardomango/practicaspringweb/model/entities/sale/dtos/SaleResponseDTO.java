package org.eduardomango.practicaspringweb.model.entities.sale.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleResponseDTO {
    private Long id;
    private String productName;
    private String clientName;
    private Long quantity;
    private LocalDate saleDate;
}
