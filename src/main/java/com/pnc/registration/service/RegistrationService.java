package com.pnc.registration.service;

import com.pnc.registration.model.domain.RegistrationResponseDto;
import com.pnc.registration.model.domain.RegistrationRequestDto;

public interface RegistrationService {
    RegistrationResponseDto register(RegistrationRequestDto user);
}
