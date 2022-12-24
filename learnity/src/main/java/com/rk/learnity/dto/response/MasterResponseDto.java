package com.rk.learnity.dto.response;

import com.rk.learnity.enums.ResponseStatus;
import lombok.Data;

@Data
public class MasterResponseDto<T> {

    private ResponseStatus rs;
    private String rc;
    private String rd;
    private T payload;

}
