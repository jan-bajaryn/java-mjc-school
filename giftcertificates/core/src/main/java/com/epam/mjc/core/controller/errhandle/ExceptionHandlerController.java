package com.epam.mjc.core.controller.errhandle;

import com.epam.mjc.api.service.exception.GiftCertificateAlreadyExists;
import com.epam.mjc.api.service.exception.GiftCertificateNotFoundException;
import com.epam.mjc.api.service.exception.GiftCertificateValidatorException;
import com.epam.mjc.api.service.exception.TagAlreadyExistsException;
import com.epam.mjc.api.service.exception.TagNotFoundException;
import com.epam.mjc.api.service.exception.TagValidatorException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ResourceBundle;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    private final ResourceBundle bundle = ResourceBundle.getBundle("message");


    @ExceptionHandler(TagValidatorException.class)
    public ResponseEntity<Object> handleTagValidatorException(TagValidatorException ex) {
        String errorCode = formatCode(HttpStatus.BAD_REQUEST.value(), ErrorCodes.TAG_VALIDATOR);
        return getResponseEntity(ex, errorCode, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GiftCertificateValidatorException.class)
    public ResponseEntity<Object> handleGiftCertificateValidatorException(GiftCertificateValidatorException ex) {
        String errorCode = formatCode(HttpStatus.BAD_REQUEST.value(), ErrorCodes.GIFT_VALIDATOR);
        return getResponseEntity(ex, errorCode, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(GiftCertificateAlreadyExists.class)
    public ResponseEntity<Object> handleGiftCertificateAlreadyExists(GiftCertificateAlreadyExists ex) {
        String errorCode = formatCode(HttpStatus.BAD_REQUEST.value(), ErrorCodes.GIFT_DUPLICATE);//
        return getResponseEntity(ex, errorCode, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GiftCertificateNotFoundException.class)
    public ResponseEntity<Object> handleGiftCertificateNotFoundException(GiftCertificateNotFoundException ex) {
        String errorCode = formatCode(HttpStatus.NOT_FOUND.value(), ErrorCodes.GIFT_NOT_FOUND);//
        return getResponseEntity(ex, errorCode, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TagAlreadyExistsException.class)
    public ResponseEntity<Object> handleTagAlreadyExistsException(TagAlreadyExistsException ex) {
        String errorCode = formatCode(HttpStatus.BAD_REQUEST.value(), ErrorCodes.TAG_DUPLICATE);//
        return getResponseEntity(ex, errorCode, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TagNotFoundException.class)
    public ResponseEntity<Object> handleTagNotFoundException(TagNotFoundException ex) {
        String errorCode = formatCode(HttpStatus.NOT_FOUND.value(), ErrorCodes.TAG_NOT_FOUND);//
        return getResponseEntity(ex, errorCode, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleRemainException(Exception exception) {
        logger.error("Exception: ", exception);
        String errorCode = formatCode(HttpStatus.INTERNAL_SERVER_ERROR.value(), ErrorCodes.REMAIN_CODE);
        String message = bundle.getString("unexpected.error");
        ExceptionInfoHolder info = new ExceptionInfoHolder(message, errorCode);
        return new ResponseEntity<>(info, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private String formatCode(int httpPrefix, ErrorCodes giftCode) {
        return String.format("%s%s", httpPrefix, giftCode.getCode());
    }


    private ResponseEntity<Object> getResponseEntity(Exception exception, String errorCode, HttpStatus httpStatus) {
        logger.error("Exception: ", exception);
        String message = bundle.getString(exception.getMessage());
        ExceptionInfoHolder info = new ExceptionInfoHolder(message, errorCode);
        return new ResponseEntity<>(info, new HttpHeaders(), httpStatus);
    }

}
