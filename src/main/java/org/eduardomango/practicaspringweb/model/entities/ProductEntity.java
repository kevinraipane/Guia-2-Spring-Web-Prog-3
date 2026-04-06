package org.eduardomango.practicaspringweb.model.entities;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ProductEntity {

    private long id;
    private String name;
    private double price;
    private String description;
}
