package com.cos.photogram.web;

import com.cos.photogram.domain.handler.ex.CustomValidationException;
import com.cos.photogram.domain.user.User;
import com.cos.photogram.service.AuthService;
import com.cos.photogram.web.dto.auth.SignUpDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auth")
@Controller
public class AuthController {

    private final AuthService authService;

    @GetMapping("/signin")
    public String signInPage() {
        return "/auth/signin";
    }

    @GetMapping("/signup")
    public String signUpPage() {
        return "/auth/signup";
    }

    @PostMapping("/signup")
    public String postSignUp(HttpServletRequest request, @Valid SignUpDto signUpDto, BindingResult bindingResult) {

        log.info(" ##### [{}] {}", request.getMethod(), request.getRequestURI());
        log.info(" @@@@@ {}", signUpDto.toString());

        User user = signUpDto.toEntity();

        log.info(" @@@@@ {}", user.toString());

        authService.signUpService(user);

        return "redirect:/auth/signin"; // redirect를 사용하지 않을 경우 브라우저 주소창의 url이 /signup으로 표시된다.
    }
}
