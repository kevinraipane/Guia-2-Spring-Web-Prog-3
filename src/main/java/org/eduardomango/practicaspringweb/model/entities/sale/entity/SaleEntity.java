package org.eduardomango.practicaspringweb.model.entities.sale.entity;

import java.time.LocalDate;

import lombok.*;
import org.eduardomango.practicaspringweb.model.entities.user.entity.UserEntity;
import org.eduardomango.practicaspringweb.model.entities.product.entity.ProductEntity;

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
