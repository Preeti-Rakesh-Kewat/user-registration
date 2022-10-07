package com.pnc.registration.client;

import com.pnc.registration.model.client.GeoLocationClientDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@Configuration
@Slf4j
public class GeoLocationClientImpl implements GeoLocationClient {
    private RestTemplate restTemplate;

    @Value("${client.geoLocation.url}")
    private String geoLocationUrl;

    public GeoLocationClientImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Override
    public GeoLocationClientDto getGeoLocation(String ipAddress) {
        log.info("Calling External API to get location info for ipAddress: {}", ipAddress);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromUriString(geoLocationUrl)
                .queryParam(ipAddress);
        GeoLocationClientDto response = restTemplate.getForObject(uriBuilder.toUriString(), GeoLocationClientDto.class);

        log.info("Response received for ipAddress: {} and response: {}", ipAddress, response);
        return response;

    }
}
