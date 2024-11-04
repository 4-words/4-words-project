package com.sparta.backend.domain.user;

import com.sparta.backend.config.PasswordEncoder;
import com.sparta.backend.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
@Getter
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String name;

    private String address;

    private String image;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User(final String email, final String password, final String name, final String address, final String image,
                final Role role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.address = address;
        this.image = image;
        this.role = role;
    }

    public static User of(final String email, final String password, final String name, final String address,
                          final String image, final String role) {
        return new User(email, password, name, address, image, Role.from(role));
    }

    public boolean isValidPassword(final String password, final PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(password, this.password);
    }
}
