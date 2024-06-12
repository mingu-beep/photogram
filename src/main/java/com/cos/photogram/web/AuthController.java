package com.cos.photogram.web;

import com.cos.photogram.domain.user.User;
import com.cos.photogram.service.AuthService;
import com.cos.photogram.web.dto.auth.SignUpDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequestMapping("/auth")
@Controller
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/signin")
    public String signInPage() {
        return "/auth/signin";
    }

    @GetMapping("/signup")
    public String signUpPage() {
        return "/auth/signup";
    }

    @PostMapping("/signup")
    public String postSignUp(HttpServletRequest request, SignUpDto signUpDto) {

        log.info(" ##### [{}] {}", request.getMethod(), request.getRequestURI());
        log.info(" @@@@@ {}", signUpDto.toString());

        User user = signUpDto.toEntity();

        log.info(" @@@@@ {}", user.toString());

        authService.signUpService(user);

        return "redirect:/auth/signin"; // redirect를 사용하지 않을 경우 브라우저 주소창의 url이 /signup으로 표시된다.
    }
}
