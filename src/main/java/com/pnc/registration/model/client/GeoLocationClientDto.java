package com.pnc.registration.model.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoLocationClientDto implements Serializable {
    private String status;
    private String countryCode;
    private String city;

}
