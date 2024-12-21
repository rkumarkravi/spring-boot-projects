package com.egjpa.rkproj.dto.req;

import lombok.Data;

@Data
public class PaginationDto {
    private int page;
    private int size;
    private String sortBy;
    private String sortOrder;
}


