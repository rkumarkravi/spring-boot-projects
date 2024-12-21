package com.egjpa.rkproj.config;

import com.egjpa.rkproj.dto.res.CommonResDto;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestAdvice {
    @ExceptionHandler({Exception.class, RuntimeException.class})
    private CommonResDto<?> generalExceptionHandling(Exception ex) {
        ex.printStackTrace();
        return CommonResDto.<Void>builder()
                .rs("EX")
//                .rd(ex.getMessage())
                .build();
    }
}
