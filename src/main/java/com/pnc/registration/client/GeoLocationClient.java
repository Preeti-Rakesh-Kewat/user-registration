package com.pnc.registration.client;

import com.pnc.registration.model.client.GeoLocation;

public interface GeoLocationClient {

    GeoLocation getGeoLocation(String ipAddress);
}
