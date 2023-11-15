package com.rkumarkravi.instaclone.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
public class ApiResponse<T> {
    String status;
    String message;
    String path;
    String timeStamp= LocalDateTime.now().toString();
    T data;
}
