package com.pnc.registration.service;

import com.pnc.registration.model.domain.RegistrationRequestDto;
import com.pnc.registration.model.domain.RegistrationResponseDto;

public interface RegistrationService {
    RegistrationResponseDto register(RegistrationRequestDto user);
}
