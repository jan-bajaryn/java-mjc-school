package com.epam.mjc.api.controller.errhandle;

import com.epam.mjc.api.service.exception.CountValidatorException;
import com.epam.mjc.api.service.exception.EmptyGiftCertificates;
import com.epam.mjc.api.service.exception.GiftCertificateAlreadyExists;
import com.epam.mjc.api.service.exception.GiftCertificateNameAlreadyExistsException;
import com.epam.mjc.api.service.exception.GiftCertificateNotFoundException;
import com.epam.mjc.api.service.exception.GiftCertificateValidatorException;
import com.epam.mjc.api.service.exception.MissedIdAndNameException;
import com.epam.mjc.api.service.exception.OrderNotFountException;
import com.epam.mjc.api.service.exception.OrderValidatorException;
import com.epam.mjc.api.service.exception.PaginationException;
import com.epam.mjc.api.service.exception.PurchaseCertificateValidatorException;
import com.epam.mjc.api.service.exception.TagAlreadyExistsException;
import com.epam.mjc.api.service.exception.TagNotFoundException;
import com.epam.mjc.api.service.exception.TagValidatorException;
import com.epam.mjc.api.service.exception.UserNotFoundException;
import com.epam.mjc.api.service.exception.UserValidatorException;
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

    @ExceptionHandler(GiftCertificateNameAlreadyExistsException.class)
    ResponseEntity<Object> handleGiftCertificateNameAlreadyExistsException(GiftCertificateNameAlreadyExistsException ex);

    @ExceptionHandler(UserNotFoundException.class)
    ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex);


    @ExceptionHandler(OrderValidatorException.class)
    ResponseEntity<Object> handleOrderValidatorException(OrderValidatorException ex);

    @ExceptionHandler(EmptyGiftCertificates.class)
    ResponseEntity<Object> handleEmptyGiftCertificates(EmptyGiftCertificates ex);

    @ExceptionHandler(UserValidatorException.class)
    ResponseEntity<Object> handleUserValidatorException(UserValidatorException ex);

    @ExceptionHandler(OrderNotFountException.class)
    ResponseEntity<Object> handleOrderNotFountException(OrderNotFountException ex);

    @ExceptionHandler(PaginationException.class)
    ResponseEntity<Object> handlePaginationException(PaginationException ex);

    @ExceptionHandler(MissedIdAndNameException.class)
    ResponseEntity<Object> handleMissedIdAndNameException(MissedIdAndNameException ex);

    @ExceptionHandler(PurchaseCertificateValidatorException.class)
    ResponseEntity<Object> handlePurchaseCertificateValidatorException(PurchaseCertificateValidatorException ex);

    @ExceptionHandler(CountValidatorException.class)
    ResponseEntity<Object> handleCountValidatorException(CountValidatorException ex);


    @ExceptionHandler(Throwable.class)
    ResponseEntity<Object> handleRemainException(Throwable exception);
}
