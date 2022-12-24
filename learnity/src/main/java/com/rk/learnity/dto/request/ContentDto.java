package com.rk.learnity.dto.request;

import com.rk.learnity.entity.Content;
import com.rk.learnity.enums.ContentTypeLearnity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * A DTO for the {@link com.rk.learnity.entity.Content} entity
 */
@Data
@NoArgsConstructor
public class ContentDto implements Serializable {
    private Long contentId;
    private ContentTypeLearnity type;
    private String value;
    private int order;

    public ContentDto(Content content) {
        this.contentId = content.getContentId();
        this.type = content.getType();
        this.order = content.getOrder();
        this.value = content.getValue();
    }
}