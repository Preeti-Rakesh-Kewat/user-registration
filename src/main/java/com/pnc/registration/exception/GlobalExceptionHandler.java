package com.pnc.registration.exception;

import com.pnc.registration.model.domain.RegistrationResponseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

@Data
@Slf4j
@ControllerAdvice
@EqualsAndHashCode(callSuper = false)
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> constraintValidationException(ConstraintViolationException constraintViolationException) {
        String errorMessage = "";
        if (constraintViolationException != null && constraintViolationException.getConstraintViolations() != null) {
            //Gets the first violation in the list
            ConstraintViolation<?> violation = constraintViolationException.getConstraintViolations().iterator().next();

            //Get the last node of the violation
            String field = null;

            //The returned value of getPropertyPath() is an Iterable<Node> and the last element of the iterator is the field name that violated the constraint.
            for (Path.Node node : violation.getPropertyPath()) {
                field = node.getName();
            }

            StringJoiner joiner = new StringJoiner(" ");
            errorMessage = joiner.add(field).add(violation.getMessage()).toString();
        } else {
            errorMessage = "ConstraintViolationException occurred.";
        }

        return new ResponseEntity<>(getErrorResponse(errorMessage),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<Object> headerMissingException(MissingRequestHeaderException ex) {
        return new ResponseEntity<>(getErrorResponse(ex.getMessage()),HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(GeoLocationClientException.class)
    public ResponseEntity<RegistrationErrorResponseDto> cceCacheException(GeoLocationClientException ex) {
        log.error("[GeoLocationClientException]:{} ", ex.getMessage());
        return new ResponseEntity<>(getErrorResponse(ex.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {UserNotEligibleException.class})
    public ResponseEntity<RegistrationErrorResponseDto> resourceNotFoundException(UserNotEligibleException ex) {
        return new ResponseEntity<>(getErrorResponse(ex.getMessage()),HttpStatus.BAD_REQUEST);
    }

    private RegistrationErrorResponseDto getErrorResponse(String msg){
        return RegistrationErrorResponseDto.builder()
                .status("ERROR")
                .errorMessage(msg)
                .build();

    }

}

