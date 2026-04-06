package org.eduardomango.practicaspringweb.model.entities;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserEntity {

    private long id;
    private String username;
    private String email;
    private String password;
}
