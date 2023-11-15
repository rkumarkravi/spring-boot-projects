package com.rkumarkravi.instaclone.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "ic_media_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private IUser user;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private LocalDateTime uploadDateTime;

    // Other upload-related fields, getters, setters

    // Constructors, toString, equals, and hashCode methods
}

