package com.cos.photogram.web;

import com.cos.photogram.domain.handler.ex.CustomException;
import com.cos.photogram.domain.user.User;
import com.cos.photogram.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/{id}")
    public String profilePage(@PathVariable int id, Model model) {

        User userEntity = userRepository.findById(id).orElseThrow(() -> {
            throw new CustomException("존재하지 않는 사용자에 대한 접근입니다.");
        });

        model.addAttribute("user", userEntity);
        return "user/profile";
    }

    @GetMapping("/update")
    public String updatePage() {
        return "user/update";
    }
}

