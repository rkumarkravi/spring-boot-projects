package com.egjpa.rkproj.dto.req;

import lombok.*;
import org.springframework.data.domain.Pageable;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class CommentReqDto {
    private Long id;
    private String content;
    private Long userId;
    private PaginationDto page;
}
