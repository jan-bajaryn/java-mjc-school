package com.epam.mjc.api.controller.errhandle;

import com.epam.mjc.api.service.exception.GiftCertificateAlreadyExists;
import com.epam.mjc.api.service.exception.GiftCertificateNotFoundException;
import com.epam.mjc.api.service.exception.GiftCertificateValidatorException;
import com.epam.mjc.api.service.exception.TagAlreadyExistsException;
import com.epam.mjc.api.service.exception.TagNotFoundException;
import com.epam.mjc.api.service.exception.TagValidatorException;
import com.epam.mjc.api.service.exception.WrongQuerySortException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public interface ExceptionHandlerController {
    @ExceptionHandler(TagValidatorException.class)
    ResponseEntity<Object> handleTagValidatorException(TagValidatorException ex);

    @ExceptionHandler(GiftCertificateValidatorException.class)
    ResponseEntity<Object> handleGiftCertificateValidatorException(GiftCertificateValidatorException ex);

    @ExceptionHandler(GiftCertificateAlreadyExists.class)
    ResponseEntity<Object> handleGiftCertificateAlreadyExists(GiftCertificateAlreadyExists ex);

    @ExceptionHandler(GiftCertificateNotFoundException.class)
    ResponseEntity<Object> handleGiftCertificateNotFoundException(GiftCertificateNotFoundException ex);

    @ExceptionHandler(TagAlreadyExistsException.class)
    ResponseEntity<Object> handleTagAlreadyExistsException(TagAlreadyExistsException ex);

    @ExceptionHandler(TagNotFoundException.class)
    ResponseEntity<Object> handleTagNotFoundException(TagNotFoundException ex);

    @ExceptionHandler(WrongQuerySortException.class)
    ResponseEntity<Object> handleWrongQuerySortException(WrongQuerySortException ex);

    @ExceptionHandler(Throwable.class)
    ResponseEntity<Object> handleRemainException(Throwable exception);
}
