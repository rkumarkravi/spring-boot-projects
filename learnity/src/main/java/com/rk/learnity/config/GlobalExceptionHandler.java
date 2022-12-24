package com.rk.learnity.config;

import com.rk.learnity.dto.response.MasterResponseDto;
import com.rk.learnity.utility.ResponseUtility;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    private ResponseEntity<MasterResponseDto<Map<String, String>>> handleAnyExcetion(Exception ex) {
        return ResponseUtility.setFailureResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
