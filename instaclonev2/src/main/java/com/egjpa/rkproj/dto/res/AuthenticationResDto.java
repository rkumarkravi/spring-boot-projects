package com.egjpa.rkproj.dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationResDto {
    private String authToken;
    private String refreshToken;
}
