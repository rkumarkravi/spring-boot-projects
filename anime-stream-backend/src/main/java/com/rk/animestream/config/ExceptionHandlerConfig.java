package com.rk.animestream.config;

import com.rk.animestream.enums.ResponseStatus;
import com.rk.animestream.pojos.ApiResponse;
import com.sun.org.apache.xpath.internal.operations.String;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class ExceptionHandlerConfig extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handleContentNotAllowedException(Exception ex, WebRequest request) {
        ApiResponse<String> exceptionResponse=new ApiResponse<>();
        exceptionResponse.setStatus(ResponseStatus.FAIL);
        exceptionResponse.setMessage("Exception: "+ex.getMessage());
        exceptionResponse.setPath(request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
