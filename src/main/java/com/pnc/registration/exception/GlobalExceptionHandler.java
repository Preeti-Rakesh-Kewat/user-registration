package com.pnc.registration.exception;

import com.pnc.registration.model.domain.RegistrationResponseData;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
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

        RegistrationResponseData error = getErrorResponse(errorMessage);


        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<Object> headerMissingException(MissingRequestHeaderException ex) {
        RegistrationResponseData error = getErrorResponse(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(GeoLocationClientException.class)
    public ResponseEntity<Object> cceCacheException(GeoLocationClientException ex) {


        RegistrationResponseData error = getErrorResponse(ex.getMessage());
        log.error("[GeoLocationClientException]:{} ", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private RegistrationResponseData getErrorResponse(String message) {
        RegistrationResponseData error = new RegistrationResponseData();
        error.setStatus("ERROR");
        error.setMessage(message);
        return error;
    }
}

