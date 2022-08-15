package com.rk.animestream.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "thumbnail")
@Data
@NoArgsConstructor
public class Thumbnail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tid", nullable = false)
    private Long tId;
    @Lob
    byte[] tBlob;
    @Column(name="file_name")
    String file;
}