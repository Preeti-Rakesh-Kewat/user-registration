package com.pnc.registration.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class GeoLocationClientException extends RuntimeException {


    private static final long serialVersionUID = 1L;
    private String message;

}


