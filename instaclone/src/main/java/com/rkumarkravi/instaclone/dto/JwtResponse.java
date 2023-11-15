package com.rkumarkravi.instaclone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    private Map<String,Object> user;
}
