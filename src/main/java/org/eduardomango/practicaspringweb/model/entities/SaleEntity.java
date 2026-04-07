package org.eduardomango.practicaspringweb.model.entities;

import java.time.LocalDate;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SaleEntity {
    @EqualsAndHashCode.Include
    private Long id;

    private ProductEntity products;
    private Long quantity;
    private UserEntity client;
    private LocalDate saleDate;
}
