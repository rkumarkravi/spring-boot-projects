package com.rk.animestream.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThumbnailDto implements Serializable {
    private Long tId;
    private byte[] tBlob;
    private String file;
}
