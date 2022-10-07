package com.pnc.registration.client;

import com.pnc.registration.model.client.GeoLocation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GeoLocationClientImpl implements GeoLocationClient{
    private RestTemplate restTemplate;

    @Value("${client.geoLocation.url}")
    private String GeoLocationUrl;
    /**
     * @param ipAddress
     * @return GeoLocation
     */
    @Override
    public GeoLocation getGeoLocation(String ipAddress) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromUriString(GeoLocationUrl)
                .queryParam(ipAddress);
        return restTemplate.getForObject(uriBuilder.toUriString(), GeoLocation.class);
    }
}
