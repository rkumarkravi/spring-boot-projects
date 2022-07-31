package com.rk.animestream.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "video_blob")
@NoArgsConstructor
@Data
public class VideoBlob {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "vbid", nullable = false)
    private Long vbId;

    @Lob
    byte[] vBlob;
}