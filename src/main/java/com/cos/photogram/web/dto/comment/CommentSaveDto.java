package com.cos.photogram.web.dto.comment;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CommentSaveDto {

    @NotBlank
    private String content;

    @NotBlank
    private Integer imageId;

}
