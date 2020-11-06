package com.epam.mjc.core.controller.errhandle;

import com.epam.mjc.api.service.exception.GiftCertificateValidatorException;
import com.epam.mjc.api.service.exception.TagValidatorException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TagValidatorException.class)
    public ResponseEntity<Object> handleTagValidatorException(TagValidatorException ex) {
        String errorCode = formatCode(HttpStatus.BAD_REQUEST.value(), ErrorCodes.TAG_CODE);
        return getResponseEntity(ex, errorCode, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GiftCertificateValidatorException.class)
    public ResponseEntity<Object> handleGiftCertificateValidatorException(GiftCertificateValidatorException ex) {
        String errorCode = formatCode(HttpStatus.BAD_REQUEST.value(), ErrorCodes.GIFT_CODE);
        return getResponseEntity(ex, errorCode, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleRemainException(Exception exception) {
        String errorCode = formatCode(HttpStatus.INTERNAL_SERVER_ERROR.value(), ErrorCodes.REMAIN_CODE);
        return getResponseEntity(exception, errorCode, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String formatCode(int httpPrefix, ErrorCodes giftCode) {
        return String.format("%s%s", httpPrefix, giftCode.getCode());
    }


    private ResponseEntity<Object> getResponseEntity(Exception exception, String errorCode, HttpStatus httpStatus) {
        String message = exception.getMessage();
        ExceptionInfoHolder info = new ExceptionInfoHolder(message, errorCode);
        return new ResponseEntity<>(info, new HttpHeaders(), httpStatus);
    }

}
