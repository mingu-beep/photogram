package com.cos.photogram.domain.handler;

import com.cos.photogram.domain.handler.ex.CustomApiException;
import com.cos.photogram.domain.handler.ex.CustomApiValidationException;
import com.cos.photogram.domain.handler.ex.CustomException;
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

        return e.getDetails() == null ? Script.back(e.getMsg()) : Script.back(e.getDetails().toString());
    }

    @ExceptionHandler(CustomApiValidationException.class)
    public ResponseEntity<CMRespDto<?>> updateApiValidationException(CustomApiValidationException e) {
        return new ResponseEntity<>(new CMRespDto<>(-1, e.getMessage(), e.getDetails()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomApiException.class)
    public ResponseEntity<?> apiException(CustomApiException e) {
        return new ResponseEntity<>(new CMRespDto<>(-1, e.getMessage(), null), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomException.class)
    public String exception(CustomException e) {
        return Script.back(e.getMsg());
    }
}
