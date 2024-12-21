package com.egjpa.rkproj.dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenRefreshResDto {
    private String accessToken;
    private String refreshToken;
}
