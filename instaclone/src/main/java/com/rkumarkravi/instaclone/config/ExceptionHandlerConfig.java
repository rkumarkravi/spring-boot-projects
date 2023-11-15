package com.rkumarkravi.instaclone.config;

import com.rkumarkravi.instaclone.dto.ApiResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class ExceptionHandlerConfig extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {Exception.class, UsernameNotFoundException.class})
    public @NotNull ResponseEntity<Object> handleContentNotAllowedException(@NotNull Exception ex, @NotNull WebRequest request) {
        ApiResponse<String> exceptionResponse=new ApiResponse<>();
        exceptionResponse.setStatus("F");
        exceptionResponse.setMessage("Exception: "+ex.getMessage());
        exceptionResponse.setPath(request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
