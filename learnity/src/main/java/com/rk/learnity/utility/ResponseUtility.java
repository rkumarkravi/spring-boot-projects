package com.rk.learnity.utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rk.learnity.dto.response.MasterResponseDto;
import com.rk.learnity.enums.ResponseMessages;
import com.rk.learnity.enums.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtility {

    public static Gson gsonObj = new GsonBuilder()
            .setPrettyPrinting().serializeNulls()
            .create();

    public static <T> ResponseEntity<MasterResponseDto<T>> setSuccessResponse(T payload) {
        MasterResponseDto<T> masterResponseDto = new MasterResponseDto<>();
        masterResponseDto.setPayload(payload);
        masterResponseDto.setRc("00");
        masterResponseDto.setRd(ResponseMessages.SU.getValue());
        masterResponseDto.setRs(ResponseStatus.SUCCESS);
        return ResponseEntity.ok(masterResponseDto);
    }

    public static <T> ResponseEntity<MasterResponseDto<T>> setFailureResponse(String message, HttpStatus status) {
        MasterResponseDto<T> masterResponseDto = new MasterResponseDto<>();
        masterResponseDto.setPayload(null);
        masterResponseDto.setRc("01");
        masterResponseDto.setRd(message);
        masterResponseDto.setRs(ResponseStatus.FAIL);
        return new ResponseEntity<>(masterResponseDto, status);
    }

}
