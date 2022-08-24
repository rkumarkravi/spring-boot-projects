package com.rk.animestream.models;

import com.rk.animestream.enums.AnimeType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
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
    @Column(unique = true)
    private String name;
    private Date dateOfRelease;
    private String credits;
    @Column( length = 2000 )
    private String description;
    private String animeBackdrop;
    private String poster;
    private String originCountry;
    private Integer likes;
    @Column( length = 2000 )
    private String trailerLink;
    private Boolean released=false;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "anime_aid")
    private Set<Video> videos = new LinkedHashSet<>();

}