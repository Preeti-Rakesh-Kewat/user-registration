package com.pnc.registration.client;

import com.pnc.registration.model.client.GeoLocationClientDto;

public interface GeoLocationClient {

    GeoLocationClientDto getGeoLocation(String ipAddress);
}
