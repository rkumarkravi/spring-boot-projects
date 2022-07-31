package com.rk.animestream.models;

import com.rk.animestream.enums.AnimeType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "anime")
@NoArgsConstructor
@Data
public class Anime {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "aid", nullable = false)
    private Long aId;
    @Enumerated(EnumType.STRING)
    private AnimeType animeType;
    private String name;
    private String dateOfReleased;
    private String credits;
    private String description;
    private String animeThumbnail;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "anime_aid")
    private Set<Video> videos = new LinkedHashSet<>();
}