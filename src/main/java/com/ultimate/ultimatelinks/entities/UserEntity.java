package com.ultimate.ultimatelinks.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String name;

    public UserEntity(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }
}
