package com.pnc.registration.service.impl;

import com.pnc.registration.client.GeoLocationClient;
import com.pnc.registration.model.client.GeoLocation;
import com.pnc.registration.model.domain.RegistrationResponseData;
import com.pnc.registration.model.domain.User;
import com.pnc.registration.service.RegistrationService;
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

        GeoLocation geoLocation = geoLocationClient.getGeoLocation(user.getIpAddress());

        RegistrationResponseData responseData = new RegistrationResponseData();
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
