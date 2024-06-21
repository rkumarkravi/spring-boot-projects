package com.egjpa.rkproj.dto.req;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AuthenticationReqDto {
    private String username;
    private String password;
}
