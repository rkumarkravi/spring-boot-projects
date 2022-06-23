package com.rk.hrm.configs;

import com.rk.hrm.dtos.ExceptionHrmModel;
import com.rk.hrm.exceptions.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;


@ControllerAdvice
@RestController
@Slf4j
public class GlobalExceptionCatch extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<String> handleContentNotAllowedException(ResourceNotFoundException rnfe) {
        return new ResponseEntity<>(rnfe.getLocalizedMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handleContentNotAllowedException(Exception rnfe, WebRequest request) {
        ExceptionHrmModel exceptionHrmModel = new ExceptionHrmModel(rnfe.getClass().getSimpleName(),
                HttpStatus.INTERNAL_SERVER_ERROR + "",
                rnfe.getLocalizedMessage(),
                request.getDescription(false),
                new Date());
        rnfe.printStackTrace();
        return new ResponseEntity<>(exceptionHrmModel, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionHrmModel exceptionHrmModel = new ExceptionHrmModel(ex.getClass().getSimpleName(),
                HttpStatus.BAD_REQUEST + "",
                ex.getBindingResult().toString(),
                request.getDescription(false),
                new Date());
        return new ResponseEntity<>(exceptionHrmModel, HttpStatus.BAD_REQUEST);
    }
}
