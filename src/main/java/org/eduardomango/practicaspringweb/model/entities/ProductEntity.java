package org.eduardomango.practicaspringweb.model.entities;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ProductEntity {
    @EqualsAndHashCode.Include
    private long id;

    private String name;
    private double price;
    private String description;
}
