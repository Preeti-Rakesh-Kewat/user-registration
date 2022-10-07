package com.pnc.registration.service;

import com.pnc.registration.client.GeoLocationClient;
import com.pnc.registration.exception.UserNotEligibleException;
import com.pnc.registration.model.client.GeoLocationClientDto;
import com.pnc.registration.model.domain.RegistrationRequestDto;
import com.pnc.registration.model.domain.RegistrationResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class RegistrationServiceTest {
    @InjectMocks
    private RegistrationServiceImpl registrationService;
    @Mock
    private GeoLocationClient geoLocationClient;

    @Test
    public void test_RegisterUser_Valid_Country() {
        RegistrationRequestDto registrationRequestDto = buildUserRequestDto();

        GeoLocationClientDto locationInfoDto = GeoLocationClientDto.builder()
                .city("Toronto")
                .countryCode("CA")
                .build();
        when(geoLocationClient.getGeoLocation(registrationRequestDto.getIpAddress())).thenReturn(locationInfoDto);

        RegistrationResponseDto registrationResponseDto = registrationService.register(registrationRequestDto);
        assertEquals("Welcome testUserName from Toronto", registrationResponseDto.getMessage());
        assertNotNull(registrationResponseDto.getRegistrationNumber());
    }

    private RegistrationRequestDto buildUserRequestDto() {
        RegistrationRequestDto registrationRequestDto = RegistrationRequestDto.builder()
                .userName("testUserName")
                .password("Somepas$w0rd")
                .ipAddress("some-ip-address")
                .build();
        return registrationRequestDto;
    }

    @Test
    public void test_RegisterUser_Invalid_Country() {
        RegistrationRequestDto registrationRequestDto = buildUserRequestDto();

        GeoLocationClientDto locationInfoDto = GeoLocationClientDto.builder()
                .city("New York")
                .countryCode("US")
                .build();
        when(geoLocationClient.getGeoLocation(registrationRequestDto.getIpAddress())).thenReturn(locationInfoDto);

        assertThrows(UserNotEligibleException.class, () -> registrationService.register(registrationRequestDto));
    }

    @Test
    public void test_RegisterUser_No_Location_Info() {
        RegistrationRequestDto registrationRequestDto = buildUserRequestDto();

        assertThrows(UserNotEligibleException.class, () -> registrationService.register(registrationRequestDto));
    }

}