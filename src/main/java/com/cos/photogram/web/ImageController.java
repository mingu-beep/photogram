package com.cos.photogram.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/image")
public class ImageController {

    @GetMapping("/story")
    public String storyPage() {
        return "image/story";
    }

    @GetMapping("/popular")
    public String popularPage() {
        return "image/popular";
    }

    @GetMapping("/upload")
    public String uploadPage() {
        return "image/upload";
    }

}
