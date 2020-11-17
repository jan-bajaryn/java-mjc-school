package com.epam.mjc.core.controller.errhandle;

import com.epam.mjc.api.controller.errhandle.ExceptionHandlerController;
import com.epam.mjc.api.controller.errhandle.Translator;
import com.epam.mjc.api.service.exception.EmptyGiftCertificates;
import com.epam.mjc.api.service.exception.GiftCertificateAlreadyExists;
import com.epam.mjc.api.service.exception.GiftCertificateNameAlreadyExistsException;
import com.epam.mjc.api.service.exception.GiftCertificateNotFoundException;
import com.epam.mjc.api.service.exception.GiftCertificateValidatorException;
import com.epam.mjc.api.service.exception.OrderNotFountException;
import com.epam.mjc.api.service.exception.OrderValidatorException;
import com.epam.mjc.api.service.exception.TagAlreadyExistsException;
import com.epam.mjc.api.service.exception.TagNotFoundException;
import com.epam.mjc.api.service.exception.TagValidatorException;
import com.epam.mjc.api.service.exception.UserNotFoundException;
import com.epam.mjc.api.service.exception.UserValidatorException;
import com.epam.mjc.api.service.exception.WrongQuerySortException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class ExceptionHandlerControllerImpl extends ResponseEntityExceptionHandler implements ExceptionHandlerController {

    private final Translator translator;

    @Autowired
    public ExceptionHandlerControllerImpl(Translator translator) {
        this.translator = translator;
    }

    @Override
    @ExceptionHandler(TagValidatorException.class)
    public ResponseEntity<Object> handleTagValidatorException(TagValidatorException ex) {
        String errorCode = formatCode(HttpStatus.BAD_REQUEST.value(), ErrorCodes.TAG_VALIDATOR);
        return getResponseEntity(ex, errorCode, HttpStatus.BAD_REQUEST);
    }

    @Override
    @ExceptionHandler(GiftCertificateValidatorException.class)
    public ResponseEntity<Object> handleGiftCertificateValidatorException(GiftCertificateValidatorException ex) {
        String errorCode = formatCode(HttpStatus.BAD_REQUEST.value(), ErrorCodes.GIFT_VALIDATOR);
        return getResponseEntity(ex, errorCode, HttpStatus.BAD_REQUEST);
    }


    @Override
    @ExceptionHandler(GiftCertificateAlreadyExists.class)
    public ResponseEntity<Object> handleGiftCertificateAlreadyExists(GiftCertificateAlreadyExists ex) {
        String errorCode = formatCode(HttpStatus.BAD_REQUEST.value(), ErrorCodes.GIFT_DUPLICATE);
        return getResponseEntity(ex, errorCode, HttpStatus.BAD_REQUEST);
    }

    @Override
    @ExceptionHandler(GiftCertificateNotFoundException.class)
    public ResponseEntity<Object> handleGiftCertificateNotFoundException(GiftCertificateNotFoundException ex) {
        String errorCode = formatCode(HttpStatus.NOT_FOUND.value(), ErrorCodes.GIFT_NOT_FOUND);
        return getResponseEntity(ex, errorCode, HttpStatus.NOT_FOUND);
    }

    @Override
    @ExceptionHandler(TagAlreadyExistsException.class)
    public ResponseEntity<Object> handleTagAlreadyExistsException(TagAlreadyExistsException ex) {
        String errorCode = formatCode(HttpStatus.BAD_REQUEST.value(), ErrorCodes.TAG_DUPLICATE);
        return getResponseEntity(ex, errorCode, HttpStatus.BAD_REQUEST);
    }

    @Override
    @ExceptionHandler(TagNotFoundException.class)
    public ResponseEntity<Object> handleTagNotFoundException(TagNotFoundException ex) {
        String errorCode = formatCode(HttpStatus.NOT_FOUND.value(), ErrorCodes.TAG_NOT_FOUND);
        return getResponseEntity(ex, errorCode, HttpStatus.NOT_FOUND);
    }

    @Override
    @ExceptionHandler(WrongQuerySortException.class)
    public ResponseEntity<Object> handleWrongQuerySortException(WrongQuerySortException ex) {
        String errorCode = formatCode(HttpStatus.BAD_REQUEST.value(), ErrorCodes.WRONG_SORT);
        return getResponseEntity(ex, errorCode, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Object> handleGiftCertificateNameAlreadyExistsException(GiftCertificateNameAlreadyExistsException ex) {
        String errorCode = formatCode(HttpStatus.BAD_REQUEST.value(), ErrorCodes.CERT_NAME_EXISTS);
        return getResponseEntity(ex, errorCode, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex) {
        String errorCode = formatCode(HttpStatus.NOT_FOUND.value(), ErrorCodes.USER_NOT_FOUND);
        return getResponseEntity(ex, errorCode, HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<Object> handleOrderValidatorException(OrderValidatorException ex) {
        String errorCode = formatCode(HttpStatus.BAD_REQUEST.value(), ErrorCodes.ORDER_VALIDATOR);
        return getResponseEntity(ex, errorCode, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Object> handleEmptyGiftCertificates(EmptyGiftCertificates ex) {
        String errorCode = formatCode(HttpStatus.BAD_REQUEST.value(), ErrorCodes.EMPTY_GIFT_CERTIFICATES);
        return getResponseEntity(ex, errorCode, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Object> handleUserValidatorException(UserValidatorException ex) {
        String errorCode = formatCode(HttpStatus.BAD_REQUEST.value(), ErrorCodes.USER_VALIDATOR);
        return getResponseEntity(ex, errorCode, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Object> handleOrderNotFountException(OrderNotFountException ex) {
        String errorCode = formatCode(HttpStatus.NOT_FOUND.value(), ErrorCodes.ORDER_NOT_FOUNT);
        return getResponseEntity(ex, errorCode, HttpStatus.NOT_FOUND);
    }

    @Override
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Object> handleRemainException(Throwable exception) {
        logger.error("Exception: ", exception);
        String errorCode = formatCode(HttpStatus.INTERNAL_SERVER_ERROR.value(), ErrorCodes.REMAIN_CODE);
        String message = translator.getString("unexpected.error");
        ExceptionInfoHolder info = new ExceptionInfoHolder(message, errorCode);
        return new ResponseEntity<>(info, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private String formatCode(int httpPrefix, ErrorCodes giftCode) {
        return String.format("%s%s", httpPrefix, giftCode.getCode());
    }


    private ResponseEntity<Object> getResponseEntity(Exception exception, String errorCode, HttpStatus httpStatus) {
        logger.error("Exception: ", exception);
        String message = translator.getString(exception.getMessage());
        ExceptionInfoHolder info = new ExceptionInfoHolder(message, errorCode);
        return new ResponseEntity<>(info, new HttpHeaders(), httpStatus);
    }

    private ResponseEntity<Object> handleTypicalException(Throwable ex, String message, ErrorCodes errorCode, HttpStatus httpStatus) {
        logger.error("Exception: ", ex);
        ExceptionInfoHolder info = new ExceptionInfoHolder(
                translator.getString(message),
                formatCode(httpStatus.value(), errorCode)
        );
        return new ResponseEntity<>(info, new HttpHeaders(), httpStatus);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleTypicalException(ex, "method-not-allowed", ErrorCodes.METHOD_NOT_ALLOWED, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleTypicalException(ex, "media-not-supported", ErrorCodes.MEDIA_NOT_SUPPORTED, HttpStatus.UNSUPPORTED_MEDIA_TYPE);

    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleTypicalException(ex, "media-not-acceptable", ErrorCodes.MEDIA_NOT_ACCEPTABLE, HttpStatus.NOT_ACCEPTABLE);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleTypicalException(ex, "missing-path-variable", ErrorCodes.MISSING_PATH_VARIABLE, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleTypicalException(ex, "missing-servlet-request-param", ErrorCodes.MISSING_SERVLET_PARAMETER, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleTypicalException(ex, "type-mismatch", ErrorCodes.TYPE_MISMATCH, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleTypicalException(ex, "not-readable", ErrorCodes.MESSAGE_NOT_REPEATABLE, HttpStatus.BAD_REQUEST);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleTypicalException(ex, "method-argument-not-valid", ErrorCodes.METHOD_ARGUMENT_NOT_VALID, HttpStatus.BAD_REQUEST);
    }


}
