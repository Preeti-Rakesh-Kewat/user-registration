package com.pnc.registration.service;

import com.pnc.registration.client.GeoLocationClient;
import com.pnc.registration.exception.GeoLocationClientException;
import com.pnc.registration.model.client.GeoLocation;
import com.pnc.registration.model.domain.RegistrationResponseData;
import com.pnc.registration.model.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RegistrationServiceImpl implements RegistrationService {

    private GeoLocationClient geoLocationClient;

    /**
     * @param user
     * @return RegistrationResponseData
     */
    @Override
    public RegistrationResponseData register(User user) {
        GeoLocation geoLocation = null;

      try {
        geoLocation = geoLocationClient.getGeoLocation(user.getIpAddress());
      }catch(Exception ex){
          throw new GeoLocationClientException(ex.getMessage());
        }

        RegistrationResponseData responseData = null;
        if (geoLocation.getCountryCode().equalsIgnoreCase("CA")) {
            responseData.setStatus("Success");
            responseData.setRegistrationNumber(java.util.UUID.randomUUID().toString());
            responseData.setMessage(" Welcome " + user.getUserName() + " from " + geoLocation.getCity());
        } else {
            responseData.setStatus("Success");
            responseData.setMessage("user is not eligible to register");
        }
        return responseData;

    }

}
