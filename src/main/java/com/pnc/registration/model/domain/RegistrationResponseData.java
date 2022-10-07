package com.pnc.registration.model.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RegistrationResponseData implements Serializable {
    private String Status;
    private String RegistrationNumber;
    private String Message;
}
