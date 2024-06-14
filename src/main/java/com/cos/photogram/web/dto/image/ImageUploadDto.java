package com.cos.photogram.web.dto.image;

import com.cos.photogram.domain.image.Image;
import com.cos.photogram.domain.user.User;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImageUploadDto {

    private MultipartFile file;
    private String caption;

}
