package com.pnc.registration.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Slf4j
@RestControllerAdvice
@EqualsAndHashCode(callSuper = false)
public class GlobalExceptionHandler  {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<RegistrationErrorResponseDto> errorDtoList= ex.getBindingResult().getFieldErrors()
                .stream().map(err -> RegistrationErrorResponseDto.builder()
                        .status("ERROR")
                        .errorMessage(err.getField() + " " + err.getDefaultMessage())
                        .build()
                )
                .collect(Collectors.toList());
        return new ResponseEntity<>(errorDtoList, HttpStatus.BAD_REQUEST);
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

