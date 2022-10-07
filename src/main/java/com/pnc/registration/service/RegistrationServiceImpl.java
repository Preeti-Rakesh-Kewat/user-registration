package com.pnc.registration.service;

import com.pnc.registration.client.GeoLocationClient;
import com.pnc.registration.exception.GeoLocationClientException;
import com.pnc.registration.exception.UserNotEligibleException;
import com.pnc.registration.model.client.GeoLocationClientDto;
import com.pnc.registration.model.domain.RegistrationRequestDto;
import com.pnc.registration.model.domain.RegistrationResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RegistrationServiceImpl implements RegistrationService {

    private final GeoLocationClient geoLocationClient;


    @Override
    public RegistrationResponseDto register(RegistrationRequestDto registrationRequestDto) {
        GeoLocationClientDto geoLocation = null;

      try {
        geoLocation = geoLocationClient.getGeoLocation(registrationRequestDto.getIpAddress());
      }catch(Exception ex){
          throw new GeoLocationClientException(ex.getMessage());
        }

        if (geoLocation!=null && "CA".equalsIgnoreCase(geoLocation.getCountryCode()) ){
            return RegistrationResponseDto.builder()
                    .Status("SUCCESS")
                    .RegistrationNumber(UUID.randomUUID().toString())
                    .Message("Welcome " + registrationRequestDto.getUserName() + " from " + geoLocation.getCity())
                    .build();
        } else {
            throw new UserNotEligibleException("User is not eligible for register");
        }

    }

}
