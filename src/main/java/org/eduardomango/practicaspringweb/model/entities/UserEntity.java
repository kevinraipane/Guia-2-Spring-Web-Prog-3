package org.eduardomango.practicaspringweb.model.entities;

import lombok.*;

@Getter
@Setter
//Con esta linea le podemos decir que campos explicitos utilizar en el Equals
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserEntity {

    @EqualsAndHashCode.Include //Aqui determinamos explicitamente que campos usar en la comparacion de equals
    private long id;

    private String username;
    private String email;
    private String password;
}
