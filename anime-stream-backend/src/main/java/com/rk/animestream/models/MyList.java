package com.rk.animestream.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "my_list")
@Data
@NoArgsConstructor
public class MyList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "mid", nullable = false)
    private Long mId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_uid", nullable = false, unique = true)
    private User user;

    @ManyToMany
    @JoinTable(name = "my_list_animes",
            joinColumns = @JoinColumn(name = "my_list_mid"),
            inverseJoinColumns = @JoinColumn(name = "animes_aid"))
    private Set<Anime> animes = new LinkedHashSet<>();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date addedOn=new Date();

    public Set<Anime> getAnimes() {
        return animes;
    }

    public void setAnimes(Set<Anime> animes) {
        this.animes = animes;
    }
}