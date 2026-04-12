package org.eduardomango.practicaspringweb.model.entities.product.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseDTO {
    private long id;
    private String name;
    private double price;
    private String description;
}
