package com.rk.animestream.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoBlobFileDto implements Serializable {
    private Long vbId;
    private byte[] vBlob;
    private String vFile;
}
