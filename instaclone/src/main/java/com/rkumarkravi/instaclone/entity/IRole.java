package com.rkumarkravi.instaclone.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Entity
@Table(name = "ic_roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IRole implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<IUser> users;

    @Override
    public String getAuthority() {
        return name;
    }

    // Other role-related fields, getters, setters

    // Constructors, toString, equals, and hashCode methods
}

