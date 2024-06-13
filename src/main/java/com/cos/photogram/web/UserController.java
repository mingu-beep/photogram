package com.cos.photogram.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/{id}")
    public String profilePage(@PathVariable int id) {
        return "user/profile";
    }

    @GetMapping("/update")
    public String updatePage() {
        return "user/update";
    }
}

