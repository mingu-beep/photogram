package com.cos.photogram.web.dto.comment;

import lombok.Data;

@Data
public class CommentSaveDto {

    private String content;
    private Integer imageId;

}
