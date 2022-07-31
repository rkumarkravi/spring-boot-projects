package com.rk.animestream.pojos;

import com.rk.animestream.enums.ResponseStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class ApiResponse<T> {
    ResponseStatus status;
    String message;
    String path;
    String timeStamp= String.valueOf(new Date().getTime());
    T data;
}
