package com.cos.photogram.domain.handler;

import com.cos.photogram.domain.handler.ex.CustomApiValidationException;
import com.cos.photogram.domain.handler.ex.CustomValidationException;
import com.cos.photogram.util.Script;
import com.cos.photogram.web.dto.CMRespDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(CustomValidationException.class)
    public String signInValidationException(CustomValidationException e) {

        log.error(" XXXXX {} / {}", e.getMsg(), e.getDetails().toString());

//        return new CMRespDto<>(-1, e.getMsg(), e.getDetails());

        return Script.back(e.getDetails().toString());
    }

    @ExceptionHandler(CustomApiValidationException.class)
    public ResponseEntity<CMRespDto<?>> updateApiValidationException(CustomApiValidationException e) {
        return new ResponseEntity<>(new CMRespDto<>(-1, e.getMessage(), e.getDetails()), HttpStatus.BAD_REQUEST);
    }
}
